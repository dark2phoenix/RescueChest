package dark2phoenix.mods.rescuechest.item;

import net.minecraft.creativetab.CreativeTabs;

public class ItemWoodenCoin extends ItemBase {

    public ItemWoodenCoin(int id) {
        super(id);

        this.setUnlocalizedName("WoodenCoin");
        setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(0);
        this.setMaxStackSize(4);
        this.setNoRepair();
    }

}
