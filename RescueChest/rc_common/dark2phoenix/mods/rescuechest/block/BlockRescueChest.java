package dark2phoenix.mods.rescuechest.block;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.UP;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.configuration.Blocks;
import dark2phoenix.mods.rescuechest.lib.Constants;
import dark2phoenix.mods.rescuechest.lib.Reference;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class BlockRescueChest extends BlockContainer {

	/**
	 * Material for this block
	 */
    static Material material = Material.wood;
    
    /**
     * List of the sides for this block
     */
    private static String[] sideNames = { "top", "front", "side" };
    
    /**
     * Map that compares the side of the block to the texture to use
     */
    private static int[] sideMapping = { 0, 0, 2, 1, 2, 2, 2 };
	
    /**
     * Randomizer used when the block is broken and items are dropped
     */
    private Random random;
    
    @SideOnly(Side.CLIENT)
    private Icon[] icons;

	public BlockRescueChest(int id) {

		super(id, material);
		setHardness(0.5F);
		setStepSound(Block.soundWoodFootstep);
		setBlockBounds(0.0625F, 0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName("rescueChest");
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
	    TileEntityRescueChest tileentitychest = (TileEntityRescueChest) world.getBlockTileEntity(x, y, z);
        if (tileentitychest != null)
        {
            dropContent(0, tileentitychest, world, tileentitychest.xCoord, tileentitychest.yCoord, tileentitychest.zCoord);
        }
		super.breakBlock(world, x, y, z, par5, par6);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityRescueChest();
	}

	 @SideOnly(Side.CLIENT)
	 @Override
	 public Icon getIcon(int side, int metadata) {
        return icons[ sideMapping[side] ];
     }

	 @Override
	 @SideOnly(Side.CLIENT)
	 public void registerIcons(IconRegister par1IconRegister) {
	     
	     icons = new Icon[3];

	     int i = 0;
         for (String s : sideNames) {
             icons[i++] = par1IconRegister.registerIcon(String.format("%s:%s_%s", Reference.MOD_ID.toLowerCase(), Blocks.RESCUE_CHEST_NAME, s));
         }

	 }

	@Override
	public int getRenderType() {
	    return RescueChest.getRescueChestRenderID();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int idk, float what, float these, float are) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);

		if (tileEntity == null || !(tileEntity instanceof TileEntityRescueChest)) {
			return true;
		}

		if (world.isBlockSolidOnSide(x, y + 1, z, ForgeDirection.DOWN)) {
			return true;
		}

		if (world.isRemote) {
			return true;
		}

		// Mark this as the player's currently selected rescue chest

		NBTTagCompound playerData = player.getEntityData();
		NBTTagCompound persistedData = (NBTTagCompound) player.getEntityData().getTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (persistedData == null) {
			persistedData = new NBTTagCompound();
		}

		NBTTagList rescueChestData = new NBTTagList();

		NBTTagCompound worldNameTag = new NBTTagCompound();
		worldNameTag.setString("worldName", world.getWorldInfo().getWorldName());
		rescueChestData.appendTag(worldNameTag);
		NBTTagCompound chestLocationTag = new NBTTagCompound();
		chestLocationTag.setIntArray("chestLocation", new int[] { x, y, z });
		rescueChestData.appendTag(chestLocationTag);
		NBTTagCompound playerNameTag = new NBTTagCompound();
		playerNameTag.setString("playerName", player.getEntityName());
		rescueChestData.appendTag(playerNameTag);
		rescueChestData.setName(Constants.NBT_RESCUE_CHEST_TAG_NAME);
		persistedData.setTag(Constants.NBT_RESCUE_CHEST_TAG_NAME, rescueChestData);
		playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, persistedData);

		// code to open gui explained later
		player.openGui(RescueChest.instance, 0, world, x, y, z);

		return true;

	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k) {
		super.onBlockAdded(world, i, j, k);
		world.markBlockForUpdate(i, j, k);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityLiving, ItemStack itemStack) {

	       int direction = 0;
           int facing = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

	        if (facing == 0) {
	            direction = ForgeDirection.NORTH.ordinal();
	        }
	        else if (facing == 1) {
	            direction = ForgeDirection.EAST.ordinal();
	        }
	        else if (facing == 2) {
	            direction = ForgeDirection.SOUTH.ordinal();
	        }
	        else if (facing == 3) {
	            direction = ForgeDirection.WEST.ordinal();
	        }
	    

		TileEntity te = world.getBlockTileEntity(i, j, k);
		if (te != null && te instanceof TileEntityRescueChest) {
		    TileEntityRescueChest rcte = (TileEntityRescueChest) te;
			rcte.setOrientation(direction);
			rcte.setDimension(entityLiving.worldObj.getWorldInfo().getDimension());
			if (entityLiving instanceof EntityPlayer) {
			    EntityPlayer player = (EntityPlayer) entityLiving;
			    rcte.setOwnerName(player.getCommandSenderName());
			}
			world.markBlockForUpdate(i, j, k);
		}

	}

	@Override
	public boolean renderAsNormalBlock() {
		return true;
	}

	/**
	 * Updates the blocks bounds based on its current state.
	 */
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		if (world.getBlockId(x, y, z - 1) == this.blockID) {
			this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
		}
		else if (world.getBlockId(x, y, z + 1) == this.blockID) {
			this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
		}
		else if (world.getBlockId(x - 1, y, z) == this.blockID) {
			this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		}
		else if (world.getBlockId(x + 1, y, z) == this.blockID) {
			this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
		}
		else {
			this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		}
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public float getExplosionResistance(Entity par1Entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		return super.getExplosionResistance(par1Entity, world, x, y, z, explosionX, explosionY, explosionZ);
	}

	
    public void dropContent(int newSize, IInventory chest, World world, int xCoord, int yCoord, int zCoord)    {
        
        random = new Random(world.getSeed());
        
        for (int l = newSize; l < chest.getSizeInventory(); l++)
        {
            ItemStack itemstack = chest.getStackInSlot(l);
            if (itemstack == null)
            {
                continue;
            }
            float f = random.nextFloat() * 0.8F + 0.1F;
            float f1 = random.nextFloat() * 0.8F + 0.1F;
            float f2 = random.nextFloat() * 0.8F + 0.1F;
            while (itemstack.stackSize > 0)
            {
                int i1 = random.nextInt(21) + 10;
                if (i1 > itemstack.stackSize)
                {
                    i1 = itemstack.stackSize;
                }
                itemstack.stackSize -= i1;
                EntityItem entityitem = new EntityItem(world, (float) xCoord + f, (float) yCoord + (newSize > 0 ? 1 : 0) + f1, (float) zCoord + f2,
                        new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                float f3 = 0.05F;
                entityitem.motionX = (float) random.nextGaussian() * f3;
                entityitem.motionY = (float) random.nextGaussian() * f3 + 0.2F;
                entityitem.motionZ = (float) random.nextGaussian() * f3;
                if (itemstack.hasTagCompound())
                {
                    entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
                }
                world.spawnEntityInWorld(entityitem);
            }
        }
    }
	
    private static final ForgeDirection[] validRotationAxes = new ForgeDirection[] { UP, DOWN };
    @Override
    public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z)
    {
        return validRotationAxes;
    }

    @Override
    public boolean rotateBlock(World worldObj, int x, int y, int z, ForgeDirection axis)
    {
        if (worldObj.isRemote) {
            return false;
        }
        if (axis == UP || axis == DOWN) {
            TileEntity tileEntity = worldObj.getBlockTileEntity(x, y, z);
            if (tileEntity instanceof TileEntityRescueChest) {
                TileEntityRescueChest rcte = (TileEntityRescueChest) tileEntity;
                rcte.rotateAround(axis);
            }
            return true;
        }
        return false;
    }
    
    @Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5) {
        return Container.calcRedstoneFromInventory((TileEntityRescueChest) par1World.getBlockTileEntity(par2, par3, par4));
    }
    
}