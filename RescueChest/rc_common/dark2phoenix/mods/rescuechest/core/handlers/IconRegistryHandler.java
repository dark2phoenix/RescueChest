package dark2phoenix.mods.rescuechest.core.handlers;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import dark2phoenix.mods.rescuechest.gui.slot.SlotHotBar;

public class IconRegistryHandler {

    @ForgeSubscribe
    public void onItemIconRegister(TextureStitchEvent.Pre evt) {
        if (evt.map.equals(Minecraft.getMinecraft().renderEngine.textureMapItems)) {
            SlotHotBar.active = evt.map.registerIcon("RescueChest:HotbarSlotActive");
            SlotHotBar.inactive = evt.map.registerIcon("RescueChest:HotbarSlotInactive");
        }
    }
}
