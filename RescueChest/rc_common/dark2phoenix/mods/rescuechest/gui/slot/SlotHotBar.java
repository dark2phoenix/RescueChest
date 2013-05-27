package dark2phoenix.mods.rescuechest.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SlotHotBar extends Slot {
    
    
    public static Icon active;
    
    public static Icon inactive;
    

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
        Icon icon = (isActive) ? active : inactive;
        return icon;
    }

}
