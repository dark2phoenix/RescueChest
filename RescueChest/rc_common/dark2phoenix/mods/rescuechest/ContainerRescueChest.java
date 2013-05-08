package dark2phoenix.mods.rescuechest;

import java.util.logging.Logger;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import dark2phoenix.mods.rescuechest.Constants.ArmorSlots;
import dark2phoenix.mods.rescuechest.gui.SlotArmor;
import dark2phoenix.mods.rescuechest.gui.SlotHotBar;

public class ContainerRescueChest extends Container {
	

	TileEntityRescueChest tileEntityRescueChest;
	
	private Logger logger = RescueChest.logger;

	private String sourceClass = this.getClass().getName();
	

	/**
	 * Number of Rows for the container
	 */
	private final int rows = 5;

	/**
	 * Number of slots per row
	 */
	private final int slotsPerRow = 9;

	/**
	 * Container GUI width
	 */
	private final int containerWidth = 184;
	/**
	 * Container GUI height
	 */
	private final int containerHeight = 184;
	/**
	 * Total number of slots the container holds
	 */
	private final int totalNumberofSlots = rows * slotsPerRow;

	public ContainerRescueChest(InventoryPlayer inventoryPlayer, TileEntityRescueChest te) {
		tileEntityRescueChest = te;
		tileEntityRescueChest.openChest();

		// Armor Inventory
		addSlotToContainer(new SlotArmor(te, 0 + 0 * slotsPerRow, 12 + 0 * 18, 8 + 0 * 18, ArmorSlots.HELMET ));
		addSlotToContainer(new SlotArmor(te, 1 + 0 * slotsPerRow, 12 + 1 * 18, 8 + 0 * 18, ArmorSlots.CHEST_PLATE ));
		addSlotToContainer(new SlotArmor(te, 2 + 0 * slotsPerRow, 12 + 2 * 18, 8 + 0 * 18, ArmorSlots.GREAVES ));
		addSlotToContainer(new SlotArmor(te, 3 + 0 * slotsPerRow, 12 + 3 * 18, 8 + 0 * 18, ArmorSlots.BOOTS ));
		
		// Hot Bar Inventory
		int hotBotRow = 1;
		for (int chestCol = 0; chestCol < slotsPerRow; chestCol++) {
			addSlotToContainer(new SlotHotBar(te, chestCol + hotBotRow * slotsPerRow, 12 + chestCol * 18, 8 + hotBotRow * 18, false));
		}

		
		// Chest Inventory
		for (int chestRow = 2; chestRow < rows; chestRow++) {
			for (int chestCol = 0; chestCol < slotsPerRow; chestCol++) {
				addSlotToContainer(new Slot(te, chestCol + chestRow * slotsPerRow, 12 + chestCol * 18, 8 + chestRow * 18));
			}

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
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntityRescueChest.isUseableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
		ItemStack itemstack = null;
		Slot slot = (Slot) inventorySlots.get(slotNumber);
		
		if (slot != null && slot.getHasStack() ) {
			ItemStack itemStack1 = slot.getStack();
			itemstack = itemStack1.copy();
			
			int firstSlot = 4; //first non-armor slot
			
			if ( itemStack1.getItem() != null && (itemStack1.getItem() instanceof ItemArmor ) )  {
				
				for (int armorSlotCheck = 0; armorSlotCheck < 4; armorSlotCheck++ ) {
					if ( this.getSlot(armorSlotCheck).isItemValid(itemStack1) ) {
						firstSlot = armorSlotCheck;
						break;
					}
				}
			}
			

			if (slotNumber < totalNumberofSlots) {
				if (!mergeItemStack(itemStack1, totalNumberofSlots, inventorySlots.size(), true)) {
					return null;
				}
			}
			else if (!mergeItemStack(itemStack1, firstSlot, totalNumberofSlots, false)) {
				return null;
			}
			if (itemStack1.stackSize == 0) {
				slot.putStack(null);
			}
			else {
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

}