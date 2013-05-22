package dark2phoenix.mods.rescuechest.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class ItemChestRenderer implements ISimpleBlockRenderingHandler {

    TileEntityRescueChest tile = new TileEntityRescueChest();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        tile.blockMetadata = metadata;
        TileEntityRenderer.instance.renderTileEntityAt(tile, 0.0D, 0.0D, 0.0D, 0.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    @Override
    public boolean shouldRender3DInInventory() {
        return true;
    }

    @Override
    public int getRenderId() {
        return RescueChest.getRescueChestRenderID();
    }

}
