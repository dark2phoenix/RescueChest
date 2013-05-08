package dark2phoenix.mods.rescuechest.gui;

import java.util.logging.Logger;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.BlockRescueChest;
import dark2phoenix.mods.rescuechest.RescueChest;

public class SlotHotBar extends Slot {

	
	private Logger logger = RescueChest.logger;

	private String sourceClass = this.getClass().getName();
	
	private boolean isActive = false;

	public SlotHotBar(IInventory par2IInventory, int par3, int par4, int par5, boolean makeActive) {
		super(par2IInventory, par3, par4, par5);
		super.texture = "/dark2phoenix/mods/rescuechest/sprites/SlotBackgrounds.png";
		this.isActive = makeActive;
		
	}

	/**
	 * Returns the maximum stack size for a given slot (usually the same as
	 * getInventoryStackLimit(), but 1 in the case of armor slots)
	 */
	@Override
	public int getSlotStackLimit() {
		return 64;
	}

	    
//	@SideOnly(Side.CLIENT)
//	@Override
//	/**
//	 * Returns the icon index on items.png that is used as background image of the slot.
//	 */
//	public Icon getBackgroundIconIndex() {
//		// 256x256 sheet, column + position * 16;
////		return ( (isActive) ? 0 : 1 ) + 0 * 16;
//	    
//	    return BlockRescueChest.getHotbarIcon( (isActive) ? 0 : 1 );
//	}
	
	

	
}
