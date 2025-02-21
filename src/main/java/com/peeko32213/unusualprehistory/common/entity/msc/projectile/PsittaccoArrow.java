package com.peeko32213.unusualprehistory.common.entity.msc.projectile;

import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.PlayMessages;

public class PsittaccoArrow extends AbstractArrow {

    private boolean bounce;

    public PsittaccoArrow(EntityType<PsittaccoArrow> psittaccoArrowEntityType, Level pLevel) {
        super(psittaccoArrowEntityType, pLevel);
    }

    public PsittaccoArrow(double pX, double pY, double pZ, Level pLevel) {
        super(UPEntities.PSITTACCO_ARROW.get(), pX, pY, pZ, pLevel);
    }

    public PsittaccoArrow(LivingEntity pShooter, Level pLevel) {
        super(UPEntities.PSITTACCO_ARROW.get(), pShooter, pLevel);
        this.bounce = true;
    }

    public PsittaccoArrow(PlayMessages.SpawnEntity spawnEntity, Level pLevel) {
        this(UPEntities.PSITTACCO_ARROW.get(), pLevel);
    }

    public PsittaccoArrow(LivingEntity pShooter, Level pLevel, boolean bounce, AbstractArrow.Pickup pickup) {
        this(pShooter, pLevel);
        this.bounce = bounce;
        this.pickup = pickup;
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        launchMultipleProjectile(12);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        launchMultipleProjectile(12);
    }


    public void launchMultipleProjectile(int count) {
        for (int c = 0; c < count && bounce; c++) {
            PsittaccoArrow projectile = new PsittaccoArrow((LivingEntity) this.getOwner(), this.level(), false, Pickup.DISALLOWED);
            float bodyFacingAngle = (float) Math.toRadians((c * (360F / count)));
            double sx = getX() + Math.sin((float) (bodyFacingAngle));
            double sy = getY() + (this.getBbHeight());
            double sz = getZ() + Math.cos((bodyFacingAngle));
            int radius = random.nextInt(2,8);

            double tx = sx + this.getBbWidth() + (radius * Math.sin((bodyFacingAngle)));
            double tz = sz + this.getBbWidth() + (radius * Math.cos((bodyFacingAngle)));
            double ty = this.level().getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) tx, (int) tz);

            double shootPosx = tx - sx;
            double shootPosy = ty - sy;
            double shootPosz = tz - sz;

            //this.playSound(SoundEvents.EXPERIENCE_BOTTLE_THROW, this.getSoundVolume(), (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 1.0F);
            projectile.moveTo(sx, sy, sz, getYRot(), getXRot());
            //projectile.moveTo(tx, ty, tz, getYRot(), getXRot());
            projectile.shoot(shootPosx, shootPosy, shootPosz, 1.0F, 1.0F);
            this.level().addFreshEntity(projectile);
        }
    }


    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(UPItems.PSITTACCO_ARROW.get());
    }
}
