package com.peeko32213.unusualprehistory.common.item.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.client.model.tool.HandmadeClubModel;
import com.peeko32213.unusualprehistory.client.render.tool.ToolRenderer;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.common.ToolActions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ItemHandmadeClub extends SwordItem implements IAnimatable {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);


    public ItemHandmadeClub(Tier tier, int attackDamage, float attackSpeed) {
        super(tier, attackDamage, attackSpeed, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses() * 3)
                .tab(UnusualPrehistory.DINO_TAB)
        );
    }
    /**
     * Making all those blocks does spam the console with uuid stuff....
     * */
    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        if(pAttacker.level.isClientSide) return false;
        ServerLevel level = (ServerLevel) pAttacker.getLevel();
        BlockPos pos = pTarget.getOnPos();
        int radius = 5;
        //for (int l = 0; l < (radius); l++) {
        //    for (int p = 0; p < (radius); p++) {
        //        for (double a = 0; a < (Math.PI * 2); a = a + 0.6) {
        //            double x = (l) * Math.cos(a);
        //            double k = (p) * Math.sin(a);
        //            BlockPos pos1 = pos.offset((int) Math.round(x), 0, (int) Math.round(k));
        //            BlockState state = level.getBlockState(pos1);
        //            BlockState state1 = Blocks.STONE.defaultBlockState();
        //            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(level, pos1,state);
        //            fallingBlockEntity.setDeltaMovement(0,1,0);
        //            level.addFreshEntity(fallingBlockEntity);
        //        }
        //    }
        //}
        for (int x = -radius; x < radius; x++) {
            for (int z = -radius; z < radius; z++) {
                double distance = distance(x, z, radius, radius);
                if (distance < 1) {
                    BlockPos pos1 = pos.offset(x, 0,z);
                    BlockState state = level.getBlockState(pos1);
                    if(state.is(Blocks.BEDROCK) || !state.is(UPTags.CLUB_WHITELIST_BLOCKS)) continue;
                    level.destroyBlock(pos, false);
                    FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(level, pos1,state);
                    Vec3 vec3 = new Vec3(0,1,0);
                    Vec3 vec31 = vec3.normalize();
                    fallingBlockEntity.setDeltaMovement(vec31);
                    level.addFreshEntity(fallingBlockEntity);
                }
            }
        }
        return super.hurtEnemy(pStack, pTarget, pAttacker);
    }

    public static double distance(double x, double z, double xRadius, double zRadius) {
        return Mth.square((double) x / (xRadius)) + Mth.square((double) z / (zRadius));
    }



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

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().loop("animation.handmade_club.idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

}
