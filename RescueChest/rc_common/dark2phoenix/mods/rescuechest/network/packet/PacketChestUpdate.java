package dark2phoenix.mods.rescuechest.network.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.network.INetworkManager;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.common.network.Player;
import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.network.PacketTypeHandler;

public class PacketChestUpdate extends PacketRescueChest {

    public int x, y, z;
    public byte orientation;
    public int upgradeValue;
    
    public PacketChestUpdate() {

        super(PacketTypeHandler.CHEST, true);
    }

    public PacketChestUpdate(int x, int y, int z, ForgeDirection orientation, int upgradeValue) {

        super(PacketTypeHandler.CHEST, true);
        this.x = x;
        this.y = y;
        this.z = z;
        this.orientation = (byte) orientation.ordinal();
        this.upgradeValue = upgradeValue;

    }

    @Override
    public void writeData(DataOutputStream data) throws IOException {

        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        data.writeByte(orientation);
        data.writeInt(upgradeValue);
    }

    @Override
    public void readData(DataInputStream data) throws IOException {

        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        orientation = data.readByte();
        upgradeValue = data.readInt();

    }

    @Override
    public void execute(INetworkManager manager, Player player) {

        RescueChest.proxy.handleTileEntityPacket(x, y, z, ForgeDirection.getOrientation(orientation), upgradeValue);
    }

}
