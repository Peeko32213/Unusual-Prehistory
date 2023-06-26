package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.client.screen.util.EntityLinkData;
import com.peeko32213.unusualprehistory.client.screen.util.ItemRenderData;
import com.peeko32213.unusualprehistory.client.screen.util.LinkData;

import java.util.Collections;
import java.util.List;

public class EncyclopediaCodec {

    public static Codec<EncyclopediaCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.STRING.fieldOf("title").forGetter(t -> t.title),
                    Codec.STRING.optionalFieldOf("text", "help.txt").forGetter(r -> r.textLocation),
                    EncyclopediaPictureCodec.CODEC.listOf().optionalFieldOf("pictures", Collections.emptyList()).forGetter(p -> p.pictures),
                    RecipeCodec.CODEC.listOf().optionalFieldOf("recipes",Collections.emptyList()).forGetter(r -> r.recipes),
                    EntityRenderDataCodec.CODEC.listOf().optionalFieldOf("entity_renders", Collections.emptyList()).forGetter( e -> e.entityRenders),
                    EntityLinkButtonCodec.CODEC.listOf().optionalFieldOf("entity_button", Collections.emptyList()).forGetter( e -> e.entityButtons),
                    EntityIndexCodec.CODEC.listOf().optionalFieldOf("entity_index", Collections.emptyList()).forGetter(i -> i.entityIndex),
                    LinkDataCodec.CODEC.listOf().optionalFieldOf("linked_page_buttons", Collections.emptyList()).forGetter(l -> l.linkButtons),
                    ItemRenderDataCodec.CODEC.listOf().optionalFieldOf("item_renders", Collections.emptyList()).forGetter(l -> l.itemRenders)

            ).apply(inst, EncyclopediaCodec::new)
    );
    private final String textLocation;
    private final String title;
    private final List<EncyclopediaPictureCodec> pictures;
    private final List<RecipeCodec> recipes;
    private final  List<EntityRenderDataCodec> entityRenders;
    private final List<EntityLinkData> entityButtons;
    private final List<EntityIndexCodec> entityIndex;
    private final List<LinkData> linkButtons;
    private final List<ItemRenderData> itemRenders;
    public EncyclopediaCodec(String title, String textLocation, List<EncyclopediaPictureCodec> pictures, List<RecipeCodec> recipes, List<EntityRenderDataCodec> entityRenders, List<EntityLinkData> entityButtons, List<EntityIndexCodec> entityIndex, List<LinkData> linkButtons, List<ItemRenderData> itemRenders) {
        this.textLocation = textLocation;

        this.title = title;
        this.pictures = pictures;
        this.recipes = recipes;
        this.entityRenders = entityRenders;
        this.entityButtons = entityButtons;
        this.entityIndex = entityIndex;
        this.linkButtons = linkButtons;
        this.itemRenders = itemRenders;
    }

    public String getTextLocation() {
        return textLocation;
    }


    public String getTitle() {
        return title;
    }

    public List<EncyclopediaPictureCodec> getPictures() {
        return pictures;
    }

    public List<RecipeCodec> getRecipes() {
        return recipes;
    }

    public List<EntityRenderDataCodec> getEntityRenders() {
        return entityRenders;
    }

    public List<EntityLinkData> getEntityButtons() {
        return entityButtons;
    }

    public List<EntityIndexCodec> getEntityIndex() {
        return entityIndex;
    }

    public List<LinkData> getLinkButtons() {
        return linkButtons;
    }

    public List<ItemRenderData> getItemRenders() {
        return itemRenders;
    }
}
