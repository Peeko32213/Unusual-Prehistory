package com.peeko32213.unusualprehistory.core.events;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityDunkleosteus;
import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UnusualPrehistory.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public void onLootLevelEvent(LootingLevelEvent event) {
        DamageSource src = event.getDamageSource();
        if (src != null) {
            if (src.getEntity() instanceof EntityDunkleosteus) {
                event.setLootingLevel(event.getLootingLevel() + 3);
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (!event.getEntity().getUseItem().isEmpty() && event.getSource() != null && event.getSource().getEntity() != null) {
            if (event.getEntity().getUseItem().getItem() == UPItems.TRIKE_SHIELD.get()) {
                if (event.getSource().getEntity() instanceof LivingEntity living) {
                    boolean flag = false;
                    if (living.distanceTo(event.getEntity()) <= 4
                            && !living.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 1));
                        flag = true;
                    }
                }
            }
        }
    }



    @SubscribeEvent
    //cant be canceled
    public void preventClick(PlayerInteractEvent.LeftClickEmpty event){
        if(event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventClick(PlayerInteractEvent.LeftClickBlock event){
        if(event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventClick(PlayerInteractEvent.RightClickBlock event){
        if(event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    //cant be canceled
    public void preventClick(PlayerInteractEvent.RightClickEmpty event){
        if(event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventClick(PlayerInteractEvent.RightClickItem event){
        if(event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventInteract(PlayerInteractEvent.EntityInteract event){
        if(event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())){
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void preventDamage(AttackEntityEvent event){
        if(event.getEntity().hasEffect(UPEffects.PREVENT_CLICK.get())){
            event.setCanceled(true);
        }
    }
}





