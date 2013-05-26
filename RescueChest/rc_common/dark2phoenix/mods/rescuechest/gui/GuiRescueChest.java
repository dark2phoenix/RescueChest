package dark2phoenix.mods.rescuechest.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import dark2phoenix.mods.rescuechest.configuration.Blocks;
import dark2phoenix.mods.rescuechest.core.StringUtils;
import dark2phoenix.mods.rescuechest.inventory.ContainerRescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class GuiRescueChest extends GuiContainer {

	public GuiRescueChest(InventoryPlayer inventoryPlayer, TileEntityRescueChest tileEntity) {
		// the container is instantiated and passed to the superclass for handling
		super(new ContainerRescueChest(inventoryPlayer, tileEntity, false));
		this.xSize = 184;
		this.ySize = 184;
		this.allowUserInput = false;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int param1, int param2) {
	    // draws the specified string. Args: string, x, y, color, dropShadow
		fontRenderer.drawString(StringUtils.localize("gui.rescuechest.name"), 85, 7, 0xFFAA00, false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		// draw your Gui here, only thing you need to change is the path
		String texture = String.format("/mods/%s/textures/gui/%s", Blocks.RESCUE_CHEST_NAME.toLowerCase(), "RescueChestContainer.png");
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(texture);
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}

}
