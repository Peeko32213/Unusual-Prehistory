package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityAustroraptor;
import com.peeko32213.unusualprehistory.common.entity.EntityUlughbegsaurus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class UlughbegsaurusModel extends AnimatedGeoModel<EntityUlughbegsaurus>
{
    private static final ResourceLocation TEXTURE_BLUE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus.png");
    private static final ResourceLocation TEXTURE_YELLOW = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus_yellow.png");
    private static final ResourceLocation TEXTURE_ORANGE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus_orange.png");
    private static final ResourceLocation TEXTURE_WHITE = new ResourceLocation("unusualprehistory:textures/entity/ulughbegsaurus_white.png");

    @Override
    public ResourceLocation getModelResource(EntityUlughbegsaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/ulughbegsaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityUlughbegsaurus object)
    {
        switch (object.getVariant()){
            case 1:
                return TEXTURE_WHITE;
            case 2:
                return TEXTURE_YELLOW;
            case 3:
                return TEXTURE_ORANGE;
            default:
                return TEXTURE_BLUE;
        }    }

    @Override
    public ResourceLocation getAnimationResource(EntityUlughbegsaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/ulughbegsaurus.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityUlughbegsaurus dino, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(dino, uniqueID, customPredicate);

        if (customPredicate == null) return;

        List<EntityModelData> extraDataOfType = customPredicate.getExtraDataOfType(EntityModelData.class);
        IBone head = this.getAnimationProcessor().getBone("Neck");

        if (dino.isBaby()) {
            head.setScaleX(1.75F);
            head.setScaleY(1.75F);
            head.setScaleZ(1.75F);
        }

        if (!dino.isSprinting()) {
            head.setRotationY(extraDataOfType.get(0).netHeadYaw * Mth.DEG_TO_RAD);
        }
    }

}

