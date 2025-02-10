package com.peeko32213.unusualprehistory.common.item.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.peeko32213.unusualprehistory.client.model.tool.HandmadeClubModel;
import com.peeko32213.unusualprehistory.client.render.tool.ToolRenderer;
import com.peeko32213.unusualprehistory.common.entity.msc.projectile.ThrowableFallingBlockEntity;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolActions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.DefaultAnimations;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ItemHandmadeClub extends SwordItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public ItemHandmadeClub(Tier tier, int attackDamage, float attackSpeed) {
        super(tier, attackDamage, attackSpeed, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses() * 3)
        );
    }

//    @Override
//    public InteractionResult useOn(UseOnContext pContext) {
//        Level pLevel = pContext.getLevel();
//        if(pLevel.isClientSide) return InteractionResult.FAIL;
//        BlockPos pos = pContext.getClickedPos();
//        BlockState state = pLevel.getBlockState(pos);
//        ServerLevel level = (ServerLevel) pLevel;
//        if(state.is(Blocks.BEDROCK) || !state.is(UPTags.CLUB_WHITELIST_BLOCKS)) return InteractionResult.FAIL;
//        ThrowableFallingBlockEntity fallingBlockEntity = ThrowableFallingBlockEntity.fall(level, pos,state);
//        Vec3 vec3 = new Vec3(0,1,0);
//        Vec3 vec31 = vec3.normalize();
//        fallingBlockEntity.setDeltaMovement(vec31);
//        fallingBlockEntity.setHurtsEntities(1, 10);
//        return super.useOn(pContext);
//    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        if (slot == EquipmentSlot.MAINHAND) {
            return ImmutableMultimap.of(
                    Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", super.getDamage(), AttributeModifier.Operation.ADDITION),
                    Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", -3.4F, AttributeModifier.Operation.ADDITION)
            );
        }
        else return super.getAttributeModifiers(slot, stack);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private final ToolRenderer renderer = new ToolRenderer<>(new HandmadeClubModel());

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return this.renderer;
            }
        });
    }

    @Override
    public boolean canPerformAction(ItemStack stack, net.minecraftforge.common.ToolAction toolAction) {
        return ToolActions.DEFAULT_SWORD_ACTIONS.contains(toolAction);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller", state -> PlayState.CONTINUE)
                .triggerableAnim("animation.handmade_club.idle", DefaultAnimations.IDLE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
