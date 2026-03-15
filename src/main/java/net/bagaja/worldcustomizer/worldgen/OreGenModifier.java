package net.bagaja.worldcustomizer.worldgen;

import com.mojang.serialization.Codec;
import net.bagaja.worldcustomizer.config.OreSettings;
import net.bagaja.worldcustomizer.config.WorldGenConfig;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public record OreGenModifier() implements BiomeModifier {

    // Register the modifier type itself
    public static final DeferredRegister<Codec<? extends BiomeModifier>> MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, "worldcustomizer");

    public static final RegistryObject<Codec<OreGenModifier>> CODEC =
            MODIFIER_SERIALIZERS.register("ore_gen", () -> Codec.unit(new OreGenModifier()));

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.REMOVE) return;

        // --- Remove vanilla ores ---
        builder.getGenerationSettings()
                .getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES)
                .removeIf(holder -> {
                    var key = holder.unwrapKey();
                    if (key.isEmpty()) return false;
                    String path = key.get().location().getPath();
                    return path.contains("ore_coal")
                            || path.contains("ore_iron")
                            || path.contains("ore_gold")
                            || path.contains("ore_diamond")
                            || path.contains("ore_redstone")
                            || path.contains("ore_lapis")
                            || path.contains("ore_copper")
                            || path.contains("ore_emerald");
                });

        // --- Add configured ores ---
        addOre(builder, Blocks.COAL_ORE.defaultBlockState(), Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(),
                OreSettings.COAL_VEIN_SIZE, OreSettings.COAL_VEINS_PER_CHUNK,
                OreSettings.COAL_MIN_HEIGHT, OreSettings.COAL_MAX_HEIGHT);

        addOre(builder, Blocks.IRON_ORE.defaultBlockState(), Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(),
                OreSettings.IRON_VEIN_SIZE, OreSettings.IRON_VEINS_PER_CHUNK,
                OreSettings.IRON_MIN_HEIGHT, OreSettings.IRON_MAX_HEIGHT);

        addOre(builder, Blocks.GOLD_ORE.defaultBlockState(), Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState(),
                OreSettings.GOLD_VEIN_SIZE, OreSettings.GOLD_VEINS_PER_CHUNK,
                OreSettings.GOLD_MIN_HEIGHT, OreSettings.GOLD_MAX_HEIGHT);

        addOre(builder, Blocks.DIAMOND_ORE.defaultBlockState(), Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState(),
                OreSettings.DIAMOND_VEIN_SIZE, OreSettings.DIAMOND_VEINS_PER_CHUNK,
                OreSettings.DIAMOND_MIN_HEIGHT, OreSettings.DIAMOND_MAX_HEIGHT);

        addOre(builder, Blocks.REDSTONE_ORE.defaultBlockState(), Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState(),
                OreSettings.REDSTONE_VEIN_SIZE, OreSettings.REDSTONE_VEINS_PER_CHUNK,
                OreSettings.REDSTONE_MIN_HEIGHT, OreSettings.REDSTONE_MAX_HEIGHT);

        addOre(builder, Blocks.LAPIS_ORE.defaultBlockState(), Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(),
                OreSettings.LAPIS_VEIN_SIZE, OreSettings.LAPIS_VEINS_PER_CHUNK,
                OreSettings.LAPIS_MIN_HEIGHT, OreSettings.LAPIS_MAX_HEIGHT);

        addOre(builder, Blocks.COPPER_ORE.defaultBlockState(), Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState(),
                OreSettings.COPPER_VEIN_SIZE, OreSettings.COPPER_VEINS_PER_CHUNK,
                OreSettings.COPPER_MIN_HEIGHT, OreSettings.COPPER_MAX_HEIGHT);

        addOre(builder, Blocks.EMERALD_ORE.defaultBlockState(), Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState(),
                OreSettings.EMERALD_VEIN_SIZE, OreSettings.EMERALD_VEINS_PER_CHUNK,
                OreSettings.EMERALD_MIN_HEIGHT, OreSettings.EMERALD_MAX_HEIGHT);
    }

    private void addOre(ModifiableBiomeInfo.BiomeInfo.Builder builder,
                        BlockState stoneOre, BlockState deepslateOre,
                        int veinSize, int veinsPerChunk, int minY, int maxY) {
        if (veinsPerChunk <= 0) return;

        List<OreConfiguration.TargetBlockState> targets = List.of(
                OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), stoneOre),
                OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), deepslateOre)
        );

        PlacedFeature placed = new PlacedFeature(
                Holder.direct(new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(targets, veinSize))),
                List.of(
                        CountPlacement.of(veinsPerChunk),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.absolute(minY),
                                VerticalAnchor.absolute(maxY)
                        ),
                        BiomeFilter.biome()
                )
        );

        builder.getGenerationSettings().addFeature(
                GenerationStep.Decoration.UNDERGROUND_ORES,
                Holder.direct(placed)
        );
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC.get();
    }
}