package com.peeko32213.unusualprehistory.client.model.entity.prehistoric;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.custom.prehistoric.MegalaniaEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MegalaniaModel extends GeoModel<MegalaniaEntity>
{
    private static final ResourceLocation TEXTURE_TEMPERATE = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_temperate.png");
    private static final ResourceLocation TEXTURE_COLD = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_WARM = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_warm.png");
    private static final ResourceLocation TEXTURE_NETHER = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_nether.png");

    @Override
    public ResourceLocation getModelResource(MegalaniaEntity megalania) {
        return new ResourceLocation(UnusualPrehistory.MODID, "geo/megalania.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MegalaniaEntity megalania) {
        return switch (megalania.getVariant()) {
            case 1 -> TEXTURE_COLD;
            case 2 -> TEXTURE_WARM;
            case 3 -> TEXTURE_NETHER;
            default -> TEXTURE_TEMPERATE;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(MegalaniaEntity megalania) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/megalania.animation.json");
    }

    @Override
    public void setCustomAnimations(MegalaniaEntity animatable, long instanceId, AnimationState<MegalaniaEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Varanus_Head");

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

