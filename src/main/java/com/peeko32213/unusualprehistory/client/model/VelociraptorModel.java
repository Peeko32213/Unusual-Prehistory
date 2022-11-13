package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityPachycephalosaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityVelociraptor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

public class VelociraptorModel extends AnimatedGeoModel<EntityVelociraptor>
{
    @Override
    public ResourceLocation getModelLocation(EntityVelociraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/velociraptor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EntityVelociraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/velociraptor2.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EntityVelociraptor object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/velociraptor.animation.json");
    }

    @Override
    public void setLivingAnimations(EntityVelociraptor dino, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(dino, uniqueID, customPredicate);

        if (customPredicate == null) return;

        List<EntityModelData> extraDataOfType = customPredicate.getExtraDataOfType(EntityModelData.class);
        IBone head = this.getAnimationProcessor().getBone("Head");

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

