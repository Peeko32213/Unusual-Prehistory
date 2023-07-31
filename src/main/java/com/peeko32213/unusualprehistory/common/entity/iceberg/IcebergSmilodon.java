package com.peeko32213.unusualprehistory.common.entity.iceberg;

import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityWorldSpawnable;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class IcebergSmilodon extends EntityWorldSpawnable {
    public static final ResourceLocation SMILODON_LOOT = prefix("entities/iceberg/smilodon");
    public IcebergSmilodon(EntityType<? extends LivingEntity> p_27557_, Level p_27558_) {
        super(p_27557_, p_27558_);
    }

    @Override
    public ResourceLocation getDeadLootTable() {
        return SMILODON_LOOT;
    }

    @Override
    protected String getFrozenState() {
        return "animation.smilodon.frozen";
    }
    @Override
    protected ItemStack getDnaItem() {
        return UPItems.AMBER_GUMMY.get().getDefaultInstance();
    }

    @Override
    protected int dropCount() {
        return 5;
    }

}
