package dark2phoenix.mods.rescuechest.gui.slot;

import java.util.logging.Logger;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.item.ItemHotBar;

public class SlotHotBar extends Slot {

    private Logger  logger      = RescueChest.logger;

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
     * Check if the stack is a valid item for this slot. Always true beside for
     * the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return this.isActive;
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
        Icon icon = ItemHotBar.getHotbarIcon((isActive) ? 0 : 1);
        return icon;
    }

}
