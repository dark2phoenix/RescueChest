package dark2phoenix.mods.rescuechest.tileentity;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.inventory.ContainerRescueChest;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityRescueChest extends TileEntity implements IInventory {

    private Logger      logger      = RescueChest.logger;

    private String      sourceClass = this.getClass().getName();

    /** Direction the chest is currently facing **/
    private byte        facing;

    /** The current angle of the lid (between 0 and 1) */
    float        lidAngle    = 0.0F;

    /** The angle of the lid last tick */
    float        prevLidAngle;

    /** The number of players currently using this chest */
    int          numUsingPlayers;

    /** Server sync counter (once per 20 ticks) */
    private int         ticksSinceSync;

    /** Has the inventory been manipulated, use for resync */
    private boolean     inventoryTouched;

    /** Array of the current chest contents */
    ItemStack[]         chestContents;

    /**
     * Total number of slots
     */
    private int inventorySize = 40;
    
    private boolean isHotBarActive = false;
    
    
    public TileEntityRescueChest() {
        chestContents = new ItemStack[getSizeInventory()];
    }

    /**
     * Called when a player or other entity close's the chest
     */
    @Override
    public void closeChest() {
        String sourceMethod = "closeChest";
        logger.entering(sourceClass, sourceMethod);

        if (worldObj == null) {
            logger.logp(Level.FINEST, sourceClass, sourceMethod, "worldObj is null, exiting");
            logger.exiting(sourceClass, sourceMethod);
            return;
        }

        numUsingPlayers = (numUsingPlayers-- < 0) ? 0 : numUsingPlayers;
        logger.logp(Level.FINEST, sourceClass, sourceMethod, "Decrementing numUsingPlayers by 1 to : " + numUsingPlayers);
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, RescueChest.rescueChestBlock.blockID, 1, numUsingPlayers);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amt) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.stackSize <= amt) {
                setInventorySlotContents(slot, null);
            } else {
                stack = stack.splitStack(amt);
                if (stack.stackSize == 0) {
                    setInventorySlotContents(slot, null);
                }
            }
        }
        return stack;
    }

    /**
     * @return the chestContents
     */
    public ItemStack[] getChestContents() {
        return chestContents;
    }

    public byte getFacing() {
        return this.facing;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public String getInvName() {
        return "dark2phoenix.mods.rescuechest.tileentityrescuechest";
    }

    /**
     * @return the lidAngle
     */
    public float getLidAngle() {
        return lidAngle;
    }

    /**
     * @return the numUsingPlayers
     */
    public int getNumUsingPlayers() {
        return numUsingPlayers;
    }

    /**
     * @return the prevLidAngle
     */
    public float getPrevLidAngle() {
        return prevLidAngle;
    }

    @Override
    public int getSizeInventory() {
        return inventorySize;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return chestContents[slot];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            setInventorySlotContents(slot, null);
        }
        return stack;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public boolean isStackValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }

    /**
     * Called when a player or other entity open's the chest
     */
    @Override
    public void openChest() {
        String sourceMethod = "openChest";
        logger.entering(sourceClass, sourceMethod);
        if (worldObj == null) {
            logger.logp(Level.FINEST, sourceClass, sourceMethod, "worldObj is null, exiting");
            logger.exiting(sourceClass, sourceMethod);
            return;
        }
        numUsingPlayers++;
        logger.logp(Level.FINEST, sourceClass, sourceMethod, "Incrementing numUsingPlayers by 1 to : " + numUsingPlayers);
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, RescueChest.rescueChestBlock.blockID, 1, numUsingPlayers);
        logger.exiting(sourceClass, sourceMethod);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        String sourceMethod = "readFromNBT";
        super.readFromNBT(tagCompound);

        NBTTagList tagList = tagCompound.getTagList("Inventory");
        for (int i = 0; i < tagList.tagCount(); i++) {
            NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
            byte slot = tag.getByte("Slot");
            if (slot >= 0 && slot < chestContents.length) {
                chestContents[slot] = ItemStack.loadItemStackFromNBT(tag);
            }
        }
        facing = tagCompound.getByte("facing");
        NBTTagList rescueChestTagList = tagCompound.getTagList("RescueChest Information");

    }

    /**
     * Called when a client event is received with the event number and
     * argument, see World.sendClientEvent
     */
    @Override
    public boolean receiveClientEvent(int i, int j) {
        if (i == 1) {
            numUsingPlayers = j;
        } else if (i == 2) {
            facing = (byte) j;
        } else if (i == 3) {
            facing = (byte) (j & 0x7);
            numUsingPlayers = (j & 0xF8) >> 3;
        }
        return true;
    }

    public void rotateAround(ForgeDirection axis) {
        String sourceMethod="RotateAround";
        logger.entering(sourceClass, sourceMethod, axis);
        byte newDirection = (byte) ForgeDirection.getOrientation(facing).getRotation(axis).ordinal();
        setFacing(newDirection);
        logger.logp(Level.FINE, sourceClass, sourceMethod, String.format("Setting direction to %d", newDirection));
        worldObj.addBlockEvent(xCoord, yCoord, zCoord, RescueChest.rescueChestBlock.blockID, 2, getFacing());
        logger.exiting(sourceClass, sourceMethod, newDirection);
    }

    /**
     * @param chestContents the chestContents to set
     */
    public void setChestContents(ItemStack[] chestContents) {
        this.chestContents = chestContents;
    }

    public void setFacing(byte chestFacing) {
        this.facing = chestFacing;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        chestContents[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit()) {
            stack.stackSize = getInventoryStackLimit();
        }
    }

    /**
     * @param lidAngle the lidAngle to set
     */
    public void setLidAngle(float lidAngle) {
        this.lidAngle = lidAngle;
    }

    public void setMaxStackSize(int size) {

    }

    /**
     * @param numUsingPlayers the numUsingPlayers to set
     */
    public void setNumUsingPlayers(int numUsingPlayers) {
        this.numUsingPlayers = numUsingPlayers;
    }

    /**
     * @param prevLidAngle the prevLidAngle to set
     */
    public void setPrevLidAngle(float prevLidAngle) {
        this.prevLidAngle = prevLidAngle;
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses,
     * e.g. the mob spawner uses this to count ticks and creates a new spawn
     * inside its implementation.
     */
    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj != null && !this.worldObj.isRemote && this.numUsingPlayers != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numUsingPlayers = 0;
            float distanceFromChest = 5.0F;

            @SuppressWarnings("rawtypes")
            List entityList = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getAABBPool().getAABB((double) ((float) this.xCoord - distanceFromChest), (double) ((float) this.yCoord - distanceFromChest), (double) ((float) this.zCoord - distanceFromChest), (double) ((float) (this.xCoord + 1) + distanceFromChest), (double) ((float) (this.yCoord + 1) + distanceFromChest), (double) ((float) (this.zCoord + 1) + distanceFromChest)));
            
            @SuppressWarnings("rawtypes")
            Iterator entityListIterator = entityList.iterator();

            while (entityListIterator.hasNext()) {
                EntityPlayer entityPlayer = (EntityPlayer) entityListIterator.next();

                if (entityPlayer.openContainer instanceof ContainerRescueChest) {
                    ++this.numUsingPlayers;
                }
            }
        }

        if (worldObj != null && !worldObj.isRemote && ticksSinceSync < 0) {
            worldObj.addBlockEvent(xCoord, yCoord, zCoord, RescueChest.rescueChestBlock.blockID, 3, ((numUsingPlayers << 3) & 0xF8) | (facing & 0x7));
        }
        if (!worldObj.isRemote && inventoryTouched) {
            inventoryTouched = false;
        }

        this.ticksSinceSync++;
        prevLidAngle = lidAngle;
        float f = 0.1F;
        if (numUsingPlayers > 0 && lidAngle == 0.0F) {
            double d = (double) xCoord + 0.5D;
            double d1 = (double) zCoord + 0.5D;
            worldObj.playSoundEffect(d, (double) yCoord + 0.5D, d1, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }
        if (numUsingPlayers == 0 && lidAngle > 0.0F || numUsingPlayers > 0 && lidAngle < 1.0F) {
            float f1 = lidAngle;
            if (numUsingPlayers > 0) {
                lidAngle += f;
            } else {
                lidAngle -= f;
            }
            if (lidAngle > 1.0F) {
                lidAngle = 1.0F;
            }
            float f2 = 0.5F;
            if (lidAngle < f2 && f1 >= f2) {
                double d2 = (double) xCoord + 0.5D;
                double d3 = (double) zCoord + 0.5D;
                worldObj.playSoundEffect(d2, (double) yCoord + 0.5D, d3, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }
            if (lidAngle < 0.0F) {
                lidAngle = 0.0F;
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        String sourceMethod = "WriteToNBT";
        super.writeToNBT(tagCompound);

        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < chestContents.length; i++) {
            ItemStack stack = chestContents[i];
            if (stack != null) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setByte("Slot", (byte) i);
                stack.writeToNBT(tag);
                itemList.appendTag(tag);
            }
        }
        tagCompound.setTag("Inventory", itemList);
        tagCompound.setByte("facing", facing);
    }

    public boolean isHotBarActive() {
        return isHotBarActive;
    }

    public void setHotBarActive(boolean isHotBarActive) {
        this.isHotBarActive = isHotBarActive;
    }
    
    
}