package dark2phoenix.mods.rescuechest.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import dark2phoenix.mods.rescuechest.configuration.Blocks;
import dark2phoenix.mods.rescuechest.core.StringUtils;

public class ModBlocks {

    /* Mod block instances */
    public static Block rescueChest;

    public static void init() {

        rescueChest = new BlockRescueChest(Blocks.RESCUECHEST_ID);
        GameRegistry.registerBlock(rescueChest, Blocks.RESCUE_CHEST_NAME);
        LanguageRegistry.addName(rescueChest, StringUtils.localize("block.rescuechest.name"));

        initBlockRecipes();

    }

    @SuppressWarnings("unchecked")
    private static void initBlockRecipes() {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(new ItemStack(rescueChest), true, new Object[] { "PPP", "PGP", "PPP", Character.valueOf('P'), "plankWood", Character.valueOf('G'), "ingotGold" }));
    }
    

    
}
