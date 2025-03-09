package com.peeko32213.unusualprehistory.datagen.entitydata;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.data.entity.*;
import com.peeko32213.unusualprehistory.common.data.entity.attribute.AttributesModifier;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.List;
import java.util.function.Consumer;

public class EntityDataGenerator extends EntityDataProvider {
    public EntityDataGenerator(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildEntityData(Consumer<EntityDataConsumer> pWriter) {
        PrehistoricEntityData trexData = new PrehistoricEntityData(
                WeightedRandomList.create(
                        new WeightedVariantData(
                                65,
                                new VariantData(
                                        1,
                                        new EntityResourceLocationData(
                                                new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_rex.geo.json"),
                                                new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_rex.png"),
                                                new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus.animation.json"),
                                                UPRenderTypes.getRenderType(ResourceLocation.tryParse("entity_cutout"))
                                        ),
                                        new AttributesModifier(List.of(
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.MAX_HEALTH,300D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.MOVEMENT_SPEED,0.2D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.ATTACK_DAMAGE,16D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.KNOCKBACK_RESISTANCE,1.5D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.FOLLOW_RANGE,32D
                                                )
                                        )
                                        )
                                )
                        ),
                        new WeightedVariantData(
                                1,
                                new VariantData(
                                        2,
                                        new EntityResourceLocationData(
                                                new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_mcraeensis.geo.json"),
                                                new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis.png"),
                                                new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus.animation.json"),
                                                UPRenderTypes.getRenderType(ResourceLocation.tryParse("entity_cutout_no_cull"))
                                        ),
                                        new AttributesModifier(List.of(
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.MAX_HEALTH,300D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.MOVEMENT_SPEED,0.5D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.ATTACK_DAMAGE,16D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.KNOCKBACK_RESISTANCE,1.5D
                                                ),
                                                new AttributesModifier.AttributesMap(
                                                        Attributes.FOLLOW_RANGE,32D
                                                )
                                        )
                                        )
                                ))
                )
        );

        pWriter.accept(new EntityDataConsumer(UPEntities.TYRANNOSAURUS.get(), trexData));
    }
}
