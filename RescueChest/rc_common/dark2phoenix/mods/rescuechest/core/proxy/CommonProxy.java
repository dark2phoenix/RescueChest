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
            TileEntityRescueChest rcte = (TileEntityRescueChest) tileEntity;
            return new ContainerRescueChest(player.inventory, rcte, false);
        } else {
            return null;
        }
    }

    public World getClientWorld() {
        return null;
    }

    public String getCurrentLanguage() {
        return Minecraft.getMinecraft().gameSettings.language;
    }

}
