package net.bagaja.worldcustomizer;

import net.bagaja.worldcustomizer.worldgen.OreGenModifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(WorldCustomizerMod.MODID)
public class WorldCustomizerMod {
    public static final String MODID = "worldcustomizer";

    public WorldCustomizerMod() {
        var modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the biome modifier serializer
        OreGenModifier.MODIFIER_SERIALIZERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(WorldEventHandler::onLevelLoad);
        MinecraftForge.EVENT_BUS.addListener(WorldEventHandler::onLevelSave);

        // Client-only event: only register on the client side
        if (FMLEnvironment.dist == Dist.CLIENT) {
            MinecraftForge.EVENT_BUS.addListener(
                    net.bagaja.worldcustomizer.client.CreateWorldScreenHandler::onScreenInit);
        }
    }
}