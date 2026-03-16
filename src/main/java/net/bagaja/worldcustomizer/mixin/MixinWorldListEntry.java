package net.bagaja.worldcustomizer.mixin;

import net.bagaja.worldcustomizer.config.OreSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Files;
import java.nio.file.Path;

@Mixin(WorldSelectionList.WorldListEntry.class)
public class MixinWorldListEntry {

    @Shadow @Final LevelSummary summary;

    @Inject(method = "joinWorld", at = @At("HEAD"))
    private void loadSettingsBeforeJoin(CallbackInfo ci) {
        try {
            // Build the path to our JSON directly from the level ID
            // without needing the server to be running
            Path savesDir = Minecraft.getInstance()
                    .getLevelSource()
                    .getBaseDir();

            Path jsonPath = savesDir
                    .resolve(summary.getLevelId())
                    .resolve("worldcustomizer.json");

            if (Files.exists(jsonPath)) {
                OreSettings.loadFromPath(jsonPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}