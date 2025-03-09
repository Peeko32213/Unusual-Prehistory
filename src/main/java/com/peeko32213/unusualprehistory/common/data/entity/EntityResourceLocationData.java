package com.peeko32213.unusualprehistory.common.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class EntityResourceLocationData {

    public static final Codec<EntityResourceLocationData> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    ResourceLocation.CODEC.fieldOf("model_location").forGetter(EntityResourceLocationData::getModelLocation),
                    ResourceLocation.CODEC.fieldOf("texture_location").forGetter(EntityResourceLocationData::getTextureLocation),
                    ResourceLocation.CODEC.fieldOf("animation_location").forGetter(EntityResourceLocationData::getAnimationLocation),
                    UPRenderTypes.CODEC.fieldOf("render_type").forGetter(EntityResourceLocationData::getRenderType)
            ).apply(inst, EntityResourceLocationData::new)
    );

    private final ResourceLocation modelLocation;
    private final ResourceLocation textureLocation;
    private final ResourceLocation animationLocation;
    private final UPRenderTypes.RenderTypes renderType;

    public EntityResourceLocationData(ResourceLocation modelLocation, ResourceLocation textureLocation, ResourceLocation animationLocation, UPRenderTypes.RenderTypes renderType) {
        this.modelLocation = modelLocation;
        this.textureLocation = textureLocation;
        this.animationLocation = animationLocation;
        this.renderType = renderType;
    }

    public UPRenderTypes.RenderTypes getRenderType() {
        return renderType;
    }

    public ResourceLocation getAnimationLocation() {
        return animationLocation;
    }

    public ResourceLocation getModelLocation() {
        return modelLocation;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }

    public static EntityResourceLocationData getDefaultInstance() {
        return new EntityResourceLocationData(
                new ResourceLocation(UnusualPrehistory.MODID, "geo/tyrannosaurus/tyrannosaurus_mcraeensis.geo.json"),
                new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/tyrannosaurus/tyrannosaurus_mcraeensis.png"),
                new ResourceLocation(UnusualPrehistory.MODID, "animations/tyrannosaurus.animation.json"),
                UPRenderTypes.getRenderType(ResourceLocation.tryParse("entity_cutout"))
                );
    }

}
