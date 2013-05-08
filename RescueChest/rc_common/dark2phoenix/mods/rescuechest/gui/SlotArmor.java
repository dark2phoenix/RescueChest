package dark2phoenix.mods.rescuechest.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.Constants;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.Constants.ArmorSlots;

public class SlotArmor extends Slot {

	/**
	 * The armor type that can be placed on that slot, it uses the same values of
	 * armorType field on ItemArmor.
	 */
	private int armorType = -1;
	
	
	private Logger logger = RescueChest.logger;

	private String sourceClass = this.getClass().getName();

	public SlotArmor(IInventory par2IInventory, int par3, int par4, int par5, ArmorSlots allowedArmorType) {
		super(par2IInventory, par3, par4, par5);
		this.armorType = allowedArmorType.getValue();
	}

	/**
	 * Returns the maximum stack size for a given slot (usually the same as
	 * getInventoryStackLimit(), but 1 in the case of armor slots)
	 */
	@Override
	public int getSlotStackLimit() {
		return 1;
	}

	/**
	 * Check if the stack is a valid item for this slot. Always true beside for
	 * the armor slots.
	 */
	@Override
	public boolean isItemValid(ItemStack itemStack) {
		String sourceMethod = "isItemValid";
		logger.entering(sourceMethod, sourceMethod, itemStack);

		boolean returnValue =  (itemStack == null ) ? false 
				: (itemStack.getItem() instanceof ItemArmor ? ((ItemArmor) itemStack.getItem()).armorType == this.armorType 
				: (itemStack.getItem().itemID != Block.pumpkin.blockID && itemStack.getItem().itemID != Item.skull.itemID ? false 
				: this.armorType == 0));
		logger.logp(Level.FINE, sourceMethod, sourceMethod, "Decision was " + returnValue);
		logger.exiting(sourceMethod, sourceMethod);
		return returnValue;
	}

	@SideOnly(Side.CLIENT)
	/**
	 * Returns the icon index on items.png that is used as background image of the slot.
	 */
	 /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
    public Icon getBackgroundIconIndex()
    {
        return ItemArmor.func_94602_b(this.armorType);
    }
}
