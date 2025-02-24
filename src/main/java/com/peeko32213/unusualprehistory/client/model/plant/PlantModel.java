package com.peeko32213.unusualprehistory.client.model.plant;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.ArchelonEntity;
import com.peeko32213.unusualprehistory.common.entity.plants.EntityPlant;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class PlantModel extends GeoModel<EntityPlant> {
    //MODELS
    private String modelMod;
    private String textureName;

    public PlantModel(String modelMod, String textureName){
        this.modelMod = modelMod;
        this.textureName = textureName;
    }

    @Override
    public ResourceLocation getModelResource(EntityPlant object) {
        return prefix("geo/plants/"+ modelMod + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EntityPlant object) {
        return prefix("textures/entity/"+ textureName);
    }

    @Override
    public ResourceLocation getAnimationResource(EntityPlant object) {
        return new ResourceLocation(UnusualPrehistory.MODID, "animations/plant/plant.animation.json");
    }
}