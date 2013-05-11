package dark2phoenix.mods.rescuechest.client.proxy;

import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import dark2phoenix.mods.rescuechest.client.RescueChestRenderHelper;
import dark2phoenix.mods.rescuechest.client.renderer.TileEntityRescueChestRenderer;
import dark2phoenix.mods.rescuechest.core.proxy.CommonProxy;
import dark2phoenix.mods.rescuechest.inventory.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderInformation() {
        ChestItemRenderHelper.instance = new RescueChestRenderHelper();
    }

    public void registerTileEntitySpecialRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRescueChest.class, new TileEntityRescueChestRenderer());
    }

    public World getClientWorld() {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityRescueChest) {
            return new ContainerRescueChest(player.inventory, (TileEntityRescueChest) te, false);
        } else {
            return null;
        }
    }

}
