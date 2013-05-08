package dark2phoenix.mods.rescuechest.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import dark2phoenix.mods.rescuechest.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.TileEntityRescueChest;

public class GuiRescueChest extends GuiContainer {

	public GuiRescueChest(InventoryPlayer inventoryPlayer, TileEntityRescueChest tileEntity) {
		// the container is instantiated and passed to the superclass for handling
		super(new ContainerRescueChest(inventoryPlayer, tileEntity));
		this.xSize = 184;
		this.ySize = 184;
		this.allowUserInput = false;
	}

//	@Override
//	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
//		// // the parameters for drawString are: string, x, y, color
//		fontRenderer.drawString("SavePoint Chest", 8, 6, 4210752);
//		// //draws "Inventory" or your regional equivalent
//		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
//	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		// draw your Gui here, only thing you need to change is the path
//		int texture = mc.renderEngine.getTexture("/dark2phoenix/mods/rescuechest/sprites/RescueChestContainer.png");
		String texture = String.format("/mods/%s/textures/gui/%s", RescueChest.modid.toLowerCase(), "RescueChestContainer2.png");
		
		
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
