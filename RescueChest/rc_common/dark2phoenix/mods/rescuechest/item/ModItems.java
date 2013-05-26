package dark2phoenix.mods.rescuechest.item;

import cpw.mods.fml.common.registry.LanguageRegistry;
import dark2phoenix.mods.rescuechest.configuration.Items;
import dark2phoenix.mods.rescuechest.core.StringUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModItems {
    
    public static Item hotbar;
    public static Item woodenCoin;

    
    public static void init() {

        hotbar = new ItemHotBar(Items.HOTBAR_ITEM_ID);
        LanguageRegistry.addName(hotbar, "Hotbar Item");

        woodenCoin = new ItemWoodenCoin(Items.WOODEN_COIN_ID);
        LanguageRegistry.addName(woodenCoin, StringUtils.localize("item.woodencoin.name"));
        
        initItemRecipies();
    }

    @SuppressWarnings("unchecked")
    private static void initItemRecipies() {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(woodenCoin), true, new Object[] { " P ", "PLP", " P ", Character.valueOf('P'), "plankWood", Character.valueOf('L'), "woodLog" }));
    }

}
