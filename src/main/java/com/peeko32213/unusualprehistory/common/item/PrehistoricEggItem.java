package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.entity.custom.eggs.PrehistoricEggEntity;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.EggSize;
import com.peeko32213.unusualprehistory.common.entity.custom.eggs.EggVariant;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PrehistoricEggItem extends Item {

    private Supplier<? extends EntityType<?>> entity;
    private EggSize size;
    private EggVariant variant;
    private float color1;
    private float color2;
    private int hatchTime;


    public PrehistoricEggItem(Supplier<? extends EntityType<?>> entity, EggSize eggSize, EggVariant variant, int hatchTime , float eggBaseColor, float eggSpotColor) {
        this(new Properties(), entity, eggSize,variant, hatchTime, eggBaseColor, eggSpotColor);
    }

    public PrehistoricEggItem(Properties pProperties, Supplier<? extends EntityType<?>> entity, EggSize eggSize, EggVariant variant, int hatchTime , float eggBaseColor, float eggSpotColor) {
        super(pProperties);
        this.entity = entity;
        this.size = eggSize;
        this.color1 = eggBaseColor;
        this.color2 = eggSpotColor;
        this.hatchTime = hatchTime;
        this.variant = variant;
    }

    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Direction direction = pContext.getClickedFace();
        if (direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level level = pContext.getLevel();
            BlockPlaceContext blockplacecontext = new BlockPlaceContext(pContext);
            BlockPos blockpos = blockplacecontext.getClickedPos();
            ItemStack itemstack = pContext.getItemInHand();
            Vec3 vec3 = Vec3.atCenterOf(blockpos);
            AABB aabb = UPEntities.PREHISTORIC_EGG.get().getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());
            if (level.noCollision(null, aabb) && level.getEntities(null, aabb).isEmpty()) {

                    PrehistoricEggEntity egg = new PrehistoricEggEntity(level, vec3.x(), blockpos.getY(), vec3.z(), entity, size, variant, color1, color2, hatchTime, itemstack);
                    if (egg == null) {
                        return InteractionResult.FAIL;
                    }
                if (level instanceof ServerLevel serverlevel) {
                    float f = (float) Mth.floor((Mth.wrapDegrees(pContext.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    serverlevel.addFreshEntityWithPassengers(egg);
                    level.playSound(null, egg.getX(), egg.getY(), egg.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                    egg.gameEvent(GameEvent.ENTITY_PLACE, pContext.getPlayer());
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }

    public Supplier<? extends EntityType<?>> getEntity() {
        return entity;
    }
}
