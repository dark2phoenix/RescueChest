package dark2phoenix.mods.rescuechest.core.proxy;


import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import dark2phoenix.mods.rescuechest.client.audio.SoundHandler;
import dark2phoenix.mods.rescuechest.inventory.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.lib.Constants;
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
    

    public void registerSoundHandler() {
    }

    public void handleTileEntityPacket(int x, int y, int z, ForgeDirection inOrientation, int inUpgradeValue) {

    }
    
    public void initTileEntities() {
        GameRegistry.registerTileEntity(TileEntityRescueChest.class, Constants.TILE_ENTITY_RESCUE_CHEST_NAME);
    }

    
}
