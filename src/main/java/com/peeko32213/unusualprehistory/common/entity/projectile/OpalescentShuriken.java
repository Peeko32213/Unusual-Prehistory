package com.peeko32213.unusualprehistory.common.entity.projectile;

import com.peeko32213.unusualprehistory.core.registry.UPDamageTypes;
import com.peeko32213.unusualprehistory.core.registry.entities.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkHooks;

@OnlyIn(value = Dist.CLIENT, _interface = ItemSupplier.class)

public class OpalescentShuriken extends AbstractArrow implements ItemSupplier {

    private int life;

    public OpalescentShuriken(EntityType<? extends OpalescentShuriken> pEntityType, Level level) {
        super(pEntityType, level);
    }

    public OpalescentShuriken(Level level, LivingEntity shooter) {
        super(UPEntities.OPALESCENT_SHURIKEN.get(), shooter, level);
    }

    @Override
    protected void doPostHurtEffects(LivingEntity entity) {
        super.doPostHurtEffects(entity);
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity target = result.getEntity();
        Entity shooter = this.getOwner();

        float motion = (float) this.getDeltaMovement().length();
        int damage = Mth.ceil(Mth.clamp((double) motion * 0.5F * this.getBaseDamage(), 0.0D, 2.147483647E9D));

        DamageSource damagesource = UPDamageTypes.shuriken(this.level(), this, shooter);

        if (shooter instanceof LivingEntity living) {
            living.setLastHurtMob(target);
        }

        boolean isEnderman = target.getType() == EntityType.ENDERMAN;
        if (this.isOnFire() && !isEnderman) {
            target.setSecondsOnFire(5);
        }

        if (target.hurt(damagesource, (float) damage)) {
            if (isEnderman) return;

            if (target instanceof LivingEntity livingTarget) {

                if (!this.level().isClientSide() && shooter instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingTarget, shooter);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity) shooter, livingTarget);
                }

                this.doPostHurtEffects(livingTarget);
                if (livingTarget != shooter && livingTarget instanceof Player && shooter instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer) shooter).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

            }

            this.playSound(this.getDefaultHitGroundSoundEvent(), 1.0F, 1.25F / (this.random.nextFloat() * 0.2F + 0.9F));
        }
        else {
            target.setRemainingFireTicks(target.getRemainingFireTicks());
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level().isClientSide() && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }
                this.discard();
            }
        }
    }

    @Override
    public double getBaseDamage() {
        return 4.0D;
    }

    protected ItemStack getPickupItem() {
        return new ItemStack(UPItems.OPALESCENT_SHURIKEN.get());
    }

    public ItemStack getItem() {
        return new ItemStack(UPItems.OPALESCENT_SHURIKEN.get());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected void tickDespawn() {
        ++this.life;
        if (this.life >= 5000) {
            this.discard();
        }
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.WOOD_BREAK;
    }

    protected float getWaterInertia() {
        return 0.99F;
    }
}
