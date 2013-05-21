package dark2phoenix.mods.rescuechest.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;

public class ItemHotBar extends Item {

    public ItemHotBar(int id) {
        super(id);
        setUnlocalizedName("hotbarItem");
    }

 
    /**
     * Icon list for the GUI to show whether slots are enabled or not
     */
    @SideOnly(Side.CLIENT)
    private static Icon[] hotbarIcons = new Icon[2];
    
    @SideOnly(Side.CLIENT)
    public static Icon getHotbarIcon( int offset ) {
        return hotbarIcons[offset];
    }
    
    @SideOnly(Side.CLIENT)
    public static Icon setHotbarIcon( int offset, Icon icon ) {
        return hotbarIcons[offset] = icon;
    }
    
    @Override
    public void registerIcons(IconRegister iconRegister) {
        setHotbarIcon(0, iconRegister.registerIcon("RescueChest:SlotAvailable"));
        setHotbarIcon(1, iconRegister.registerIcon("RescueChest:SlotNotAvailable"));
    }


}