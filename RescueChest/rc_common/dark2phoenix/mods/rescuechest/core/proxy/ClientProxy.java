package dark2phoenix.mods.rescuechest.core.proxy;


import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;

import dark2phoenix.mods.rescuechest.client.RescueChestItemRenderHelper;
import dark2phoenix.mods.rescuechest.client.renderer.ItemChestRenderer;
import dark2phoenix.mods.rescuechest.client.renderer.TileEntityRescueChestRenderer;
import dark2phoenix.mods.rescuechest.inventory.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;
import dark2phoenix.mods.rescuechest.client.audio.SoundHandler;

public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderInformation() {
        ChestItemRenderHelper.instance = new RescueChestItemRenderHelper();
    }

    public void registerTileEntitySpecialRenderer() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRescueChest.class, new TileEntityRescueChestRenderer());
        RenderingRegistry.registerBlockHandler(new ItemChestRenderer());
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


    @Override
    public void registerSoundHandler() {

        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }

    @Override
    public void handleTileEntityPacket(int x, int y, int z, ForgeDirection inOrientation, int inUpgradeValue) {

        TileEntity tileEntity = FMLClientHandler.instance().getClient().theWorld.getBlockTileEntity(x, y, z);

        if (tileEntity != null) {
            if (tileEntity instanceof TileEntityRescueChest) {
                TileEntityRescueChest rcte = (TileEntityRescueChest) tileEntity;
                
                rcte.setOrientation(inOrientation);
                rcte.setUpgradeValue(inUpgradeValue);
            }
        }
    }
    
    
}
