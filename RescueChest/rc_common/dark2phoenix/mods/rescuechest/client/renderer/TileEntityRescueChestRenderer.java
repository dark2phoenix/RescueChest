package dark2phoenix.mods.rescuechest.client.renderer;

import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import dark2phoenix.mods.rescuechest.lib.Reference;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

@SideOnly(Side.CLIENT)
public class TileEntityRescueChestRenderer extends TileEntitySpecialRenderer {

    /** The normal small chest model. */
    private ModelChest chestModel  = new ModelChest();

    public TileEntityRescueChestRenderer() {
        super();
    }


    /**
     * Renders the TileEntity for the chest at a position.
     */
    public void renderTileEntityRescueChestAt(TileEntityRescueChest tileEntity, double x, double y, double z, float partialTick) {
        if (tileEntity == null) { 
            return;
        }

        // Default direction
        ForgeDirection blockFacingDirection = ForgeDirection.SOUTH;

        if (tileEntity.getWorldObj() != null ) {
            blockFacingDirection = tileEntity.getOrientation();
        } 
        
        this.bindTextureByName(String.format("/mods/%s/textures/model/%s", Reference.MOD_ID.toLowerCase(), "RescueChestTexture.png"));

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
        
        short facingDirection = 0;        

        if (blockFacingDirection != null) {
            if (blockFacingDirection == ForgeDirection.NORTH) {
                facingDirection = 180;
            }
            else if (blockFacingDirection == ForgeDirection.SOUTH) {
                facingDirection = 0;
            }
            else if (blockFacingDirection == ForgeDirection.WEST) {
                facingDirection = 90;
            }
            else if (blockFacingDirection == ForgeDirection.EAST) {
                facingDirection = -90;
            }
        }
        
        GL11.glRotatef((float) facingDirection, 0.0F, 1.0F, 0.0F);
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
        if ( tileEntity instanceof TileEntityRescueChest) {
            renderTileEntityRescueChestAt((TileEntityRescueChest) tileEntity, x, y, z, partialTick);
        }
        else {
            throw new IllegalArgumentException("This is not a rescuechest tile entity!!!!");
        }
            
    }
}
