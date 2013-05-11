package dark2phoenix.mods.rescuechest.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.BlockRescueChest;
import dark2phoenix.mods.rescuechest.HotbarItem;
import dark2phoenix.mods.rescuechest.RescueChest;

public class SlotHotBar extends Slot {

    private Logger  logger  = RescueChest.logger;

    private String  sourceClass = this.getClass().getName();

    private boolean isActive    = false;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public SlotHotBar(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition, boolean makeActive) {
        super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
        this.setActive(makeActive);
    }

    /**
     * Returns the maximum stack size for a given slot (usually the same as
     * getInventoryStackLimit(), but 1 in the case of armor slots)
     */
    @Override
    public int getSlotStackLimit() {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    @Override
    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
    public Icon getBackgroundIconIndex() {
        String sourceMethod = "getBackgroundIconIndex()";
        Icon icon = HotbarItem.getHotbarIcon( (isActive) ? 0 : 1 ); 
        logger.logp(Level.FINE, sourceClass, sourceMethod, String.format("Icon name is %s",  icon.getIconName()));
        return icon;
    }
    
    
}
