package com.peeko32213.unusualprehistory.common.item;

import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;

public class UPPickaxeItem extends PickaxeItem {
    public UPPickaxeItem(Tier tier, int attackDamage, float attackSpeed) {
        super(tier, attackDamage, attackSpeed, new Properties()
                .stacksTo(1)
                .defaultDurability(tier.getUses())
        );
    }

}
