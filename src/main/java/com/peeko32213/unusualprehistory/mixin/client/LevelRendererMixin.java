/*package com.peeko32213.unusualprehistory.mixin.client;


import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {


    @Inject(method = "levelEvent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;addParticleInternal(Lnet/minecraft/core/particles/ParticleOptions;ZDDDDDD)Lnet/minecraft/client/particle/Particle;", shift = At.Shift.BEFORE))
    private void UnusualPrehistory$redirectSplashPotionAddParticle(

            int pType, BlockPos pPos, int pData, CallbackInfo ci) {
        SplashPotionParticleEvent event = new SplashPotionParticleEvent(
                particleOptions,
                new Vec3(x, y, z),
                new Vec3(vx, vy, vz),
                ItemStack.EMPTY
        );

        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            return null;
        }

        Particle spawnedParticle = instance.addParticle(
                event.getParticleOptions(),
                event.getPosition().x,
                event.getPosition().y,
                event.getPosition().z,
                event.getMotion().x,
                event.getMotion().y,
                event.getMotion().z
        );

        event.setResultingParticle(spawnedParticle);
        return spawnedParticle;
    }
}
*/