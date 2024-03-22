package com.peeko32213.unusualprehistory.client.model;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.EntityGuanlingsaurus;
import com.peeko32213.unusualprehistory.common.entity.EntityProtosphyraena;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GuanlingsaurusModel extends GeoModel<EntityGuanlingsaurus> {

    //double rotYAdditive = 0;
    double xAdditive = 0;

    @Override
    public ResourceLocation getModelResource(EntityGuanlingsaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/guanlingsaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityGuanlingsaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "textures/entity/guanlingsaurus.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EntityGuanlingsaurus object)
    {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/guanlingsaurus.animation.json");
    }

    @Override
    public void setCustomAnimations(EntityGuanlingsaurus animatable, long instanceId, AnimationState<EntityGuanlingsaurus> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        //minecraft works off degrees, geckolib works off radians
        if (animationState == null) return;

        CoreGeoBone body = this.getAnimationProcessor().getBone("body");
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        double headingChange = Math.toRadians(animatable.deltaYRot);

        CoreGeoBone tail1 = this.getAnimationProcessor().getBone("tail");
        double tail1AngleY = tail1.getRotY();
        double tail1AngleX = tail1.getRotX();
        //positions of the bone IN RELATION TO THE ENTITY ROOT

        CoreGeoBone tail2 = this.getAnimationProcessor().getBone("tail2");
        double tail2AngleY = tail2.getRotY();
        double tail2AngleX = tail2.getRotX();

        CoreGeoBone tailfin  = this.getAnimationProcessor().getBone("tailfin");
        double tailfinAngleY = tailfin.getRotY();
        double tailfinAngleX = tailfin.getRotX();

        //dynamic yaw stuff

        if (headingChange >= 0.02454369260617026 || headingChange <= -0.02454369260617026){
            body.setRotY((float) (headingChange * 3));
            tail1.setRotY((float) (headingChange * 2));
            tail2.setRotY((float) (headingChange * 2));
            tailfin.setRotY((float) (headingChange * 3));
            //essentially whenever a bone's parent turns it will turn on the next tick to compensate for the turning
            //parenting is all body rn but that is planned to changefg
        }

        System.out.println(headingChange);


        //dynamic pitch
        if (!animatable.isInWater()) {
            body.setRotZ(1.5708f);
        }
        else {

            body.setRotX(extraDataOfType.headPitch() * (float)Math.PI / 180F);
            body.setRotY(extraDataOfType.netHeadYaw() * (float)Math.PI / 180F);

            animatable.xDiff = body.getRotX() - animatable.oldRotX;
            animatable.oldRotX = body.getRotX();
            //find the old x rotation and the differential

            this.xAdditive += animatable.xDiff;

            tail1.setRotX((float) ((float) (tail1AngleX-xAdditive)*2));
            tail2.setRotX((float) ((float) (tail2AngleX-xAdditive)*1.5));
            tailfin.setRotX((float) (tailfinAngleX-xAdditive));


        }
        //end of dynamic pitch

        if (this.xAdditive > 0) {
            this.xAdditive -= ( Math.abs(animatable.deltaDist));

            if (this.xAdditive < 0){
                this.xAdditive = 0;
                //reset to zero if it overshoots
            }

        } else if (this.xAdditive < 0) {
            this.xAdditive += (Math.abs(animatable.deltaDist));

            if (this.xAdditive > 0){
                this.xAdditive = 0;
            }
        }//slowly adjust the additive yaw back to zero depending on how much the entity moves


    }
}
