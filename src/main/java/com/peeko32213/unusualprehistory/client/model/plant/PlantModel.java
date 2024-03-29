package com.peeko32213.unusualprehistory.client.model.plant;

import com.peeko32213.unusualprehistory.common.entity.plants.EntityPlant;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.peeko32213.unusualprehistory.UnusualPrehistory.prefix;

public class PlantModel extends AnimatedGeoModel<EntityPlant> {
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
        return null;
    }


}