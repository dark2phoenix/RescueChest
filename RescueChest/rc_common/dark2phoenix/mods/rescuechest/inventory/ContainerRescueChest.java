package dark2phoenix.mods.rescuechest.inventory;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.gui.slot.SlotArmor;
import dark2phoenix.mods.rescuechest.gui.slot.SlotHotBar;
import dark2phoenix.mods.rescuechest.lib.Constants.ArmorSlots;
import dark2phoenix.mods.rescuechest.lib.Constants.InventoryType;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class ContainerRescueChest extends Container {

    TileEntityRescueChest tileEntityRescueChest;

    private Logger        logger             = RescueChest.logger;

    private String        sourceClass        = this.getClass().getName();

    /**
     * Number of Rows for the container
     */
    private final int     rows               = 4;

    /**
     * Number of slots per row
     */
    private final int     slotsPerRow        = 9;

    /**
     * Container GUI width
     */
    private final int     containerWidth     = 184;
    /**
     * Container GUI height
     */
    private final int     containerHeight    = 184;
    /**
     * Total number of slots the container holds
     */
    private final int     totalNumberofSlots = 40;

    /**
     * Rescue Chest Container. Defines the slots and interaction with the
     * inventory of the Rescue Chest.
     * 
     * @param inventoryPlayer
     *            Player Inventory
     * @param te
     *            RescueChest Tile Inventory
     * @param checkSlotsOnly
     *            Is this instance of Container going to be used by a player/GUI
     *            or just being called to validate the slots
     */
    public ContainerRescueChest(InventoryPlayer inventoryPlayer, TileEntityRescueChest te, boolean checkSlotsOnly) {
        tileEntityRescueChest = te;

        int slotIndex = 0;
        // Armor Inventory
        addSlotToContainer(new SlotArmor(te, slotIndex, 12 + slotIndex++ * 18, 8, ArmorSlots.HELMET));
        addSlotToContainer(new SlotArmor(te, slotIndex, 12 + slotIndex++ * 18, 8, ArmorSlots.CHEST_PLATE));
        addSlotToContainer(new SlotArmor(te, slotIndex, 12 + slotIndex++ * 18, 8, ArmorSlots.GREAVES));
        addSlotToContainer(new SlotArmor(te, slotIndex, 12 + slotIndex++ * 18, 8, ArmorSlots.BOOTS));

        // Chest Inventory
        for (int chestRow = 1; chestRow < 4; chestRow++) {
            for (int chestCol = 0; chestCol < slotsPerRow; chestCol++) {
                addSlotToContainer(new Slot(te, slotIndex++, 12 + chestCol * 18, 8 + chestRow * 18));
            }

        }

        // Hot Bar Inventory
        int hotBotRow = 4;
        for (int chestCol = 0; chestCol < slotsPerRow; chestCol++) {
            boolean makeActive = true;

            addSlotToContainer(new SlotHotBar(te, slotIndex++, 12 + chestCol * 18, 8 + hotBotRow * 18, makeActive));
        }

        // Bottom of the Display

        // Player Inventory
        int leftCol = (containerWidth - 162) / 2 + 1;
        for (int playerInvRow = 0; playerInvRow < 3; playerInvRow++) {
            for (int playerInvCol = 0; playerInvCol < 9; playerInvCol++) {
                addSlotToContainer(new Slot(inventoryPlayer, playerInvCol + playerInvRow * 9 + 9, leftCol + playerInvCol * 18, containerHeight - (4 - playerInvRow) * 18 - 10));
            }

        }

        // Player Hotbar
        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            addSlotToContainer(new Slot(inventoryPlayer, hotbarSlot, leftCol + hotbarSlot * 18, containerHeight - 24));
        }
        if (!checkSlotsOnly) {
            tileEntityRescueChest.openChest();
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tileEntityRescueChest.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(slotNumber);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack1 = slot.getStack();
            itemstack = itemStack1.copy();

            int firstSlot = 4; // first non-armor slot

            if (itemStack1.getItem() != null && (itemStack1.getItem() instanceof ItemArmor)) {

                for (int armorSlotCheck = 0; armorSlotCheck < 4; armorSlotCheck++) {
                    if (this.getSlot(armorSlotCheck).isItemValid(itemStack1)) {
                        firstSlot = armorSlotCheck;
                        break;
                    }
                }
            }

            if (slotNumber < totalNumberofSlots) {
                if (!mergeItemStack(itemStack1, totalNumberofSlots, inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!mergeItemStack(itemStack1, firstSlot, totalNumberofSlots, false)) {
                return null;
            }
            if (itemStack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public void onCraftGuiClosed(EntityPlayer entityplayer) {
        super.onCraftGuiClosed(entityplayer);
        tileEntityRescueChest.closeChest();
    }

    /**
     * Private method to go through an inventory and transfer items to the
     * Rescue Chest
     * 
     * @param dyingPlayer
     *            Player who is dying
     * @param activeChest
     *            Rescue Chest the player is currently using
     * @param playerInventory
     *            Inventory object to process
     * @param playerInventoryType
     *            The type of inventory being accessed
     */
    public void addItemsToInventory(EntityPlayer dyingPlayer, TileEntityRescueChest activeChest, ItemStack[] playerInventory, InventoryType playerInventoryType) {
        String sourceMethod = "addItemsToInventory";

        logger.entering(sourceClass, sourceMethod, new Object[] { dyingPlayer, activeChest, playerInventory, playerInventoryType });
        logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Player Inventory %scontains %d items, Rescue Chest has only %d", playerInventoryType.getName(), playerInventory.length, activeChest.getSizeInventory()));

        for (int playerInventorySlot = 0; playerInventorySlot < playerInventory.length; playerInventorySlot++) {
            logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Processing Player Inventory %s Slot %d", playerInventoryType.getName(), playerInventorySlot));

            if (playerInventory[playerInventorySlot] == null) {
                logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Player Inventory %s, Slot %d is null, continuing on", playerInventoryType.getName(), playerInventorySlot));
                continue;
            }
            logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Player Inventory %s, Slot %d contains %d of %s", playerInventoryType.getName(), playerInventorySlot, playerInventory[playerInventorySlot].stackSize, playerInventory[playerInventorySlot].getDisplayName()));
            logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Processing RescueChest Inventory for %d of %s in player inventory, %s", playerInventory[playerInventorySlot].stackSize, playerInventoryType.getName(), playerInventory[playerInventorySlot].getDisplayName()));
            boolean placedItem = false;

            logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Starting offset for player inventory %s is %d", playerInventoryType.getName(), playerInventoryType.getStartingOffset()));
            for (int chestInventorySlot = playerInventoryType.getStartingOffset(); chestInventorySlot < activeChest.getSizeInventory(); chestInventorySlot++) {
                if (activeChest.getStackInSlot(chestInventorySlot) == null) {
                    Slot currentSlot = this.getSlot(chestInventorySlot);
                    if (currentSlot.isItemValid(playerInventory[playerInventorySlot])) {
                        logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Moving %d of %s from Player Inventory %s to chest slot %d", playerInventory[playerInventorySlot].stackSize, playerInventoryType.getName(), playerInventory[playerInventorySlot].getDisplayName(), chestInventorySlot));
                        activeChest.setInventorySlotContents(chestInventorySlot, playerInventory[playerInventorySlot].copy());
                        placedItem = true;
                        break;
                    } else {
                        logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Cannot Move %s from Player Inventory to chest slot %d because it is not valid for that slot", playerInventory[playerInventorySlot].getDisplayName(), chestInventorySlot));
                    }
                } else {
                    logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Skipping Resue Chest slot %d because it contains %d of %s", chestInventorySlot, activeChest.getStackInSlot(chestInventorySlot).stackSize, activeChest.getStackInSlot(chestInventorySlot).getDisplayName()));
                }
            }
            if (!placedItem) {
                logger.logp(Level.INFO, sourceClass, sourceMethod, String.format("Dropping %d of %s of playerInventory because chest inventory is full", playerInventory[playerInventorySlot].stackSize, playerInventory[playerInventorySlot].getDisplayName()));
            }
        }
    }

}