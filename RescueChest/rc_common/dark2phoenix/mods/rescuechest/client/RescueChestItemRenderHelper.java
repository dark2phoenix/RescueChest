package dark2phoenix.mods.rescuechest.client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import dark2phoenix.mods.rescuechest.configuration.Blocks;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class RescueChestItemRenderHelper extends ChestItemRenderHelper {

    /**
     * Renders a chest at 0,0,0 - used for item rendering
     */
    @Override
    public void renderChest(Block blockToRender, int i, float f) {
        if (blockToRender.blockID == Blocks.RESCUECHEST_ID) {
            TileEntityRenderer.instance.renderTileEntityAt( new TileEntityRescueChest(), 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else {
            super.renderChest(blockToRender, i, f);
        }

    }
}