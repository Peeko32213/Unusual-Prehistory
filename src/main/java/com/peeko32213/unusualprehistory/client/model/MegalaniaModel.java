package com.peeko32213.unusualprehistory.client.model;


import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.MegalaniaEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MegalaniaModel extends GeoModel<MegalaniaEntity>
{
    private static final ResourceLocation TEXTURE_TEMPERATE = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania.png");
    private static final ResourceLocation TEXTURE_COLD = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_HOT = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_hot.png");
    private static final ResourceLocation TEXTURE_NETHER = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_nether.png");

    private static final ResourceLocation TEXTURE_TEMPERATE_BABY = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_baby.png");
    private static final ResourceLocation TEXTURE_COLD_BABY = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_cold_baby.png");
    private static final ResourceLocation TEXTURE_HOT_BABY = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_hot_baby.png");
    private static final ResourceLocation TEXTURE_NETHER_BABY = new ResourceLocation("unusualprehistory:textures/entity/megalania/megalania_nether_baby.png");

    @Override
    public ResourceLocation getModelResource(MegalaniaEntity megalania)
    {
        if(megalania.isBaby()){
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/megalania/megalania_baby.geo.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "geo/megalania/megalania.geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(MegalaniaEntity megalania)
    {
        if (megalania.isBaby()) {
        return switch (megalania.getVariant()) {
            case 1 -> TEXTURE_COLD_BABY;
            case 2 -> TEXTURE_HOT_BABY;
            case 3 -> TEXTURE_NETHER_BABY;
            default -> TEXTURE_TEMPERATE_BABY;
            };
        } else {
            return switch (megalania.getVariant()) {
                case 1 -> TEXTURE_COLD;
                case 2 -> TEXTURE_HOT;
                case 3 -> TEXTURE_NETHER;
                default -> TEXTURE_TEMPERATE;
            };
        }
    }

    @Override
    public ResourceLocation getAnimationResource(MegalaniaEntity megalania)
    {
        if(megalania.isBaby()){
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/megalania/megalania_baby.animation.json");
        } else {
            return new ResourceLocation(UnusualPrehistory.MODID, "animations/megalania/megalania.animation.json");
        }
    }

    @Override
    public void setCustomAnimations(MegalaniaEntity animatable, long instanceId, AnimationState<MegalaniaEntity> animationState) {
        super.setCustomAnimations(animatable, instanceId, animationState);
        if (animationState == null) return;
        EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);
        CoreGeoBone head = this.getAnimationProcessor().getBone("Head");

        if (!animatable.isSprinting()) {
            head.setRotY(extraDataOfType.netHeadYaw() * Mth.DEG_TO_RAD);
        }
    }
}

