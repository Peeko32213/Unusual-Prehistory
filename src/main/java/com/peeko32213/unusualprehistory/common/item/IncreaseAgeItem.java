package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class IncreaseAgeItem extends Item {

    private int age;
    private TagKey<EntityType<?>> toAge;
    public IncreaseAgeItem(Properties pProperties, TagKey<EntityType<?>> toAge, int agePercentageToAdd) {
        super(pProperties);
        this.age = agePercentageToAdd;
        this.toAge = toAge;
    }


    @Override
    public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
        if(pInteractionTarget instanceof Animal animal && animal.getType().is(toAge) && animal.getAge() < 0){
            if(!pPlayer.level.isClientSide) {
                int percentageAge = (int) (24000D * (age / 100D));
                int currentAge = animal.getAge();
                animal.setAge(animal.getAge() + percentageAge);
            }
            for(int i = 0; i < 10; i ++) {
                double d0 = pPlayer.level.random.nextGaussian() * 0.02D;
                double d1 = pPlayer.level.random.nextGaussian() * 0.01D;
                double d2 = pPlayer.level.random.nextGaussian() * 0.02D;
                pPlayer.level.addParticle(ParticleTypes.POOF, animal.getRandomX(1.0D), animal.getRandomY() + 0.5D, animal.getRandomZ(1.0D), d0, d1, d2);

                //pPlayer.level.addParticle(ParticleTypes.POOF, (animal.position().x()) - d0 * 10.0D, (animal.position().y() + ) - d1 * 10.0D, (animal.position().z() + 0.5), d0, d1, d2);
            }
        }
        return InteractionResult.FAIL;
    }
}
