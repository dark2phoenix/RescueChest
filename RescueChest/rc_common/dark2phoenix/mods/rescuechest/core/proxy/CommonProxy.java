package dark2phoenix.mods.rescuechest.core.proxy;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import dark2phoenix.mods.rescuechest.inventory.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class CommonProxy implements IGuiHandler {

    public void registerRenderInformation() {
    }

    public void registerTileEntitySpecialRenderer() {
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int X, int Y, int Z) {
        TileEntity tileEntity = world.getBlockTileEntity(X, Y, Z);
        if (tileEntity != null && tileEntity instanceof TileEntityRescueChest) {
            TileEntityRescueChest spcte = (TileEntityRescueChest) tileEntity;
            return new ContainerRescueChest(player.inventory, spcte, false);
        } else {
            return null;
        }
    }

    public String getMinecraftVersion() {
        return Loader.instance().getMinecraftModContainer().getVersion();
    }

    /* INSTANCES */
    public Object getClient() {
        return FMLClientHandler.instance().getClient();
    }

    public World getClientWorld() {
        return Minecraft.getMinecraft().theWorld;
    }

    public String getCurrentLanguage() {
        return Minecraft.getMinecraft().gameSettings.language;
    }

    /* ENTITY HANDLING */
    public void removeEntity(Entity entity) {
        entity.worldObj.removeEntity(entity);
    }

    /* LOCALIZATION */
    public void addName(Object obj, String s) {
    }

    public void addLocalization(String s1, String string) {
    }

    public String getItemDisplayName(ItemStack newStack) {
        return "";
    }

    public void registerBlock(Block block, Class<? extends ItemBlock> item) {
        GameRegistry.registerBlock(block, item, block.getUnlocalizedName().replace("tile.", ""));
    }

    public void registerItem(Item item) {
        GameRegistry.registerItem(item, item.getUnlocalizedName().replace("item.", ""));
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void registerTileEntity(Class clas, String ident) {
        GameRegistry.registerTileEntity(clas, ident);
    }

    public void onCraftingPickup(World world, EntityPlayer player, ItemStack stack) {
        stack.onCrafting(world, player, stack.stackSize);
    }

    @SuppressWarnings("unchecked")
    public void addCraftingRecipe(ItemStack result, Object[] recipe) {
        CraftingManager.getInstance().getRecipeList().add(new ShapedOreRecipe(result, recipe));
        // GameRegistry.addRecipe(result, recipe);
    }

    public void addShapelessRecipe(ItemStack result, Object[] recipe) {
        GameRegistry.addShapelessRecipe(result, recipe);
    }

    public void TakenFromCrafting(EntityPlayer thePlayer, ItemStack itemstack, IInventory craftMatrix) {
        GameRegistry.onItemCrafted(thePlayer, itemstack, craftMatrix);
    }

    public Random createNewRandom(World world) {
        return new Random(world.getSeed());
    }

}
