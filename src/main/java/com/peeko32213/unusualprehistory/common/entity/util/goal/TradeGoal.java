package com.peeko32213.unusualprehistory.common.entity.util.goal;

import com.peeko32213.unusualprehistory.common.data.LootFruitCodec;
import com.peeko32213.unusualprehistory.common.data.LootFruitJsonManager;
import com.peeko32213.unusualprehistory.common.entity.base.PrehistoricEntity;
import com.peeko32213.unusualprehistory.core.registry.UPBlocks;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;

public class TradeGoal extends Goal {
    private static final TargetingConditions TRADE_TARGETING = TargetingConditions.forNonCombat().range(10.0D).ignoreLineOfSight();
    private final TargetingConditions targetingConditions;
    protected final PrehistoricEntity mob;
    private final Ingredient items;
    @Nullable
    protected Player player;


    public TradeGoal(PrehistoricEntity pMob, Ingredient pItems) {
        this.mob = pMob;
        this.items = pItems;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        this.targetingConditions = TRADE_TARGETING.copy().selector(this::shouldTrade);
    }

    private boolean shouldTrade(LivingEntity p_148139_) {
        return this.items.test(p_148139_.getMainHandItem()) || this.items.test(p_148139_.getOffhandItem());
    }


    @Override
    public boolean canUse() {
        if (this.mob.getTradingCooldownTimer() > 0 || this.mob.isInWaterRainOrBubble() || this.items == null) {
            return false;
        } else {
            this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
            return this.player != null;
        }
    }


    @Override
    public boolean canContinueToUse() {
        if (this.mob.getTradingCooldownTimer() > 0 || this.mob.isInWaterRainOrBubble()) {
            return false;
        } else {
            this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
            return this.player != null;
        }
    }

    @Override
    public void start() {
        this.mob.setIsTrading(true);
        super.start();
    }

    @Override
    public void tick() {
        super.tick();
        Item item = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        List<LootFruitCodec> lootFruits = LootFruitJsonManager.getLoot(item, null);
        if (lootFruits == null) return;
        if (!lootFruits.isEmpty()) {
            ItemStack lootFruit = new ItemStack(UPBlocks.FRUIT_LOOT_BOX.get());
            CompoundTag lootFruitTag = lootFruit.getOrCreateTag();
            int color = lootFruits.get(0).getColor().getValue();
            int modelData = lootFruits.get(0).getCustomModelData();
            lootFruitTag.putString("translationKey", lootFruits.get(0).getTranslationKey());
            lootFruitTag.putInt("color", color);
            lootFruitTag.put("tradeItem", item.getDefaultInstance().serializeNBT());
            lootFruitTag.putInt("CustomModelData", modelData);
            lootFruit.setTag(lootFruitTag);
            this.mob.setItemInHand(InteractionHand.MAIN_HAND, lootFruit);
            if (this.mob.getTradingAndGottenItem()) {

                double d0 = this.player.getX()- this.mob.getX();
                double d1 = this.player.getY() - this.mob.getY();
                double d2 = this.player.getZ() - this.mob.getZ();
                Vec3 vec3 = new Vec3(d0, d1, d2);
                vec3.multiply(0.5, 0.5 ,0.5);
                this.mob.playSound(UPSounds.GIGANTO_TRADE.get(), 1.0F, 1.0F);

                ItemEntity lootFruitEntity = new ItemEntity(this.mob.level(), this.mob.getX(), this.mob.getY(), this.mob.getZ(), lootFruit,vec3.x(), vec3.y(), vec3.z());
                this.player.getItemInHand(InteractionHand.MAIN_HAND).shrink(1);
                this.mob.level().addFreshEntity(lootFruitEntity);
                this.mob.setTradingAndGottenItem(false);
                this.mob.setTradingCooldownTimer(this.mob.TRADING_COOLDOWN);
                this.stop();
            }
        }
    }

    @Override
    public void stop() {


        this.mob.setIsTrading(false);
        this.mob.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        super.stop();
    }
}

