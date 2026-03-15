package net.bagaja.worldcustomizer;

import net.bagaja.worldcustomizer.config.WorldGenConfig;
import net.bagaja.worldcustomizer.worldgen.OreGenModifier;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(WorldCustomizerMod.MODID)
public class WorldCustomizerMod {
    public static final String MODID = "worldcustomizer";

    public WorldCustomizerMod() {
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the biome modifier serializer
        OreGenModifier.MODIFIER_SERIALIZERS.register(modBus);

        // Register config
        ModLoadingContext.get().registerConfig(
                ModConfig.Type.SERVER,
                WorldGenConfig.SPEC,
                "worldcustomizer-worldgen.toml"
        );
    }
}