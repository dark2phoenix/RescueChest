package dark2phoenix.mods.rescuechest.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import dark2phoenix.mods.rescuechest.configuration.Blocks;
import dark2phoenix.mods.rescuechest.lib.Constants.UpgradeCoinType;
import dark2phoenix.mods.rescuechest.lib.Sounds;
import dark2phoenix.mods.rescuechest.network.PacketTypeHandler;
import dark2phoenix.mods.rescuechest.network.packet.PacketSoundEvent;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class ItemWoodenCoin extends ItemChestUpgradeCoin {

    public ItemWoodenCoin(int id) {
        super(id);

        this.setUnlocalizedName("WoodenCoin");
        setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(0);
        this.setMaxStackSize(4);
        this.setNoRepair();
        this.coinType = UpgradeCoinType.WOOD;
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int X, int Y, int Z, int side, float hitX, float hitY, float hitZ) {

        boolean discardItem = false;
        TileEntityRescueChest rcte;
        
        if (world.isRemote) {
            return false;
        }

        TileEntity te = world.getBlockTileEntity(X, Y, Z);
        if (te != null && te instanceof TileEntityRescueChest) {
            rcte = (TileEntityRescueChest) te;
            discardItem = rcte.applyUpgradeItem(this);
        } else {
            return false;
        }
        if ( discardItem ) {
            stack.stackSize--;
            world.addBlockEvent(X,Y,Z, Blocks.RESCUECHEST_ID, 3, rcte.getUpgradeValue());
            world.markBlockForUpdate(X, Y, Z);
            return true;
        }
        return false;
    }
    

}
