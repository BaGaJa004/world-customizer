package net.bagaja.worldcustomizer;

import net.bagaja.worldcustomizer.config.OreSettings;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = WorldCustomizerMod.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WorldEventHandler {

    @SubscribeEvent
    public static void onLevelLoad(LevelEvent.Load event) {
        // Only run on the overworld (dimension 0) to avoid loading 3 times
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        MinecraftServer server = level.getServer();
        if (server == null) return;

        OreSettings.load(server);
    }

    @SubscribeEvent
    public static void onLevelSave(LevelEvent.Save event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (!level.dimension().equals(Level.OVERWORLD)) return;

        MinecraftServer server = level.getServer();
        if (server == null) return;

        // TO THIS:
        if (OreSettings.CUSTOM_SETTINGS_ENABLED) {
            OreSettings.save(server);
        }
    }
}