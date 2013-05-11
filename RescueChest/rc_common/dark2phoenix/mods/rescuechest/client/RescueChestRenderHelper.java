package dark2phoenix.mods.rescuechest.client;

import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.ChestItemRenderHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

import com.google.common.collect.Maps;

import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class RescueChestRenderHelper extends ChestItemRenderHelper {
    private Map<Integer, TileEntityRescueChest> itemRenders = Maps.newHashMap();

    public RescueChestRenderHelper()
    {
            itemRenders.put(0, (TileEntityRescueChest) RescueChest.rescueChestBlock.createTileEntity(null, 0));
    }

    @Override
    public void renderChest(Block block, int i, float f)
    {
        if (block == RescueChest.rescueChestBlock)
        {
            TileEntityRenderer.instance.renderTileEntityAt(itemRenders.get(i), 0.0D, 0.0D, 0.0D, 0.0F);
        }
        else
        {
            super.renderChest(block, i, f);
        }
    }
}