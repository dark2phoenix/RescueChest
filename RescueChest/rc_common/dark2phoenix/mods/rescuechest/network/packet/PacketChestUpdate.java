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

    int x, y, z;
    byte orientation;
    int upgradeValue;
    int dimension;
    String ownerName;
    
    public PacketChestUpdate() {

        super(PacketTypeHandler.CHEST, true);
    }

    public PacketChestUpdate(int inX, int inY, int inZ, ForgeDirection inOrientation, int inUpgradeValue, int inDimension, String inOwnerName) {

        super(PacketTypeHandler.CHEST, true);
        this.x = inX;
        this.y = inY;
        this.z = inZ;
        this.orientation = (byte) inOrientation.ordinal();
        this.upgradeValue = inUpgradeValue;
        this.dimension = inDimension;
        this.ownerName = inOwnerName;

    }

    @Override
    public void writeData(DataOutputStream data) throws IOException {

        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
        data.writeByte(orientation);
        data.writeInt(upgradeValue);
        data.writeInt(dimension);
        data.writeUTF(ownerName);
    }

    @Override
    public void readData(DataInputStream data) throws IOException {

        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
        orientation = data.readByte();
        upgradeValue = data.readInt();
        dimension = data.readInt();
        ownerName = data.readUTF();

    }

    @Override
    public void execute(INetworkManager manager, Player player) {

        RescueChest.proxy.handleTileEntityPacket(x, y, z, ForgeDirection.getOrientation(orientation), upgradeValue, dimension, ownerName);
    }

}
