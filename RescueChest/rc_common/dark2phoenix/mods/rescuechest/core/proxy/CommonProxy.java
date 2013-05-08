package dark2phoenix.mods.rescuechest.core.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import dark2phoenix.mods.rescuechest.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.TileEntityRescueChest;

public class CommonProxy implements IGuiHandler {
	public static String ITEM_PNG = "/dark2phoenix/mods/rescuechest/sprites/RescueChest.png";


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
		TileEntity te = world.getBlockTileEntity(X, Y, Z);
		if (te != null && te instanceof TileEntityRescueChest) {
			TileEntityRescueChest spcte = (TileEntityRescueChest) te;
			return new ContainerRescueChest(player.inventory, spcte);
		}
		else {
			return null;
		}
	}

	public World getClientWorld() {
		return null;
	}

}
