package net.bagaja.worldcustomizer;

import net.bagaja.worldcustomizer.config.WorldGenConfig;
import net.bagaja.worldcustomizer.worldgen.OreGenModifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(WorldCustomizerMod.MODID)
public class WorldCustomizerMod {
    public static final String MODID = "worldcustomizer";

    public WorldCustomizerMod(FMLJavaModLoadingContext context) {
        var modBusGroup = context.getModBusGroup();

        // Register the biome modifier serializer
        OreGenModifier.MODIFIER_SERIALIZERS.register(modBusGroup);

        // Register config
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.SERVER,
                WorldGenConfig.SPEC,
                "worldcustomizer-worldgen.toml"
        );

        // Register Forge bus events manually — @Mod.EventBusSubscriber auto-registration
        // was removed in EventBus 7 (Forge 56). Use the static BUS field on each event.
        LevelEvent.Load.BUS.addListener(WorldEventHandler::onLevelLoad);
        LevelEvent.Save.BUS.addListener(WorldEventHandler::onLevelSave);

        // Client-only event: only register on the client side
        if (FMLEnvironment.dist == Dist.CLIENT) {
            net.minecraftforge.client.event.ScreenEvent.Init.Post.BUS
                    .addListener(net.bagaja.worldcustomizer.client.CreateWorldScreenHandler::onScreenInit);
        }
    }
}