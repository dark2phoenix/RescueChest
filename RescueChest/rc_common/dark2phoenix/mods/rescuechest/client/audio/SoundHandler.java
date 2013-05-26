package dark2phoenix.mods.rescuechest.client.audio;

import java.util.logging.Level;

import dark2phoenix.mods.rescuechest.RescueChest;
import dark2phoenix.mods.rescuechest.lib.Sounds;

import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class SoundHandler {
    
    String sourceClass = this.getClass().getName();

    @ForgeSubscribe
    public void onSoundLoad(SoundLoadEvent event) {
        
        String sourceMethod = "onSoundLoad";

        // For each custom sound file we have defined in Sounds
        for (String soundFile : Sounds.soundFiles) {
            // Try to add the custom sound file to the pool of sounds
            try {
                event.manager.soundPoolSounds.addSound(soundFile, this.getClass().getResource("/" + soundFile));
            }
            // If we cannot add the custom sound file to the pool, log the exception
            catch (Exception e) {
                RescueChest.logger.logp(Level.WARNING, sourceClass, sourceMethod, String.format("Failed loading sound file: %s", soundFile));
            }
        }
    }
    
    
}
