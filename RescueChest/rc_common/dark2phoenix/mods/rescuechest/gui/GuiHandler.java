package dark2phoenix.mods.rescuechest.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import dark2phoenix.mods.rescuechest.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.TileEntityRescueChest;
import dark2phoenix.mods.rescuechest.gui.GuiRescueChest;

public class GuiHandler implements IGuiHandler {
	// returns an instance of the Container
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityRescueChest) {
			return new ContainerRescueChest(player.inventory, (TileEntityRescueChest) tileEntity);
		}
		return null;
	}

	// returns an instance of the gui
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof TileEntityRescueChest) {
			return new GuiRescueChest(player.inventory, (TileEntityRescueChest) tileEntity);
		}
		return null;

	}
}