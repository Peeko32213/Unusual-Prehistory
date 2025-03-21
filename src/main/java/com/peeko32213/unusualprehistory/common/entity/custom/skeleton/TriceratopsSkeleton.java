package com.peeko32213.unusualprehistory.common.entity.custom.skeleton;

import com.peeko32213.unusualprehistory.common.entity.custom.base.SkeletonEntity;
import com.peeko32213.unusualprehistory.core.registry.items.UPItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class TriceratopsSkeleton extends SkeletonEntity {

    public TriceratopsSkeleton(EntityType<? extends TriceratopsSkeleton> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.isEmpty() && pHand == InteractionHand.MAIN_HAND) {
        }
        return super.interact(pPlayer, pHand);
    }

    public ItemStack getPickResult() {
        return new ItemStack(UPItems.TRIKE_SKELETON.get());
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::predicate));
    }

    private <E extends SkeletonEntity> PlayState predicate(AnimationState<E> event) {
        return PlayState.CONTINUE;
    }
}
