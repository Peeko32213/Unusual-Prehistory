package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.common.entity.util.interfaces.IVariantEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import java.util.Map;

public class DefaultModel<T extends Entity & GeoAnimatable & IVariantEntity> extends GeoModel<T> {
    private ResourceLocation model;
    private Map<Integer, ResourceLocation> textures;
    private ResourceLocation animation;
    private final float headScale;
    private final String headBone;

    public DefaultModel(ModelLocations.ModelData modelData){
        this.model = modelData.getModel();
        this.textures = modelData.getTextures();
        this.animation = modelData.getAnimation();
        this.headBone = modelData.getHeadBone();
        this.headScale = modelData.getBabyHeadScale();
    }

    @Override
    public ResourceLocation getModelResource(T animatable) {
        return model;
    }

    @Override
    public ResourceLocation getTextureResource(T animatable) {
        return textures.get(animatable.getVariant());
    }

    @Override
    public ResourceLocation getAnimationResource(T animatable) {
        return animation;
    }

    @Override
    public void setCustomAnimations(T animatable, long instanceId, AnimationState<T> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone(headBone);


        if(head == null) return;
        if(animatable instanceof AgeableMob ageableMob) {
            if (ageableMob.isBaby()) {
                head.setScaleX(headScale);
                head.setScaleY(headScale);
                head.setScaleZ(headScale);
            } else {
                head.setScaleX(1.0F);
                head.setScaleY(1.0F);
                head.setScaleZ(1.0F);
            }
        }
        if (!animatable.isSprinting() && !animatable.hasControllingPassenger()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
