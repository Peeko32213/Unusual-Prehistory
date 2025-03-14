/*package com.peeko32213.unusualprehistory.mixin.client;


import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {


    @Redirect(
            method = "levelEvent",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;levelEvent(ILnet/minecraft/core/BlockPos;I)V",
                    ordinal = 0
            )
    )
    private Particle UnusualPrehistory$redirectSplashPotionAddParticle(

    ) {
        SplashPotionParticleEvent event = new SplashPotionParticleEvent(
                particleOptions,
                new Vec3(x, y, z),
                new Vec3(vx, vy, vz),
                ItemStack.EMPTY
        );

        // Post on Forge’s event bus
        MinecraftForge.EVENT_BUS.post(event);

        // If canceled, skip spawning
        if (event.isCanceled()) {
            return null;
        }

        // Otherwise, spawn with the updated data
        Particle spawnedParticle = instance.addParticle(
                event.getParticleOptions(),
                event.getPosition().x,
                event.getPosition().y,
                event.getPosition().z,
                event.getMotion().x,
                event.getMotion().y,
                event.getMotion().z
        );

        // Optionally, store the result if you want in the event
        event.setResultingParticle(spawnedParticle);
        return spawnedParticle;
    }
}
*/