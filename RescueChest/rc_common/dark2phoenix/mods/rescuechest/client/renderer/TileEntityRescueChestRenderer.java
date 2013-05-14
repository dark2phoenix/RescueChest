package dark2phoenix.mods.rescuechest.client.renderer;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.common.primitives.SignedBytes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.model.ModelRescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

@SideOnly(Side.CLIENT)
public class TileEntityRescueChestRenderer extends TileEntitySpecialRenderer {

    private RenderItem       itemRenderer;

    /** The normal small chest model. */
    private ModelRescueChest chestModel  = new ModelRescueChest();
    private Logger           logger      = RescueChest.logger;
    private String           sourceClass = this.getClass().getName();

    public TileEntityRescueChestRenderer() {
        super();
        itemRenderer = new RenderItem() {
            @Override
            public byte getMiniBlockCount(ItemStack stack) {
                return SignedBytes.saturatedCast(Math.min(stack.stackSize / 32, 15) + 1);
            }

            @Override
            public byte getMiniItemCount(ItemStack stack) {
                return SignedBytes.saturatedCast(Math.min(stack.stackSize / 32, 7) + 1);
            }

            @Override
            public boolean shouldBob() {
                return false;
            }

            @Override
            public boolean shouldSpreadItems() {
                return false;
            }
        };
        itemRenderer.setRenderManager(RenderManager.instance);
    }


    /**
     * Renders the TileEntity for the chest at a position.
     */
    public void renderTileEntityRescueChestAt(TileEntityRescueChest tileEntity, double x, double y, double z, float partialTick) {

        if (tileEntity == null) { 
            return;
        }

        int blockFacingDirection = 3;

        if (tileEntity.getWorldObj() != null ) {
            blockFacingDirection = tileEntity.getFacing();
        } 
        
        this.bindTextureByName(String.format("/mods/%s/textures/model/%s", RescueChest.modid.toLowerCase(), "RescueChestTexture.png"));

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        short facing = 0;

        if (blockFacingDirection == 2) {
            facing = 180;
        }

        if (blockFacingDirection == 3) {
            facing = 0;
        }

        if (blockFacingDirection == 4) {
            facing = 90;
        }

        if (blockFacingDirection == 5) {
            facing = -90;
        }
        
        GL11.glRotatef((float) facing, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        float lidAngle = tileEntity.getPrevLidAngle() + (tileEntity.getLidAngle() - tileEntity.getPrevLidAngle()) * partialTick;

        lidAngle = 1.0F - lidAngle;
        lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
        chestModel.chestLid.rotateAngleX = -(lidAngle * (float) Math.PI / 2.0F);
        chestModel.renderAll();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTick) {
        this.renderTileEntityRescueChestAt((TileEntityRescueChest) tileEntity, x, y, z, partialTick);
    }
}
