package com.peeko32213.unusualprehistory.client.animation;

import com.peeko32213.unusualprehistory.common.entity.custom.base.PrehistoricEntity;
import net.minecraft.Util;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import software.bernie.geckolib.GeckoLibException;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoModel;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationProcessor;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.molang.MolangParser;
import software.bernie.geckolib.core.molang.MolangQueries;
import software.bernie.geckolib.core.object.DataTicket;
import software.bernie.geckolib.loading.object.BakedAnimations;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Optional;
import java.util.function.BiConsumer;

public abstract class ServerModel<T extends GeoAnimatable> implements CoreGeoModel<T> {
    private final AnimationProcessor<T> processor = new AnimationProcessor<>(this);

    private BakedGeoModel currentModel = null;
    private double animTime;
    private double lastGameTickTime;
    private long lastRenderedInstance = -1;
    private ServerLevel serverLevel;

    public ServerModel(ServerLevel level) {
        this.serverLevel = level;
    }
    /**
     * Returns the resource path for the {@link BakedGeoModel} (model json file) to render based on the provided animatable
     */
    public abstract ResourceLocation getModelResource(T animatable);

    /**
     * Returns the resource path for the texture file to render based on the provided animatable
     */
    public abstract ResourceLocation getTextureResource(T animatable);

    /**
     * Returns the resourcepath for the {@link BakedAnimations} (animation json file) to use for animations based on the provided animatable
     */
    public abstract ResourceLocation getAnimationResource(T animatable);

    /**
     * Override this and return true if Geckolib should crash when attempting to animate the model, but fails to find a bone.<br>
     * By default, GeckoLib will just gracefully ignore a missing bone, which might cause oddities with incorrect models or mismatching variables.<br>
     */
    public boolean crashIfBoneMissing() {
        return false;
    }

    @Override
    public final BakedGeoModel getBakedGeoModel(String location) {
        return getBakedModel(new ResourceLocation(location));
    }

    /**
     * Get the baked geo model object used for rendering from the given resource path
     */
    public BakedGeoModel getBakedModel(ResourceLocation location) {
        BakedGeoModel model = ServerResourceCache.getBakedModels().get(location);

        if (model == null)
            throw new GeckoLibException(location, "Unable to find model");

        if (model != this.currentModel) {
            this.getAnimationProcessor().setActiveModel(model);
            this.currentModel = model;
        }

        return this.currentModel;
    }

    /**
     * Gets a bone from this model by name
     * @param name The name of the bone
     * @return An {@link Optional} containing the {@link software.bernie.geckolib.cache.object.GeoBone} if one matches, otherwise an empty Optional
     */
    public Optional<software.bernie.geckolib.cache.object.GeoBone> getBone(String name) {
        return Optional.ofNullable((GeoBone)getAnimationProcessor().getBone(name));
    }

    /**
     * Get the baked animation object used for rendering from the given resource path
     */

    @Override
    public Animation getAnimation(T animatable, String name) {
        ResourceLocation location = getAnimationResource(animatable);
        BakedAnimations bakedAnimations = ServerResourceCache.getBakedAnimations().get(location);

        if (bakedAnimations == null)
            throw new GeckoLibException(location, "Unable to find animation.");

        return bakedAnimations.getAnimation(name);
    }
    @Override
    public AnimationProcessor<T> getAnimationProcessor() {
        return this.processor;
    }

    /**
     * Add additional {@link DataTicket DataTickets} to the {@link AnimationState} to be handled by your animation handler at render time
     * @param animatable The animatable instance currently being animated
     * @param instanceId The unique instance id of the animatable being animated
     * @param dataConsumer The DataTicket + data consumer to be added to the AnimationState
     */
    public void addAdditionalStateData(T animatable, long instanceId, BiConsumer<DataTicket<T>, T> dataConsumer) {}

    @Override
    public final void handleAnimations(T animatable, long instanceId, AnimationState<T> animationState) {

        long time = Util.getEpochMillis();
        AnimatableManager<T> animatableManager = animatable.getAnimatableInstanceCache().getManagerForId(instanceId);
        Double currentTick = animationState.getData(DataTickets.TICK);

        if (currentTick == null)
            currentTick = animatable instanceof Entity entity ? (double)entity.tickCount : RenderUtils.getCurrentTick();

        if (animatableManager.getFirstTickTime() == -1)
            animatableManager.startedAt(currentTick + time);

        double currentFrameTime = animatable instanceof Entity ? currentTick + time : currentTick - animatableManager.getFirstTickTime();
        boolean isReRender = !animatableManager.isFirstTick() && currentFrameTime == animatableManager.getLastUpdateTime();

        if (isReRender && instanceId == this.lastRenderedInstance)
            return;

        if (!serverLevel.getServer().isStopped()  || animatable.shouldPlayAnimsWhileGamePaused()) {
            animatableManager.updatedAt(currentFrameTime);
            double lastUpdateTime = animatableManager.getLastUpdateTime();
            this.animTime += lastUpdateTime - this.lastGameTickTime;
            this.lastGameTickTime = lastUpdateTime;
        }

        animationState.animationTick = this.animTime;
        this.lastRenderedInstance = instanceId;
        AnimationProcessor<T> processor = getAnimationProcessor();

        processor.preAnimationSetup(animationState.getAnimatable(), this.animTime);

        if (!processor.getRegisteredBones().isEmpty())
            processor.tickAnimation(animatable, this, animatableManager, this.animTime, animationState, crashIfBoneMissing());

        setCustomAnimations(animatable, instanceId, animationState);
    }
    @Override
    public void applyMolangQueries(T animatable, double animTime) {
        MolangParser parser = MolangParser.INSTANCE;

        long time = Util.getEpochMillis();
        parser.setMemoizedValue(MolangQueries.LIFE_TIME, () -> animTime / 20d);
        //parser.setMemoizedValue(MolangQueries.ACTOR_COUNT, serverLevel.entity);
        parser.setMemoizedValue(MolangQueries.TIME_OF_DAY, () -> serverLevel.getDayTime() / 24000f);
        parser.setMemoizedValue(MolangQueries.MOON_PHASE, serverLevel::getMoonPhase);

        if (animatable instanceof Entity entity) {
            //parser.setMemoizedValue(MolangQueries.DISTANCE_FROM_CAMERA, () -> mc.gameRenderer.getMainCamera().getPosition().distanceTo(entity.position()));
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


        if (animatable instanceof Entity entity) {
            parser.setMemoizedValue(UPMolangQueries.IS_UNDERWATER, () -> RenderUtils.booleanToFloat(entity.isUnderWater()));
            parser.setMemoizedValue(UPMolangQueries.IS_IN_LAVA, () -> RenderUtils.booleanToFloat(entity.isInLava()));
            parser.setMemoizedValue(UPMolangQueries.IN_BUBBLE_COLUMN, () -> RenderUtils.booleanToFloat(entity.level().getBlockState(entity.blockPosition()).is(Blocks.BUBBLE_COLUMN)));
            parser.setMemoizedValue(UPMolangQueries.IS_SLIPPERY, () -> RenderUtils.booleanToFloat(entity.getBlockStateOn().is(BlockTags.ICE)));
            parser.setMemoizedValue(UPMolangQueries.IS_SILENT, () -> RenderUtils.booleanToFloat(entity.isSilent()));
            parser.setMemoizedValue(UPMolangQueries.IS_UNDERGROUND, () -> RenderUtils.booleanToFloat(entity.level().getBiome(entity.blockPosition()).is(Tags.Biomes.IS_CAVE) || (!entity.level().canSeeSky(entity.blockPosition()) && entity.blockPosition().getY() < 63)));
            parser.setMemoizedValue(UPMolangQueries.IS_COLD_ENOUGH_TO_SNOW, () -> RenderUtils.booleanToFloat(entity.level().getBiome(entity.blockPosition()).value().coldEnoughToSnow(entity.blockPosition())));
            parser.setMemoizedValue(UPMolangQueries.IS_WARM_ENOUGH_TO_RAIN, () -> RenderUtils.booleanToFloat(entity.level().getBiome(entity.blockPosition()).value().warmEnoughToRain(entity.blockPosition())));

            //parser.setMemoizedValue(UPMolangQueries.ANGLE_TO_CAMERA_X, () -> calculateAngle(calculateDotX(mc.gameRenderer.getMainCamera().getPosition(), entity.position())));
            //parser.setMemoizedValue(UPMolangQueries.ANGLE_TO_CAMERA_Y, () -> calculateAngle(calculateDotY(mc.gameRenderer.getMainCamera().getPosition(), entity.position())));
            //parser.setMemoizedValue(UPMolangQueries.ANGLE_TO_CAMERA_Z, () -> calculateAngle(calculateDotZ(mc.gameRenderer.getMainCamera().getPosition(), entity.position())));

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
                    float lerpBodyRot = Mth.rotLerp(time, livingEntity.yBodyRotO, livingEntity.yBodyRot);
                    float lerpHeadRot = Mth.rotLerp(time, livingEntity.yHeadRotO, livingEntity.yHeadRot);
                    float netHeadYaw = lerpHeadRot - lerpBodyRot;
                    return -netHeadYaw;
                });

                parser.setMemoizedValue(UPMolangQueries.HEAD_PITCH, () -> {
                    float headPitch = Mth.lerp(time, entity.xRotO, entity.getXRot());
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
                parser.setMemoizedValue(UPMolangQueries.HEAD_LOOK, () -> prehistoricEntity.getHeadLook(time));
                parser.setMemoizedValue(UPMolangQueries.TAIL_YAW, () -> prehistoricEntity.getTailYaw(time));
                parser.setMemoizedValue(UPMolangQueries.TAIL_YAW2, () -> Mth.wrapDegrees(prehistoricEntity.getTailYaw(time) - prehistoricEntity.getYaw(time)));
                parser.setMemoizedValue(UPMolangQueries.YAW, () -> prehistoricEntity.getViewYRot(time));
            }
        }

    }
}
