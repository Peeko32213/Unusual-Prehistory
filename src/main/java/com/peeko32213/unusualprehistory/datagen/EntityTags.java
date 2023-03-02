package com.peeko32213.unusualprehistory.datagen;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class EntityTags extends EntityTypeTagsProvider {


    public EntityTags(DataGenerator pGenerator, ExistingFileHelper existingFileHelper) {
        super(pGenerator, UnusualPrehistory.MODID, existingFileHelper);
    }

    protected void addTags() {

        /**Example**/
        //tag(UPTags.ANURO_TARGETS).add(UPEntities.BABY_BRACHI.get());
    }

    @Override
    public String getName() {
        return UnusualPrehistory.MODID + " Entity type tags provider";
    }
}
