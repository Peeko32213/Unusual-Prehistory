package com.peeko32213.unusualprehistory.mixin.client;

import com.peeko32213.unusualprehistory.client.sound.KimmeridgebrachypteraeschnidiumSoundInstance;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.flying.KimmeridgebrachypteraeschnidiumEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Inject(at = @At(value = "HEAD"), method = "postAddEntitySoundInstance")
    private void handleAddMob(Entity entity, CallbackInfo ci) {
        if (entity instanceof KimmeridgebrachypteraeschnidiumEntity kimmer) {
            Minecraft.getInstance().getSoundManager().queueTickingSound(new KimmeridgebrachypteraeschnidiumSoundInstance(kimmer));
        }
    }
}
