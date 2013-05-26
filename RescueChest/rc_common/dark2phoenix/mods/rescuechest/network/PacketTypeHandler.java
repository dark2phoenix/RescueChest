package dark2phoenix.mods.rescuechest.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import dark2phoenix.mods.rescuechest.lib.Reference;
import dark2phoenix.mods.rescuechest.network.packet.PacketChestUpdate;
import dark2phoenix.mods.rescuechest.network.packet.PacketRescueChest;
import dark2phoenix.mods.rescuechest.network.packet.PacketSoundEvent;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public enum PacketTypeHandler {
    CHEST(PacketChestUpdate.class),
    SOUND_EVENT(PacketSoundEvent.class);
    

    private Class<? extends dark2phoenix.mods.rescuechest.network.packet.PacketRescueChest> clazz;

    PacketTypeHandler(Class<? extends PacketRescueChest> clazz) {

        this.clazz = clazz;
    }

    public static PacketRescueChest buildPacket(byte[] data) {

        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        int selector = bis.read();
        DataInputStream dis = new DataInputStream(bis);

        PacketRescueChest packet = null;

        try {
            packet = values()[selector].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        packet.readPopulate(dis);

        return packet;
    }

    public static PacketRescueChest buildPacket(PacketTypeHandler type) {

        PacketRescueChest packet = null;

        try {
            packet = values()[type.ordinal()].clazz.newInstance();
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }

        return packet;
    }

    public static Packet populatePacket(PacketRescueChest packetRescueChest) {

        byte[] data = packetRescueChest.populate();

        Packet250CustomPayload packet250 = new Packet250CustomPayload();
        packet250.channel = Reference.CHANNEL_NAME;
        packet250.data = data;
        packet250.length = data.length;
        packet250.isChunkDataPacket = packetRescueChest.isChunkDataPacket;

        return packet250;
    }
}
