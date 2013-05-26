package dark2phoenix.mods.rescuechest.network.packet;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.Player;
import dark2phoenix.mods.rescuechest.configuration.ConfigurationSettings;
import dark2phoenix.mods.rescuechest.network.PacketTypeHandler;

/**
 * Equivalent-Exchange-3
 * 
 * PacketSoundEvent
 * 
 * @author pahimar
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */
public class PacketSoundEvent extends PacketRescueChest {

    public String soundName;
    public double x, y, z;
    public float volume, pitch;

    public PacketSoundEvent() {

        super(PacketTypeHandler.SOUND_EVENT, false);
    }

    public PacketSoundEvent(String soundName, double x, double y, double z, float volume, float pitch) {

        super(PacketTypeHandler.SOUND_EVENT, false);
        this.soundName = soundName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException {

        data.writeUTF(soundName);
        data.writeDouble(x);
        data.writeDouble(y);
        data.writeDouble(z);
        data.writeFloat(volume);
        data.writeFloat(pitch);
    }

    @Override
    public void readData(DataInputStream data) throws IOException {

        soundName = data.readUTF();
        x = data.readDouble();
        y = data.readDouble();
        z = data.readDouble();
        volume = data.readFloat();
        pitch = data.readFloat();
    }

    @Override
    public void execute(INetworkManager manager, Player player) {
        if (ConfigurationSettings.ENABLE_SOUNDS) {
            FMLClientHandler.instance().getClient().sndManager.playSound(soundName, (float) x, (float) y, (float) z, volume, pitch);
        }
    }
}
