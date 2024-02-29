package com.peeko32213.unusualprehistory.common.entity.eggs;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityKimmeridgebrachypteraeschnidium;
import com.peeko32213.unusualprehistory.common.entity.IHatchableEntity;
import com.peeko32213.unusualprehistory.common.entity.msc.anim_goal.AnimationAI;
import com.peeko32213.unusualprehistory.common.entity.msc.anim_goal.AnimationHelper;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class DinosaurLandEgg extends BaseDinosaurEgg {

    //private static final EntityDataAccessor<Integer> EGG_SIZE = SynchedEntityData.defineId(DinosaurLandEgg.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HATCH_COUNT = SynchedEntityData.defineId(DinosaurLandEgg.class, EntityDataSerializers.INT);

    private int currentHatchTime;
    private int maxHatchTime;
    private int hatchUpdateTimer;

    private LivingEntity dinosaur;
    private final int MAX_HATCH_COUNT = 2;
    private  final Map<BlockPos, Integer> cachedBlockStatePos = new HashMap<>();
    private  final Map<BlockPos, Integer> cachedLightEmissionPos = new HashMap<>();

    private static final AnimationHelper EGG_HATCH = AnimationHelper.playAnimationWController("egg_hatch", "animation.hatch");

    public DinosaurLandEgg(Level pLevel, double pX, double pY, double pZ, Supplier<? extends EntityType<?>> toSpawn, EggSize eggsize, float eggColor1, float eggColor2, int hatchTime, ItemStack stack) {
        super(UPEntities.DINO_LAND_EGG.get(), pLevel, pX, pY, pZ, stack, eggsize, (int) eggColor1, (int) eggColor2);
        this.maxHatchTime = hatchTime;
        this.dinosaur = (LivingEntity) toSpawn.get().create(pLevel);
    }

    public DinosaurLandEgg(EntityType<DinosaurLandEgg> dinosaurLandEggEntityType, Level level) {
        super(dinosaurLandEggEntityType, level);
    }


    @Override
    public void tick() {
        super.tick();
        if(level() instanceof ServerLevel level) {
        if (currentHatchTime >= maxHatchTime) {
            if (getHatchCount() >= MAX_HATCH_COUNT) {
                hatchEgg();
            } else {
                setHatchCount(getHatchCount() + 1);
                increaseHatchCount();
                currentHatchTime = 0;
            }
        }

            currentHatchTime += getIncreaseTime(level);
        }
    }
    private static final int MAX_UPDATE_TIMER_COUNT = 40;      // Maximum threshold for hatch update timer
    private static final int CORRECT_BLOCK_EXTRA_TIME = 2;     // Extra time for correct block
    private static final int WRONG_BLOCK_EXTRA_TIME = 0;       // Extra time for incorrect block
    private static final int CORRECT_ENTITY_EXTRA_TIME = 5;    // Extra time for each correct entity
    private static final int RESET_UPDATE_TIMER = 0;           // Reset value for hatch update timer
    private static final int MIN_INCREASE_TIME = 1;           // Minimum increase time for the overall timer
    private static final int MAX_HATCH_UPDATE_TIMER = 0;       // Maximum hatch update timer comparison
    private static final int MIN_LIGHT_LEVEL = 10;              // Minimum needed light level;
    private static final int LIGHT_LEVEL_EXTRA_TIME = 2;              // Minimum needed light level;
    private int cachedTimer;
    /**
     * Calculates and returns the increase in time for hatching based on the server level conditions.
     *
     * @param level The server level where the entity is present.
     * @return The calculated increase in time for hatching.
     */
    public int getIncreaseTime(ServerLevel level) {
        int increaseTime = MIN_INCREASE_TIME;

        // Check if hatch update timer is within the defined limit
        if (hatchUpdateTimer++ <= MAX_HATCH_UPDATE_TIMER) {
            BlockPos currentPosition = this.getOnPos();

            // Check if the current position is not already cached
            if (!cachedBlockStatePos.containsKey(currentPosition)) {
                BlockState blockState = level.getBlockState(currentPosition);
                // Check if the block is hatchable
                cachedBlockStatePos.clear();
                if (blockState.is(UPTags.DINO_HATCHABLE_BLOCKS)) {

                    cachedBlockStatePos.put(currentPosition, CORRECT_BLOCK_EXTRA_TIME);
                    increaseTime += CORRECT_BLOCK_EXTRA_TIME; // Increase time for correct block
                } else {
                    cachedBlockStatePos.put(currentPosition, WRONG_BLOCK_EXTRA_TIME);
                }
            } else {
                // Use the cached value for the current position
                increaseTime += cachedBlockStatePos.get(currentPosition);
            }

            //check for light at position and see if it has enough and make it spawn faster
            int light =  level().getLightEmission(currentPosition);
            if(light >= MIN_LIGHT_LEVEL) {
                increaseTime += LIGHT_LEVEL_EXTRA_TIME;
            }

            // Check for nearby living entities and increase time based on their count
            List<LivingEntity> nearbyEntities = level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(10));
            increaseTime += (nearbyEntities.size() * CORRECT_ENTITY_EXTRA_TIME);
        }

        if(cachedTimer == 0) {
            cachedTimer = increaseTime;
        }

        // Reset hatch update timer if it exceeds the maximum threshold
        if (hatchUpdateTimer >= MAX_UPDATE_TIMER_COUNT) {
            hatchUpdateTimer = RESET_UPDATE_TIMER;
            cachedTimer = 0;
        }

        return cachedTimer; // Return the calculated increase in time
    }


    public void hatchEgg() {
        level().playSound(null, this.blockPosition(), SoundEvents.TURTLE_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);

        LivingEntity dinosaurToSpawn = dinosaur;
        if (dinosaurToSpawn instanceof IHatchableEntity hatchableEntity) {
            hatchableEntity.determineVariant(random.nextInt(100));
        }


        if (dinosaurToSpawn instanceof Animal animal) {
            animal.setAge(-24000);
        }

        if(dinosaurToSpawn instanceof Mob mob) {
            mob.restrictTo(this.blockPosition(), 20);
            mob.moveTo((double) this.blockPosition().getX() + 0.3D, this.blockPosition().getY(), (double) this.blockPosition().getZ() + 0.3D, 0.0F, 0.0F);
        }

        if (!level().isClientSide) {
            BlockPos pos = dinosaurToSpawn.blockPosition();
            level().addFreshEntity(dinosaurToSpawn);
            this.spawnAnim((ServerLevel) level(), pos);
        }
        this.kill();
    }



    public void increaseHatchCount() {
        level().playSound(null, this.blockPosition(), SoundEvents.TURTLE_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
        this.triggerAnim(EGG_HATCH.getControllerName(), EGG_HATCH.getAnimName());
    }

    private static final ResourceLocation MASSIVE_EGG_BASE = prefix("textures/entity/eggs/massive_egg_base.png");
    private static final ResourceLocation MASSIVE_EGG_SLIGHTLY_CRACKED = prefix("textures/entity/eggs/massive_egg_slightly_cracked.png");
    private static final ResourceLocation MASSIVE_EGG_VERY_CRACKED = prefix("textures/entity/eggs/massive_egg_very_cracked.png");
    private static final ResourceLocation MASSIVE_EGG_SPOTS = prefix("textures/entity/eggs/massive_egg_spots.png");
    private static final ResourceLocation MASSIVE_EGG_MODEL = prefix("geo/eggs/massive_egg.geo.json");
    private static final ResourceLocation MASSIVE_EGG_ANIMATIONS = prefix("animations/eggs/massive_egg.animation.json");
    @Override
    public ResourceLocation getTexture() {
        int scale = getModelScale();
        return switch (scale) {
            case 0 -> null;
            case 1 -> null;
            case 2 -> null;
            case 3 -> MASSIVE_EGG_BASE;
            default -> null;
        };
    }


    @Override
    public ResourceLocation getModel() {
        int scale = getModelScale();
        return switch (scale) {
            case 0 -> null;
            case 1 -> null;
            case 2 -> null;
            case 3 -> MASSIVE_EGG_MODEL;
            default -> null;
        };
    }

    @Override
    public ResourceLocation getCrackTexture() {
        int hatchCount = getHatchCount();
        int scale = getModelScale();
        if(hatchCount == 1)
        {
            return switch (scale) {
                case 0 -> null;
                case 1 -> null;
                case 2 -> null;
                case 3 -> MASSIVE_EGG_SLIGHTLY_CRACKED;
                default -> null;
            };
        }
        if(hatchCount == 2)
        {
            return switch (scale) {
                case 0 -> null;
                case 1 -> null;
                case 2 -> null;
                case 3 -> MASSIVE_EGG_VERY_CRACKED;
                default -> null;
            };
        }
        //if(hatchCount == 3)
        //{
        //    return switch (scale) {
        //        case 0 -> null;
        //        case 1 -> null;
        //        case 2 -> null;
        //        case 3 -> null;
        //        default -> null;
        //    };
        //}

        return null;
    }

    @Override
    public ResourceLocation getSpotTexture() {
        int scale = getModelScale();
        return switch (scale) {
            case 0 -> null;
            case 1 -> null;
            case 2 -> null;
            case 3 -> MASSIVE_EGG_SPOTS;
            default -> null;
        };
    }

    @Override
    public ResourceLocation getAnimation() {
        int scale = getModelScale();
        return switch (scale) {
            case 0 -> null;
            case 1 -> null;
            case 2 -> null;
            case 3 -> MASSIVE_EGG_ANIMATIONS;
            default -> null;
        };
    }


    @Override
    public int getHatchTime() {
        return maxHatchTime;
    }

    @Override
    public int getCurrentHatchTime() {
        return currentHatchTime;
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        dinosaur.save(compound);
        compound.putInt("hatchTime", currentHatchTime);
        compound.putInt("maxHatchTime", maxHatchTime);
        compound.putInt("hatchCount", getHatchCount());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        Optional<Entity> type = EntityType.create(compound, level());
        this.dinosaur = (LivingEntity) type.orElse(EntityType.PIG.create(level()));
        this.currentHatchTime = compound.getInt("hatchTime");
        this.maxHatchTime = compound.getInt("maxHatchTime");
        setHatchCount(compound.getInt("hatchCount"));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HATCH_COUNT, 0);
        //this.maxHatchTime = 1000000;

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, EGG_HATCH.getControllerName(), 0, event -> {
            return PlayState.STOP;
        }).triggerableAnim(EGG_HATCH.getAnimName(), EGG_HATCH.getAnimation()));
    }

    public int getHatchCount() {
        return this.entityData.get(HATCH_COUNT);
    }

    public void setHatchCount(int count) {
        this.entityData.set(HATCH_COUNT, count);
    }
}
