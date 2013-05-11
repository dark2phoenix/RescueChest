package dark2phoenix.mods.rescuechest.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.Constants.ArmorSlots;
import dark2phoenix.mods.rescuechest.RescueChest;

public class SlotArmor extends Slot {

    /**
     * The armor type that can be placed on that slot, it uses the same values
     * of armorType field on ItemArmor.
     */
    private int    armorType   = -1;

    private Logger logger      = RescueChest.logger;

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
        logger.entering(sourceClass, sourceMethod, itemStack);

        // Making this decision in less optimized so it is easier to debug

        boolean returnValue = false;

        if (itemStack != null) {
            logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack is not null");
            returnValue = false;
            if (itemStack.getItem() instanceof ItemArmor) {
                logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack is an instance of ItemArmor", new Object[] { itemStack });
                ItemArmor itemArmor = (ItemArmor) itemStack.getItem();
                if (itemArmor.armorType == this.armorType) {
                    logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack matches slot item type", new Object[] { itemArmor.armorType, this.armorType });
                    returnValue = true;
                }
                if (this.armorType == ArmorSlots.HELMET.getValue()) {
                    logger.logp(Level.FINE, sourceClass, sourceMethod, "Check special cases for HELMET", new Object[] { itemStack.getItem() });

                    if (itemStack.getItem().itemID == Block.pumpkin.blockID) {
                        logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack contains a pumpkin which can be a helmet", new Object[] { itemStack.getItem() });
                        returnValue = true;
                    } else if (itemStack.getItem().itemID == Block.skull.blockID) {
                        logger.logp(Level.FINE, sourceClass, sourceMethod, "itemStack contains a skull which can be a helmet", new Object[] { itemStack.getItem() });
                        returnValue = true;
                    }

                }
            }
        }

        // boolean returnValue = (itemStack == null ) ? false
        // : (itemStack.getItem() instanceof ItemArmor ? ((ItemArmor)
        // itemStack.getItem()).armorType == this.armorType
        // : (itemStack.getItem().itemID != Block.pumpkin.blockID &&
        // itemStack.getItem().itemID != Item.skull.itemID ? false
        // : this.armorType == 0));
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
