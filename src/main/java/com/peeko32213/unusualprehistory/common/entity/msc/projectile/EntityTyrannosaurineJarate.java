package com.peeko32213.unusualprehistory.common.entity.msc.projectile;

import com.peeko32213.unusualprehistory.core.registry.UPEffects;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.Tags;
import net.minecraftforge.network.PlayMessages;
import net.minecraft.world.phys.HitResult;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Iterator;
import java.util.List;

public class EntityTyrannosaurineJarate extends ThrowableItemProjectile implements IAnimatable {
    public int lifetime = 10000;

    protected Item getDefaultItem() {
        return UPItems.T_JARATE.get();
    }


    public EntityTyrannosaurineJarate(EntityType<? extends EntityTyrannosaurineJarate> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public EntityTyrannosaurineJarate(Level pLevel, LivingEntity pShooter) {
        super(UPEntities.T_JARATE.get(), pShooter, pLevel);
    }

    public EntityTyrannosaurineJarate(Level pLevel, double pX, double pY, double pZ) {
        super(UPEntities.T_JARATE.get(), pX, pY, pZ, pLevel);
    }


    public void tick() {
        lifetime --;

        if (lifetime == 0) {
            this.discard();
        }

        super.tick();
    }

    protected void onHit(HitResult hitResult) {
        //hitting anything makes a cloud of piss

        level.playSound((Player)null, this.getX(), this.getY(), this.getZ(), UPSounds.JARATE_EXPLODE.get(), SoundSource.NEUTRAL, 0.5F, 1F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        level.playSound((Player)null, this.getX(), this.getY(), this.getZ(), SoundEvents.PLAYER_SPLASH, SoundSource.NEUTRAL, 0.25F, 0.2F / (level.getRandom().nextFloat() * 0.4F + 0.8F));


        if (!this.level.isClientSide) {
            //this makes the potion effect cloud
            this.level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.level, this.getX(), this.getY() + 0.2F, this.getZ());
            areaeffectcloud.setFixedColor(16646010);
            areaeffectcloud.setRadius(4F);
            areaeffectcloud.setDuration(1);

            this.level.addFreshEntity(areaeffectcloud);
        }

        AABB hitbox = this.getBoundingBox().inflate(3.7, 2.0, 3.7);
        List<LivingEntity> victimsList = this.level.getEntitiesOfClass(LivingEntity.class, hitbox);
        //this applies the actual effect
        if (!victimsList.isEmpty()) {
            for (int i = 0; i < victimsList.size(); i++) {
                LivingEntity pissedUpon = victimsList.get(i);
                pissedUpon.addEffect(new MobEffectInstance(UPEffects.PISSED_UPON.get(), 400));
            }
        }

        for(int $$2 = 0; $$2 < 40; ++$$2) {
            //this makes the glass particles
            this.level.addAlwaysVisibleParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(UPItems.FLASK.get())), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 1.5, ((double)this.random.nextFloat() - 0.5) * 1.5, ((double)this.random.nextFloat() - 0.5) * 1.5);
        }

        super.onHit(hitResult);
    }


    protected void onHitEntity(EntityHitResult hitResult) {
        //smacks the entity too just in case for compat n shit
        super.onHitEntity(hitResult);
        if (hitResult.getEntity() instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(UPEffects.PISSED_UPON.get(), 400));
        }
    }

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().loop("animation.jarate.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
