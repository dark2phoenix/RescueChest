package dark2phoenix.mods.rescuechest.gui.slot;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.lib.Constants.ArmorSlots;
import dark2phoenix.mods.rescuechest.RescueChest;

public class SlotArmor extends Slot {

    /**
     * The armor type that can be placed on that slot, it uses the same values
     * of armorType field on ItemArmor.
     */
    private int    armorType   = -1;

    private Logger logger      = RescueChest.logger;

    private String sourceClass = this.getClass().getName();

    //IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition, boolean makeActive)
    
    public SlotArmor(IInventory inventory, int slotIndex, int xDisplayPosition, int yDisplayPosition, ArmorSlots allowedArmorType) {
        super(inventory, slotIndex, xDisplayPosition, yDisplayPosition);
        this.armorType = allowedArmorType.getValue();
        this.backgroundIcon = ItemArmor.func_94602_b(this.armorType);
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
        logger.entering(sourceClass, sourceMethod, itemStack);

        // Making this decision in less optimized so it is easier to debug
        boolean returnValue = false;

        if (itemStack != null) {
            logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack is not null");
            if (itemStack.getItem() instanceof ItemArmor) {
                logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack is an instance of ItemArmor");
                ItemArmor itemArmor = (ItemArmor) itemStack.getItem();
                logger.logp(Level.FINER, sourceClass, sourceMethod, String.format("itemArmor type is %d, armor slot is type %d", itemArmor.armorType, this.armorType ));
                returnValue = itemArmor.isValidArmor(itemStack, armorType);
                logger.logp(Level.FINE, sourceClass, sourceMethod, String.format("item returns %b for armor slot type %d", returnValue, this.armorType ));
            }
            logger.logp(Level.FINE, sourceClass, sourceMethod, "Check special cases for armorType.HELMET");
            if (this.armorType == ArmorSlots.HELMET.getValue()) {
                if (itemStack.getItem().itemID == Block.pumpkin.blockID) {
                    logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack contains a pumpkin which can be a helmet");
                    returnValue = true;
                } else if (itemStack.getItem() instanceof ItemSkull ) {
                    logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack contains a skull which can be a helmet");
                    returnValue = true;
                }
            }
        }

        logger.logp(Level.FINE, sourceClass, sourceMethod, "Final decision was " + returnValue);
        logger.exiting(sourceClass, sourceMethod);
        return returnValue;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns the icon index on items.png that is used as background image of the slot.
     */
    public Icon getBackgroundIconIndex() {
        return ItemArmor.func_94602_b(this.armorType);
    }
}
