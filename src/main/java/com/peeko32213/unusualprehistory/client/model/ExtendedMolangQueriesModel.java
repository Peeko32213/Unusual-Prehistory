package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.client.animation.UPMolangQueries;
import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.core.molang.MolangQueries;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.util.RenderUtils;

public abstract class ExtendedMolangQueriesModel<T extends GeoAnimatable> extends GeoModel<T> {

    @Override
    public void applyMolangQueries(T animatable, double animTime) {
        MolangParser parser = MolangParser.INSTANCE;
        Minecraft mc = Minecraft.getInstance();
        setDefaultValues(mc, parser, animatable, animTime);
        float partialTick = mc.getPartialTick();
        if (animatable instanceof Entity entity) {
            parser.setMemoizedValue(UPMolangQueries.IS_UNDERWATER, () -> RenderUtils.booleanToFloat(entity.isUnderWater()));
            parser.setMemoizedValue(UPMolangQueries.IS_IN_LAVA, () -> RenderUtils.booleanToFloat(entity.isInLava()));
            parser.setMemoizedValue(UPMolangQueries.IN_BUBBLE_COLUMN, () -> RenderUtils.booleanToFloat(entity.level().getBlockState(entity.blockPosition()).is(Blocks.BUBBLE_COLUMN)));
            parser.setMemoizedValue(UPMolangQueries.IS_SLIPPERY, () -> RenderUtils.booleanToFloat(entity.getBlockStateOn().is(BlockTags.ICE)));
            parser.setMemoizedValue(UPMolangQueries.IS_SILENT, () -> RenderUtils.booleanToFloat(entity.isSilent()));
            parser.setMemoizedValue(UPMolangQueries.IS_UNDERGROUND, () -> RenderUtils.booleanToFloat(entity.level().getBiome(entity.blockPosition()).is(Tags.Biomes.IS_CAVE) || (!entity.level().canSeeSky(entity.blockPosition()) && entity.blockPosition().getY() < 63)));
            parser.setMemoizedValue(UPMolangQueries.IS_COLD_ENOUGH_TO_SNOW, () -> RenderUtils.booleanToFloat(entity.level().getBiome(entity.blockPosition()).value().coldEnoughToSnow(entity.blockPosition())));
            parser.setMemoizedValue(UPMolangQueries.IS_WARM_ENOUGH_TO_RAIN, () -> RenderUtils.booleanToFloat(entity.level().getBiome(entity.blockPosition()).value().warmEnoughToRain(entity.blockPosition())));


            parser.setMemoizedValue(UPMolangQueries.ANGLE_TO_CAMERA_X, () -> calculateAngle(calculateDotX(mc.gameRenderer.getMainCamera().getPosition(), entity.position())));
            parser.setMemoizedValue(UPMolangQueries.ANGLE_TO_CAMERA_Y, () -> calculateAngle(calculateDotY(mc.gameRenderer.getMainCamera().getPosition(), entity.position())));
            parser.setMemoizedValue(UPMolangQueries.ANGLE_TO_CAMERA_Z, () -> calculateAngle(calculateDotZ(mc.gameRenderer.getMainCamera().getPosition(), entity.position())));

            parser.setMemoizedValue(UPMolangQueries.IS_OVERWORLD, () -> RenderUtils.booleanToFloat(entity.level().dimension() == Level.OVERWORLD));
            parser.setMemoizedValue(UPMolangQueries.IS_END, () -> RenderUtils.booleanToFloat(entity.level().dimension() == Level.END));
            parser.setMemoizedValue(UPMolangQueries.IS_NETHER, () -> RenderUtils.booleanToFloat(entity.level().dimension() == Level.NETHER));

            parser.setMemoizedValue(UPMolangQueries.BODY_X_ROTATION, entity::getXRot);
            parser.setMemoizedValue(UPMolangQueries.BODY_Y_ROTATION, entity::getYRot);

            parser.setMemoizedValue(UPMolangQueries.HAS_RIDER, () -> RenderUtils.booleanToFloat(entity.hasPassenger(e -> e instanceof Player)));
            parser.setMemoizedValue(UPMolangQueries.HAS_PLAYER_RIDER, () -> RenderUtils.booleanToFloat(entity.hasPassenger(e -> e instanceof LivingEntity)));
            parser.setMemoizedValue(UPMolangQueries.HAS_PLAYER_RIDER, () -> RenderUtils.booleanToFloat(entity.hasPassenger(e -> e instanceof LivingEntity)));


            parser.setValue(UPMolangQueries.RIDER_LOOK_ANGLE_X, () -> {
                Entity pass = entity.getFirstPassenger();
                if(pass == null) return 0;
                return pass.getLookAngle().x * Mth.RAD_TO_DEG;});

            parser.setValue(UPMolangQueries.RIDER_LOOK_ANGLE_Y, () ->{
                Entity pass = entity.getFirstPassenger();
                if(pass == null) return 0;
                return pass.getLookAngle().y * Mth.RAD_TO_DEG;});

            parser.setValue(UPMolangQueries.RIDER_BODY_X_ROTATION, () ->{
                Entity pass = entity.getFirstPassenger();
                if(pass == null) return 0;
                return pass.getXRot() * Mth.RAD_TO_DEG;});

            parser.setValue(UPMolangQueries.RIDER_BODY_Y_ROTATION, () ->{
                Entity pass = entity.getFirstPassenger();
                if(pass == null) return 0;
                return pass.getYRot() * Mth.RAD_TO_DEG;});

            if (entity instanceof LivingEntity livingEntity) {
                parser.setMemoizedValue(UPMolangQueries.HAS_MOB_EFFECTS, () -> RenderUtils.booleanToFloat((!livingEntity.getActiveEffects().isEmpty())));
                parser.setMemoizedValue(UPMolangQueries.CAN_BREATHE_UNDERWATER, () -> {
                    boolean canBreathUnderWater = livingEntity.canBreatheUnderwater() || MobEffectUtil.hasWaterBreathing(livingEntity);
                    return RenderUtils.booleanToFloat((canBreathUnderWater));
                });
                parser.setMemoizedValue(UPMolangQueries.HAS_ITEM_IN_HAND, () -> RenderUtils.booleanToFloat(!livingEntity.getMainHandItem().isEmpty()));
                //parser.setMemoizedValue(UPMolangQueries.IS_JUMPING, () -> RenderUtils.booleanToFloat(!livingEntity.j));
                parser.setMemoizedValue(UPMolangQueries.IS_FALL_FLYING, () -> RenderUtils.booleanToFloat(livingEntity.isFallFlying()));
                parser.setMemoizedValue(UPMolangQueries.IS_GLOWING, () -> RenderUtils.booleanToFloat(livingEntity.isCurrentlyGlowing()));
                parser.setMemoizedValue(UPMolangQueries.IS_INVISIBLE, () -> RenderUtils.booleanToFloat(livingEntity.isInvisible()));
                parser.setMemoizedValue(UPMolangQueries.IS_IN_POWDER_SNOW, () -> RenderUtils.booleanToFloat(livingEntity.isInPowderSnow));


                parser.setMemoizedValue(UPMolangQueries.NET_HEAD_YAW, () -> {
                    float lerpBodyRot = (float) Mth.rotLerp(partialTick, livingEntity.yBodyRotO, livingEntity.yBodyRot);
                    float lerpHeadRot = (float) Mth.rotLerp(partialTick, livingEntity.yHeadRotO, livingEntity.yHeadRot);
                    float netHeadYaw = lerpHeadRot - lerpBodyRot;
                    return -netHeadYaw;
                });

                parser.setMemoizedValue(UPMolangQueries.HEAD_PITCH, () -> {
                    float headPitch = (float) Mth.lerp(partialTick, entity.xRotO, entity.getXRot());
                    return -headPitch;
                });
            }

            if (entity instanceof Mob mob) {
                parser.setMemoizedValue(UPMolangQueries.HAS_TARGET, () -> RenderUtils.booleanToFloat(mob.getTarget() != null));
                parser.setMemoizedValue(UPMolangQueries.DISTANCE_TO_TARGET, () -> mob.getTarget() == null ? 0 : mob.distanceToSqr(mob.getTarget()));
                parser.setMemoizedValue(UPMolangQueries.TARGET_HEALTH, mob.getTarget() == null ? () -> 0 : mob.getTarget()::getHealth);
                parser.setMemoizedValue(UPMolangQueries.TARGET_MAX_HEALTH, mob.getTarget() == null ? () -> 0 : mob.getTarget()::getMaxHealth);
                parser.setMemoizedValue(UPMolangQueries.TARGET_ARMOR, mob.getTarget() == null ? () -> 0 : mob.getTarget()::getArmorValue);
            }

            if (entity instanceof PrehistoricEntity prehistoricEntity) {
                parser.setMemoizedValue(UPMolangQueries.TAIL_YAW, () -> prehistoricEntity.getTailYaw(partialTick));
                parser.setMemoizedValue(UPMolangQueries.TAIL_YAW2, () -> Mth.wrapDegrees(prehistoricEntity.getTailYaw(partialTick) - prehistoricEntity.getYaw(partialTick)));
                parser.setMemoizedValue(UPMolangQueries.YAW, () -> prehistoricEntity.getViewYRot(partialTick));
                //parser.setMemoizedValue(UPMolangQueries.DISTANCE_TO_GROUND, () -> {
                //    Level level = entity.level();
                //    Vec3 pos = entity.position();
                //    float previousDistance = (float) prehistoricEntity.getPreviousYHeight();
                //    // Calculate the height of the ground at the entity's position
                //    int heightY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, Mth.floor(pos.x), Mth.floor(pos.z));
                //    float groundHeight = (float) heightY;
//
                //    // Calculate the distance from the entity's position to the ground
                //    float distance = (float) (pos.y - groundHeight);
//
                //    // Apply a maximum allowable change in distance per frame
                //    float maxChangePerFrame = 1f; // Adjust as needed
                //    float cappedDistance = previousDistance + Mth.clamp(distance - previousDistance, -maxChangePerFrame, maxChangePerFrame);
//
                //    // Smooth out the transition using interpolation
                //    float smoothedDistance = cubicInterpolation(previousDistance, cappedDistance, 0.1f); // Adjust the interpolation factor as needed
//
                //    // Apply velocity factor to slow down the animation
                //    float velocityFactor = 0.5f; // Adjust the velocity factor as needed
                //    float interpolatedDistance = previousDistance + (smoothedDistance - previousDistance) * velocityFactor;
//
                //    // Update previousDistance for the next iteration
                //    dragonEntity.setPreviousYHeight(interpolatedDistance);
//
                //    return interpolatedDistance;
                //});
            }
        }

    }

    public void setDefaultValues(Minecraft mc, MolangParser parser, T animatable, double animTime) {
        parser.setMemoizedValue(MolangQueries.LIFE_TIME, () -> animTime / 20d);
        parser.setMemoizedValue(MolangQueries.ACTOR_COUNT, mc.level::getEntityCount);
        parser.setMemoizedValue(MolangQueries.TIME_OF_DAY, () -> mc.level.getDayTime() / 24000f);
        parser.setMemoizedValue(MolangQueries.MOON_PHASE, mc.level::getMoonPhase);

        if (animatable instanceof Entity entity) {
            parser.setMemoizedValue(MolangQueries.DISTANCE_FROM_CAMERA, () -> mc.gameRenderer.getMainCamera().getPosition().distanceTo(entity.position()));
            parser.setMemoizedValue(MolangQueries.IS_ON_GROUND, () -> RenderUtils.booleanToFloat(entity.onGround()));
            parser.setMemoizedValue(MolangQueries.IS_IN_WATER, () -> RenderUtils.booleanToFloat(entity.isInWater()));
            parser.setMemoizedValue(MolangQueries.IS_IN_WATER_OR_RAIN, () -> RenderUtils.booleanToFloat(entity.isInWaterRainOrBubble()));

            if (entity instanceof LivingEntity livingEntity) {
                parser.setMemoizedValue(MolangQueries.HEALTH, livingEntity::getHealth);
                parser.setMemoizedValue(MolangQueries.MAX_HEALTH, livingEntity::getMaxHealth);
                parser.setMemoizedValue(MolangQueries.IS_ON_FIRE, () -> RenderUtils.booleanToFloat(livingEntity.isOnFire()));
                parser.setMemoizedValue(MolangQueries.GROUND_SPEED, () -> {
                    Vec3 velocity = livingEntity.getDeltaMovement();

                    return Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
                });
                parser.setMemoizedValue(MolangQueries.YAW_SPEED, () -> livingEntity.getViewYRot((float)animTime - livingEntity.getViewYRot((float)animTime - 0.1f)));
            }
        }
    }

    private float cubicInterpolation(float y0, float y1, float mu) {
        float mu2 = mu * mu;
        float mu3 = mu2 * mu;
        float a0 = -0.5f * y0 + 1.5f * y1 - 1.5f * y1 + 0.5f * y1;
        float a1 = y0 - 2.5f * y1 + 2 * y1 - 0.5f * y1;
        float a2 = -0.5f * y0 + 0.5f * y1;
        float a3 = y1;

        return (a0 * mu3 + a1 * mu2 + a2 * mu + a3);
    }

    private double calculateDotX(Vec3 pos1, Vec3 pos2) {
        double distance = pos1.distanceTo(pos2);
        return (pos2.x - pos1.x) / distance;
    }

    private double calculateDotY(Vec3 pos1, Vec3 pos2) {
        double distance = pos1.distanceTo(pos2);
        return (pos2.y - pos1.y) / distance;
    }

    private double calculateDotZ(Vec3 pos1, Vec3 pos2) {
        double distance = pos1.distanceTo(pos2);
        return (pos2.z - pos1.z) / distance;
    }

    private double calculateAngle(double dot) {
        return Math.acos(dot);
    }
}
