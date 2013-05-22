package dark2phoenix.mods.rescuechest.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.tileentity.TileEntityRescueChest;

public class PacketHandler implements IPacketHandler {
    @Override
    public void onPacketData(INetworkManager network, Packet250CustomPayload packet, Player player) {
        ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
        int x = dat.readInt();
        int y = dat.readInt();
        int z = dat.readInt();
        int facing = dat.readInt();
        World world = RescueChest.proxy.getClientWorld();
        TileEntity te = world.getBlockTileEntity(x, y, z);
        if (te instanceof TileEntityRescueChest) {
            TileEntityRescueChest icte = (TileEntityRescueChest) te;
            icte.setFacing(facing);
        }
        world.markBlockForUpdate(x, y, z);
    }

    public static Packet getPacket(TileEntityRescueChest TileEntityRescueChest) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
        DataOutputStream dos = new DataOutputStream(bos);
        int x = TileEntityRescueChest.xCoord;
        int y = TileEntityRescueChest.yCoord;
        int z = TileEntityRescueChest.zCoord;
        int facing = TileEntityRescueChest.getFacing();
        try {
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(z);
            dos.writeInt(facing);
        } catch (IOException e) {
            // UNPOSSIBLE?
        }
        Packet250CustomPayload pkt = new Packet250CustomPayload();
        pkt.channel = "IronChest";
        pkt.data = bos.toByteArray();
        pkt.length = bos.size();
        pkt.isChunkDataPacket = true;
        return pkt;
    }
}
