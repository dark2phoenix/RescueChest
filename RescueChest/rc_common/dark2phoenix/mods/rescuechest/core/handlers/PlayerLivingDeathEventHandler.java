package dark2phoenix.mods.rescuechest.core.handlers;

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
		
		try {
			
			// Check to see if the player has setup an inventory
			String worldName = "";
			int[] chestLocation = new int[0];
			String playerName = "";
			
			try {
				NBTTagCompound playerNBT = dyingPlayer.getEntityData();
				
				NBTTagCompound persistedData = (NBTTagCompound)dyingPlayer.getEntityData().getTag(EntityPlayer.PERSISTED_NBT_TAG);
				NBTTagList rescueChestData = persistedData.getTagList(Constants.NBT_RESCUE_CHEST_TAG_NAME);
				
				NBTTagCompound worldNameTag = (NBTTagCompound) rescueChestData.tagAt(0);
				worldName = worldNameTag.getString("worldName");
				NBTTagCompound chestLocationTag = (NBTTagCompound) rescueChestData.tagAt(1);
				chestLocation = chestLocationTag.getIntArray("chestLocation");
				NBTTagCompound playerNameTag = (NBTTagCompound) rescueChestData.tagAt(2);
				playerName = playerNameTag.getString("playerName");
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
		
				TileEntityRescueChest activeChest = null;
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
				
				ContainerRescueChest rescueChestContainer = new ContainerRescueChest(playerInventory, activeChest, false );

				// Get the items in the player's armor slots
                ItemStack[] playerArmorInventory = playerInventory.armorInventory;
                rescueChestContainer.addItemsToInventory(dyingPlayer, activeChest, playerArmorInventory, InventoryType.ARMOR);
                activeChest.updateEntity();
                dyingPlayer.worldObj.markBlockForUpdate(x, y, z);
				
				// Get the items from the player's main Inventory
				ItemStack[] playerMainInventory = playerInventory.mainInventory;
				rescueChestContainer.addItemsToInventory(dyingPlayer, activeChest, playerMainInventory, InventoryType.MAIN);
				activeChest.updateEntity();
				dyingPlayer.worldObj.markBlockForUpdate(x, y, z);

				playerInventory.clearInventory(-1, -1);
			}
		}
		catch (Exception e) {
			logger.logp(Level.SEVERE, sourceMethod, sourceMethod, "Error Occurred trying to run Rescue Chest procedure", e);
			dyingPlayer.addChatMessage("Severe error occurred when trying to transfer items to Rescue Chest, check log for details");
		}
	}

}
