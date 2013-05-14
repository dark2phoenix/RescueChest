package dark2phoenix.mods.rescuechest.core.handlers;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import dark2phoenix.mods.rescuechest.lib.Constants;
import dark2phoenix.mods.rescuechest.lib.Constants.InventoryType;
import dark2phoenix.mods.rescuechest.core.types.ChestLocation;
import dark2phoenix.mods.rescuechest.inventory.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;
import dark2phoenix.mods.rescuechest.RescueChest;

/**
 * Name and cast of this class are irrelevant
 */
public class PlayerLivingDeathEventHandler {
	/**
	 * The key is the @ForgeSubscribe annotation and the cast of the Event you put
	 * in as argument. The method name you pick does not matter. Method signature
	 * is public void, always.
	 */

	private Logger logger = RescueChest.logger;

	private String sourceClass = this.getClass().getName();

	@ForgeSubscribe
	public void livingDeathEvent(LivingDeathEvent event) {

		String sourceMethod = "livingDeathEvent";
		
		logger.entering(sourceMethod, sourceMethod);

		EntityLiving dyingEnt = event.entityLiving;
		DamageSource attackSource = event.source;

		if (!(dyingEnt instanceof EntityPlayer)) {
			return;
		}

		EntityPlayer dyingPlayer = (EntityPlayer) dyingEnt;
		InventoryPlayer playerInventory = dyingPlayer.inventory;

		int currentDimension = dyingPlayer.dimension;
		
		World world = dyingEnt.worldObj;

        TileEntityRescueChest activeChest = null;
        
        ItemStack inventoryContents[] = null;
		
		try {
			
			// Check to see if the player has setup an inventory
			String worldName = "";
			int[] chestLocation = new int[0];
			String playerName = "";
			
			try {
			    
			    logger.setLevel(Level.FINEST);
				NBTTagCompound playerNBT = dyingPlayer.getEntityData();
				logger.logp(Level.FINEST, sourceClass, sourceMethod, "Loaded chest NBT Data", new Object[] { playerNBT });
				NBTTagCompound persistedData = (NBTTagCompound)dyingPlayer.getEntityData().getTag(EntityPlayer.PERSISTED_NBT_TAG);
				NBTTagList rescueChestData = persistedData.getTagList(Constants.NBT_RESCUE_CHEST_TAG_NAME);
                logger.logp(Level.FINEST, sourceClass, sourceMethod, "Loaded rescue chest NBT Data", new Object[] { rescueChestData } );
				
				NBTTagCompound worldNameTag = (NBTTagCompound) rescueChestData.tagAt(0);
				worldName = worldNameTag.getString("worldName");
                logger.logp(Level.FINE, sourceClass, sourceMethod, "Extracted worldName from NBT", new Object[] {worldName});

				NBTTagCompound chestLocationTag = (NBTTagCompound) rescueChestData.tagAt(1);
				chestLocation = chestLocationTag.getIntArray("chestLocation");
                logger.logp(Level.FINE, sourceClass, sourceMethod, "Extracted chestLocation from NBT", new Object[] {worldName});

				NBTTagCompound playerNameTag = (NBTTagCompound) rescueChestData.tagAt(2);
				playerName = playerNameTag.getString("playerName");
                logger.logp(Level.FINE, sourceClass, sourceMethod, "Extracted playerName from NBT", new Object[] {worldName});


			}
			catch (Exception e) {
				// No stored information for this player yet
				return;
			}
			 
			// Player has a configured inventory, let's get going
			if (worldName != null && worldName.equals(dyingPlayer.worldObj.getWorldInfo().getWorldName()) && chestLocation != null && playerName != null) {

				int x = (Integer) chestLocation[0];
				int y = (Integer) chestLocation[1];
				int z = (Integer) chestLocation[2];
		
				try {
					activeChest = (TileEntityRescueChest) dyingPlayer.worldObj.getBlockTileEntity(x, y, z);
					if ( activeChest == null ) {
						dyingPlayer.addChatMessage("Rescue chest is no longer present in the world, cannot save any items!!!");
						return;
					}
				}
				catch (Exception e) {
					dyingPlayer.addChatMessage("Severe error trying to save items to Rescue chest, see log!!!");
					return;
				}
				
			   
				inventoryContents = new ItemStack[activeChest.getSizeInventory()];
				
				// Capture inventory's original contents
				inventoryContents = activeChest.getChestContents();
				
				
				
				
				ContainerRescueChest rescueChestContainer = new ContainerRescueChest(playerInventory, activeChest, false );

				// Get the items in the player's armor slots
                ItemStack[] playerArmorInventory = playerInventory.armorInventory;
                rescueChestContainer.addItemsToInventory(dyingPlayer, activeChest, playerArmorInventory, InventoryType.ARMOR);

                
				// Get the items from the player's main Inventory
				ItemStack[] playerCompleteInventory = playerInventory.mainInventory;
				
				//Split the player inventory into the hotbar and the inventory;
				
				ItemStack[] playerHotbarInventory = new ItemStack[9];
				ItemStack[] playerMainInventory = new ItemStack[playerCompleteInventory.length - playerHotbarInventory.length];

				System.arraycopy(playerCompleteInventory, 0, playerHotbarInventory, 0, 9);
				System.arraycopy(playerCompleteInventory, 9, playerMainInventory, 0, 27);

				//Add the Hotbar Inventory
				rescueChestContainer.addItemsToInventory(dyingPlayer, activeChest, playerHotbarInventory, InventoryType.HOTBAR);
				
				// Add the Main Inventory
                rescueChestContainer.addItemsToInventory(dyingPlayer, activeChest, playerMainInventory, InventoryType.MAIN);
				
				
                activeChest.updateEntity();
                dyingPlayer.worldObj.markBlockForUpdate(x, y, z);
				playerInventory.clearInventory(-1, -1);
			}
		}
		catch (Exception e) {
			logger.logp(Level.SEVERE, sourceMethod, sourceMethod, "Error Occurred trying to run Rescue Chest procedure", e);
			// restore old contents
			if (activeChest != null && inventoryContents != null)  {
			    activeChest.setChestContents(inventoryContents);
			}
			
			dyingPlayer.addChatMessage("Severe error occurred when trying to transfer items to Rescue Chest, check log for details");
		}
	}

}
