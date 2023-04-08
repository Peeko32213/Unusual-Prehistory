package com.peeko32213.unusualprehistory.common.entity.msc.util.dino;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class EntityWorldSpawnable extends LivingEntity implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final Logger LOGGER = LogManager.getLogger();
    public EntityWorldSpawnable(EntityType<? extends LivingEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }



    public static AttributeSupplier.Builder bakeAttributes() {
        return Mob.createMobAttributes()
                //HEALTH
                .add(Attributes.MAX_HEALTH, 10.0D)
                //SPEED
                .add(Attributes.MOVEMENT_SPEED, 0D);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot p_21127_) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot p_21036_, ItemStack p_21037_) {

    }

    @Override
    protected void onInsideBlock(BlockState p_20005_) {

    }



    @Override
    public boolean canCollideWith(Entity p_20303_) {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        LOGGER.info("source " + source);
        if(source == DamageSource.IN_WALL || source == DamageSource.GENERIC)
        {
            return false;
        }


        return super.hurt(source, damage);
    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        return PlayState.CONTINUE;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
