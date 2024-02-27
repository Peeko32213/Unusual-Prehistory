package com.peeko32213.unusualprehistory.common.item;

import com.peeko32213.unusualprehistory.common.entity.eggs.DinosaurLandEgg;
import com.peeko32213.unusualprehistory.common.entity.eggs.EggSize;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class DinosaurLandEggItem extends Item {

    private Supplier<? extends EntityType<?>> dinosaur;
    private EggSize size;
    private float color1;
    private float color2;
    private int hatchTime;

    public DinosaurLandEggItem(Supplier<? extends EntityType<?>> dinosaur, EggSize eggSize, int hatchTime ,float eggBaseColor, float eggSpotColor) {
        this(new Item.Properties().rarity(Rarity.RARE), dinosaur, eggSize, hatchTime, eggBaseColor, eggSpotColor);
    }

    public DinosaurLandEggItem(Properties pProperties, Supplier<? extends EntityType<?>> dinosaur, EggSize eggSize, int hatchTime ,float eggBaseColor, float eggSpotColor) {
        super(pProperties);
        this.dinosaur= dinosaur;
        this.size = eggSize;
        this.color1 = eggBaseColor;
        this.color2 = eggSpotColor;
        this.hatchTime = hatchTime;
    }


    public InteractionResult useOn(UseOnContext pContext) {
        Direction direction = pContext.getClickedFace();
        if (direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        } else {
            Level level = pContext.getLevel();
            BlockPlaceContext blockplacecontext = new BlockPlaceContext(pContext);
            BlockPos blockpos = blockplacecontext.getClickedPos();
            ItemStack itemstack = pContext.getItemInHand();
            Vec3 vec3 = Vec3.atBottomCenterOf(blockpos);
            AABB aabb = UPEntities.DINO_LAND_EGG.get().getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());
            if (level.noCollision((Entity) null, aabb) && level.getEntities((Entity) null, aabb).isEmpty()) {
                if (level instanceof ServerLevel) {
                    ServerLevel serverlevel = (ServerLevel) level;
                    DinosaurLandEgg egg = new DinosaurLandEgg(serverlevel, blockpos.getX(), blockpos.getY(), blockpos.getZ(), dinosaur, size, color1, color2, hatchTime, this.getDefaultInstance());
                    if (egg == null) {
                        return InteractionResult.FAIL;
                    }

                    float f = (float) Mth.floor((Mth.wrapDegrees(pContext.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    serverlevel.addFreshEntityWithPassengers(egg);
                    level.playSound((Player) null, egg.getX(), egg.getY(), egg.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                    egg.gameEvent(GameEvent.ENTITY_PLACE, pContext.getPlayer());
                }

                itemstack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            } else {
                return InteractionResult.FAIL;
            }
        }
    }
}
