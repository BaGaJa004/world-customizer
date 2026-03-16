package net.bagaja.worldcustomizer.worldgen;

import com.mojang.serialization.Codec;
import net.bagaja.worldcustomizer.config.OreSettings;
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
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public record OreGenModifier() implements BiomeModifier {

    public static final DeferredRegister<Codec<? extends BiomeModifier>> MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, "worldcustomizer");

    public static final RegistryObject<Codec<OreGenModifier>> CODEC =
            MODIFIER_SERIALIZERS.register("ore_gen", () -> Codec.unit(new OreGenModifier()));

    // Replace targets
    private static final RuleTest BASE_STONE_OVERWORLD = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
    private static final RuleTest STONE_ONLY           = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    private static final RuleTest DEEPSLATE_ONLY       = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    private static final RuleTest NETHERRACK           = new BlockMatchTest(Blocks.NETHERRACK);
    private static final RuleTest BASE_STONE_NETHER    = new TagMatchTest(BlockTags.BASE_STONE_NETHER);

    // Nether biome path fragments for filtering
    private static final List<String> NETHER_BIOME_PATHS = List.of(
            "nether_wastes", "soul_sand_valley", "crimson_forest",
            "warped_forest", "basalt_deltas"
    );

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase != Phase.REMOVE) return;

        String biomePath = biome.unwrapKey()
                .map(k -> k.location().getPath())
                .orElse("");

        boolean isNether = NETHER_BIOME_PATHS.stream().anyMatch(biomePath::contains);
        boolean isOverworld = !isNether;

        // ── Remove vanilla features ─────────────────────────────
        builder.getGenerationSettings()
                .getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES)
                .removeIf(holder -> {
                    var key = holder.unwrapKey();
                    if (key.isEmpty()) return false;
                    String path = key.get().location().getPath();
                    if (isOverworld) return path.contains("ore_coal")   || path.contains("ore_iron")
                            || path.contains("ore_gold")    || path.contains("ore_diamond")
                            || path.contains("ore_redstone")|| path.contains("ore_lapis")
                            || path.contains("ore_copper")  || path.contains("ore_emerald")
                            || path.contains("ore_dirt")    || path.contains("ore_gravel")
                            || path.contains("ore_granite") || path.contains("ore_diorite")
                            || path.contains("ore_andesite")|| path.contains("ore_tuff")
                            || path.equals("monster_room")
                            || path.equals("monster_room_deep");
                    if (isNether) return path.contains("ore_nether_gold")
                            || path.contains("ore_quartz")
                            || path.contains("ore_ancient_debris");
                    return false;
                });

        if (isOverworld) {
            // ── Overworld Ores ──────────────────────────────────
            addDualOre(builder, Blocks.COAL_ORE.defaultBlockState(),    Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(),
                    OreSettings.COAL_VEIN_SIZE,    OreSettings.COAL_VEINS_PER_CHUNK,
                    OreSettings.COAL_MIN_HEIGHT,   OreSettings.COAL_MAX_HEIGHT);
            addDualOre(builder, Blocks.IRON_ORE.defaultBlockState(),    Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(),
                    OreSettings.IRON_VEIN_SIZE,    OreSettings.IRON_VEINS_PER_CHUNK,
                    OreSettings.IRON_MIN_HEIGHT,   OreSettings.IRON_MAX_HEIGHT);
            addDualOre(builder, Blocks.GOLD_ORE.defaultBlockState(),    Blocks.DEEPSLATE_GOLD_ORE.defaultBlockState(),
                    OreSettings.GOLD_VEIN_SIZE,    OreSettings.GOLD_VEINS_PER_CHUNK,
                    OreSettings.GOLD_MIN_HEIGHT,   OreSettings.GOLD_MAX_HEIGHT);
            addDualOre(builder, Blocks.DIAMOND_ORE.defaultBlockState(), Blocks.DEEPSLATE_DIAMOND_ORE.defaultBlockState(),
                    OreSettings.DIAMOND_VEIN_SIZE,    OreSettings.DIAMOND_VEINS_PER_CHUNK,
                    OreSettings.DIAMOND_MIN_HEIGHT,   OreSettings.DIAMOND_MAX_HEIGHT);
            addDualOre(builder, Blocks.REDSTONE_ORE.defaultBlockState(),Blocks.DEEPSLATE_REDSTONE_ORE.defaultBlockState(),
                    OreSettings.REDSTONE_VEIN_SIZE,   OreSettings.REDSTONE_VEINS_PER_CHUNK,
                    OreSettings.REDSTONE_MIN_HEIGHT,  OreSettings.REDSTONE_MAX_HEIGHT);
            addDualOre(builder, Blocks.LAPIS_ORE.defaultBlockState(),   Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(),
                    OreSettings.LAPIS_VEIN_SIZE,   OreSettings.LAPIS_VEINS_PER_CHUNK,
                    OreSettings.LAPIS_MIN_HEIGHT,  OreSettings.LAPIS_MAX_HEIGHT);
            addDualOre(builder, Blocks.COPPER_ORE.defaultBlockState(),  Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState(),
                    OreSettings.COPPER_VEIN_SIZE,  OreSettings.COPPER_VEINS_PER_CHUNK,
                    OreSettings.COPPER_MIN_HEIGHT, OreSettings.COPPER_MAX_HEIGHT);
            addDualOre(builder, Blocks.EMERALD_ORE.defaultBlockState(), Blocks.DEEPSLATE_EMERALD_ORE.defaultBlockState(),
                    OreSettings.EMERALD_VEIN_SIZE,    OreSettings.EMERALD_VEINS_PER_CHUNK,
                    OreSettings.EMERALD_MIN_HEIGHT,   OreSettings.EMERALD_MAX_HEIGHT);

            // ── Stone Variants (replace any base stone) ─────────
            addSingleOre(builder, BASE_STONE_OVERWORLD, Blocks.DIRT.defaultBlockState(),
                    OreSettings.DIRT_VEIN_SIZE,     OreSettings.DIRT_VEINS_PER_CHUNK,
                    OreSettings.DIRT_MIN_HEIGHT,    OreSettings.DIRT_MAX_HEIGHT);
            addSingleOre(builder, BASE_STONE_OVERWORLD, Blocks.GRAVEL.defaultBlockState(),
                    OreSettings.GRAVEL_VEIN_SIZE,   OreSettings.GRAVEL_VEINS_PER_CHUNK,
                    OreSettings.GRAVEL_MIN_HEIGHT,  OreSettings.GRAVEL_MAX_HEIGHT);
            addSingleOre(builder, BASE_STONE_OVERWORLD, Blocks.GRANITE.defaultBlockState(),
                    OreSettings.GRANITE_VEIN_SIZE,  OreSettings.GRANITE_VEINS_PER_CHUNK,
                    OreSettings.GRANITE_MIN_HEIGHT, OreSettings.GRANITE_MAX_HEIGHT);
            addSingleOre(builder, BASE_STONE_OVERWORLD, Blocks.DIORITE.defaultBlockState(),
                    OreSettings.DIORITE_VEIN_SIZE,  OreSettings.DIORITE_VEINS_PER_CHUNK,
                    OreSettings.DIORITE_MIN_HEIGHT, OreSettings.DIORITE_MAX_HEIGHT);
            addSingleOre(builder, BASE_STONE_OVERWORLD, Blocks.ANDESITE.defaultBlockState(),
                    OreSettings.ANDESITE_VEIN_SIZE,  OreSettings.ANDESITE_VEINS_PER_CHUNK,
                    OreSettings.ANDESITE_MIN_HEIGHT, OreSettings.ANDESITE_MAX_HEIGHT);
            addSingleOre(builder, BASE_STONE_OVERWORLD, Blocks.TUFF.defaultBlockState(),
                    OreSettings.TUFF_VEIN_SIZE,     OreSettings.TUFF_VEINS_PER_CHUNK,
                    OreSettings.TUFF_MIN_HEIGHT,    OreSettings.TUFF_MAX_HEIGHT);
            addSingleOre(builder, BASE_STONE_OVERWORLD, Blocks.CALCITE.defaultBlockState(),
                    OreSettings.CALCITE_VEIN_SIZE,    OreSettings.CALCITE_VEINS_PER_CHUNK,
                    OreSettings.CALCITE_MIN_HEIGHT,   OreSettings.CALCITE_MAX_HEIGHT);
            addSingleOre(builder, BASE_STONE_OVERWORLD, Blocks.DEEPSLATE.defaultBlockState(),
                    OreSettings.DEEPSLATE_VEIN_SIZE,  OreSettings.DEEPSLATE_VEINS_PER_CHUNK,
                    OreSettings.DEEPSLATE_MIN_HEIGHT, OreSettings.DEEPSLATE_MAX_HEIGHT);

            // ── Dungeons ────────────────────────────────────────────────
            addDungeon(builder, OreSettings.DUNGEON_COUNT,
                    VerticalAnchor.absolute(0), VerticalAnchor.top());
            addDungeon(builder, OreSettings.DUNGEON_COUNT_DEEP,
                    VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(-1));
        }

        if (isNether) {
            // ── Nether Ores ─────────────────────────────────────
            addSingleOre(builder, NETHERRACK, Blocks.NETHER_GOLD_ORE.defaultBlockState(),
                    OreSettings.NETHER_GOLD_VEIN_SIZE,   OreSettings.NETHER_GOLD_VEINS_PER_CHUNK,
                    OreSettings.NETHER_GOLD_MIN_HEIGHT,  OreSettings.NETHER_GOLD_MAX_HEIGHT);
            addSingleOre(builder, NETHERRACK, Blocks.NETHER_QUARTZ_ORE.defaultBlockState(),
                    OreSettings.NETHER_QUARTZ_VEIN_SIZE,  OreSettings.NETHER_QUARTZ_VEINS_PER_CHUNK,
                    OreSettings.NETHER_QUARTZ_MIN_HEIGHT, OreSettings.NETHER_QUARTZ_MAX_HEIGHT);
            // Ancient debris uses SCATTERED_ORE (avoids clusters touching exposed air)
            addScatteredOre(builder, BASE_STONE_NETHER, Blocks.ANCIENT_DEBRIS.defaultBlockState(),
                    OreSettings.ANCIENT_DEBRIS_VEIN_SIZE,   OreSettings.ANCIENT_DEBRIS_VEINS_PER_CHUNK,
                    OreSettings.ANCIENT_DEBRIS_MIN_HEIGHT,   OreSettings.ANCIENT_DEBRIS_MAX_HEIGHT);

            if (!OreSettings.CAVES_ENABLED) {
                builder.getGenerationSettings()
                        .getCarvers(GenerationStep.Carving.AIR)
                        .removeIf(holder -> {
                            var key = holder.unwrapKey();
                            if (key.isEmpty()) return false;
                            return key.get().location().getPath().equals("nether_cave");
                        });
            }
        }

        if (isOverworld && (!OreSettings.CAVES_ENABLED || !OreSettings.RAVINES_ENABLED)) {
            builder.getGenerationSettings()
                    .getCarvers(GenerationStep.Carving.AIR)
                    .removeIf(holder -> {
                        var key = holder.unwrapKey();
                        if (key.isEmpty()) return false;
                        String path = key.get().location().getPath();
                        if (!OreSettings.CAVES_ENABLED &&
                                (path.equals("cave") || path.equals("cave_extra_underground"))) return true;
                        if (!OreSettings.RAVINES_ENABLED && path.equals("canyon")) return true;
                        return false;
                    });
        }

        if (isOverworld) {
            if (!OreSettings.LAKES_ENABLED)
                builder.getGenerationSettings()
                        .getFeatures(GenerationStep.Decoration.LAKES).clear();

            if (!OreSettings.LOCAL_MODIFICATIONS_ENABLED)
                builder.getGenerationSettings()
                        .getFeatures(GenerationStep.Decoration.LOCAL_MODIFICATIONS).clear();

            if (!OreSettings.SURFACE_STRUCTURES_ENABLED)
                builder.getGenerationSettings()
                        .getFeatures(GenerationStep.Decoration.SURFACE_STRUCTURES).clear();

            if (!OreSettings.UNDERGROUND_DECORATION_ENABLED)
                builder.getGenerationSettings()
                        .getFeatures(GenerationStep.Decoration.UNDERGROUND_DECORATION).clear();

            if (!OreSettings.FLUID_SPRINGS_ENABLED)
                builder.getGenerationSettings()
                        .getFeatures(GenerationStep.Decoration.FLUID_SPRINGS).clear();

            if (!OreSettings.VEGETAL_DECORATION_ENABLED)
                builder.getGenerationSettings()
                        .getFeatures(GenerationStep.Decoration.VEGETAL_DECORATION).clear();
        }
    }

    // Two-block ore (stone + deepslate variant)
    private void addDualOre(ModifiableBiomeInfo.BiomeInfo.Builder builder,
                            BlockState stoneOre, BlockState deepslateOre,
                            int veinSize, int veinsPerChunk, int minY, int maxY) {
        if (veinsPerChunk <= 0) return;
        List<OreConfiguration.TargetBlockState> targets = List.of(
                OreConfiguration.target(STONE_ONLY, stoneOre),
                OreConfiguration.target(DEEPSLATE_ONLY, deepslateOre)
        );
        addFeature(builder, Feature.ORE, new OreConfiguration(targets, veinSize), veinsPerChunk, minY, maxY);
    }

    // Single-block ore (stone variants, nether ores)
    private void addSingleOre(ModifiableBiomeInfo.BiomeInfo.Builder builder,
                              RuleTest replaceTarget, BlockState block,
                              int veinSize, int veinsPerChunk, int minY, int maxY) {
        if (veinsPerChunk <= 0) return;
        addFeature(builder, Feature.ORE,
                new OreConfiguration(replaceTarget, block, veinSize),
                veinsPerChunk, minY, maxY);
    }

    private void addDungeon(ModifiableBiomeInfo.BiomeInfo.Builder builder,
                            int count, VerticalAnchor minY, VerticalAnchor maxY) {
        if (count <= 0) return;

        PlacedFeature placed = new PlacedFeature(
                Holder.direct(new ConfiguredFeature<>(Feature.MONSTER_ROOM,
                        net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration.INSTANCE)),
                List.of(
                        CountPlacement.of(count),
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(minY, maxY),
                        BiomeFilter.biome()
                )
        );

        builder.getGenerationSettings().addFeature(
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES,
                Holder.direct(placed)
        );
    }

    // Scattered ore (ancient debris — never exposes to air)
    private void addScatteredOre(ModifiableBiomeInfo.BiomeInfo.Builder builder,
                                 RuleTest replaceTarget, BlockState block,
                                 int veinSize, int veinsPerChunk, int minY, int maxY) {
        if (veinsPerChunk <= 0) return;
        addFeature(builder, Feature.SCATTERED_ORE,
                new OreConfiguration(replaceTarget, block, veinSize, 1.0f),
                veinsPerChunk, minY, maxY);
    }

    private void addFeature(ModifiableBiomeInfo.BiomeInfo.Builder builder,
                            net.minecraft.world.level.levelgen.feature.Feature<OreConfiguration> featureType,
                            OreConfiguration config,
                            int veinsPerChunk, int minY, int maxY) {
        PlacedFeature placed = new PlacedFeature(
                Holder.direct(new ConfiguredFeature<>(featureType, config)),
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