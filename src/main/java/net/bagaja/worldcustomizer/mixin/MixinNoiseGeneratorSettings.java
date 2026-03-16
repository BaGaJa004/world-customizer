package net.bagaja.worldcustomizer.mixin;

import net.bagaja.worldcustomizer.config.OreSettings;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseGeneratorSettings.class)
public class MixinNoiseGeneratorSettings {

    @Inject(method = "defaultFluid", at = @At("RETURN"), cancellable = true)
    private void overrideDefaultFluid(CallbackInfoReturnable<BlockState> cir) {
        NoiseGeneratorSettings self = (NoiseGeneratorSettings)(Object)this;
        BlockState defaultBlock = self.defaultBlock();

        if (defaultBlock.is(Blocks.STONE)) {
            // Overworld
            cir.setReturnValue(switch (OreSettings.OVERWORLD_FLUID) {
                case LAVA -> Blocks.LAVA.defaultBlockState();
                case AIR  -> Blocks.AIR.defaultBlockState();
                default   -> Blocks.WATER.defaultBlockState();
            });
        } else if (defaultBlock.is(Blocks.NETHERRACK)) {
            // Nether
            cir.setReturnValue(switch (OreSettings.NETHER_FLUID) {
                case WATER -> Blocks.WATER.defaultBlockState();
                case AIR   -> Blocks.AIR.defaultBlockState();
                default    -> Blocks.LAVA.defaultBlockState();
            });
        }
    }
}