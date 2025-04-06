package com.peeko32213.unusualprehistory.data.server.world;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.core.registry.world.UPBiomes;
import com.teamabnormals.blueprint.common.world.modification.chunk.ChunkGeneratorModifierProvider;
import com.teamabnormals.blueprint.common.world.modification.chunk.modifiers.SurfaceRuleModifier;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.world.level.levelgen.SurfaceRules.*;

public class UPChunkGeneratorModifierProvider extends ChunkGeneratorModifierProvider {

    public UPChunkGeneratorModifierProvider(PackOutput output, CompletableFuture<Provider> provider) {
        super(UnusualPrehistory.MODID, output, provider);
    }

    @Override
    protected void registerEntries(Provider provider) {
        ConditionSource isPetrifiedForest = isBiome(UPBiomes.PETRIFIED_FOREST);
        ConditionSource sandstoneCondition1 = (SurfaceRules.stoneDepthCheck(0, false, 0, CaveSurface.CEILING));
        ConditionSource sandstoneCondition2 = (SurfaceRules.stoneDepthCheck(0, false, 30, CaveSurface.FLOOR));

        RuleSource petrifiedSandstone1 = sequence(ifTrue(sandstoneCondition1, state(Blocks.GRANITE.defaultBlockState())));
        RuleSource petrifiedSandstone2 = sequence(ifTrue(sandstoneCondition2, state(Blocks.GRANITE.defaultBlockState())));

        RuleSource petrifiedSand = sequence(state(Blocks.SAND.defaultBlockState()));
        RuleSource petrifiedMud = sequence(ifTrue(ON_FLOOR, state(Blocks.PACKED_MUD.defaultBlockState())));

        RuleSource petrifiedSurface1 = sequence(petrifiedSandstone1, petrifiedSand);
        RuleSource petrifiedSurface2 = sequence(petrifiedSandstone2, petrifiedSand);

        this.entry("unusualprehistory_surface_rules").selects("minecraft:overworld")
                .addModifier(new SurfaceRuleModifier(ifTrue(abovePreliminarySurface(), ifTrue(isPetrifiedForest, sequence(petrifiedSurface2, petrifiedSurface1))), false))
        ;

    }

    private static ConditionSource noiseRange(double low, double high) {
        return noiseCondition(Noises.SURFACE, low / 8.25D, high / 8.25D);
    }

    private static ConditionSource surfaceNoiseAbove(double noise) {
        return noiseCondition(Noises.SURFACE, noise / 8.25D, Double.MAX_VALUE);
    }
}
