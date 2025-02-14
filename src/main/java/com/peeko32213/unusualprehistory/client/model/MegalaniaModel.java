package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityMegalania;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MegalaniaModel extends GeoModel<EntityMegalania>
{
    private static final ResourceLocation TEXTURE_TEMPERATE = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania.png");
    private static final ResourceLocation TEXTURE_COLD = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_HOT = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_hot.png");
    private static final ResourceLocation TEXTURE_NETHER = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_nether.png");

    @Override
    public ResourceLocation getModelResource(EntityMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/megalania.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityMegalania object)
    {
        return switch (object.getVariant()) {
            case 1 -> TEXTURE_COLD;
            case 2 -> TEXTURE_HOT;
            case 3 -> TEXTURE_NETHER;
            default -> TEXTURE_TEMPERATE;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(EntityMegalania object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/megalania.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityMegalania animatable, long instanceId, AnimationState<EntityMegalania> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

