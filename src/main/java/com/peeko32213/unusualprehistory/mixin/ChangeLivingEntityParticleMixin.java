package com.peeko32213.unusualprehistory.mixin;

import com.peeko32213.unusualprehistory.core.events.LivingEntityChangeParticleEvent;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class ChangeLivingEntityParticleMixin {


    @Redirect(method = "tickEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", ordinal = 0))
    private void UnusualPrehistory$changeLivingEntityParticleEvent(Level instance, ParticleOptions pParticleData, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
        LivingEntity entity = (LivingEntity) (Object) this;

        LivingEntityChangeParticleEvent event = new LivingEntityChangeParticleEvent(
                entity,
                pParticleData,
                pX,
                pY,
                pZ,
                pXSpeed,
                pYSpeed,
                pZSpeed
        );

        MinecraftForge.EVENT_BUS.post(event);

        if (event.isCanceled()) {
            return;
        }

        instance.addParticle(
                event.getParticleData(),
                event.getX(),
                event.getY(),
                event.getZ(),
                event.getXSpeed(),
                event.getYSpeed(),
                event.getZSpeed()
        );
    }

}
