package com.peeko32213.unusualprehistory.common.entity.iceberg;

import com.peeko32213.unusualprehistory.common.entity.base.WorldSpawnableEntity;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.RawAnimation;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class IcebergMammoth extends WorldSpawnableEntity {
    public static final ResourceLocation MAMMOTH_LOOT = prefix("entities/iceberg/mammoth");
    public IcebergMammoth(EntityType<? extends LivingEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Override
    public ResourceLocation getDeadLootTable() {
        return MAMMOTH_LOOT;
    }
    protected static final RawAnimation FROZEN = RawAnimation.begin().thenPlay("animation.mammoth.frozen");

    @Override
    protected RawAnimation getFrozenState() {
        return FROZEN;
    }

    @Override
    protected ItemStack getDnaItem() {
        return UPItems.FROZEN_FOSSIL.get().getDefaultInstance();
    }

    @Override
    protected int dropCount() {
        return 10;
    }


}
