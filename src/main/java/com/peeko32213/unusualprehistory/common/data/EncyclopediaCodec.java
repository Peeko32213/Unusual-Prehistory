package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.client.screen.PlantLinkData;
import com.peeko32213.unusualprehistory.client.screen.util.EntityLinkData;
import com.peeko32213.unusualprehistory.client.screen.util.ItemRenderData;
import com.peeko32213.unusualprehistory.client.screen.util.LinkData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EncyclopediaCodec {

    public static Codec<EncyclopediaCodec> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.STRING.fieldOf("title").forGetter(t -> t.title),
                    Codec.STRING.optionalFieldOf("page_to_go", "unusualprehistory:root").forGetter(t -> t.pageToGo),
                    Codec.STRING.optionalFieldOf("text", "help.txt").forGetter(r -> r.textLocation),
                    EncyclopediaPictureCodec.CODEC.listOf().optionalFieldOf("pictures", Collections.emptyList()).forGetter(p -> p.pictures),
                    RecipeCodec.CODEC.listOf().optionalFieldOf("recipes",Collections.emptyList()).forGetter(r -> r.recipes),
                    EntityRenderDataCodec.CODEC.listOf().optionalFieldOf("entity_renders", Collections.emptyList()).forGetter( e -> e.entityRenders),
                    EntityLinkButtonCodec.CODEC.listOf().optionalFieldOf("entity_button", Collections.emptyList()).forGetter( e -> e.entityButtons),
                    PlantLinkButtonCodec.CODEC.listOf().optionalFieldOf("plant_button", Collections.emptyList()).forGetter( e -> e.plantButtons),
                    EntityIndexCodec.CODEC.listOf().optionalFieldOf("entity_index", Collections.emptyList()).forGetter(i -> i.entityIndex),
                    PlantIndexCodec.CODEC.listOf().optionalFieldOf("plant_index", Collections.emptyList()).forGetter(i -> i.plantIndex),
                    LinkDataCodec.CODEC.listOf().optionalFieldOf("linked_page_buttons", Collections.emptyList()).forGetter(l -> l.linkButtons),
                    ItemRenderDataCodec.CODEC.listOf().optionalFieldOf("item_renders", Collections.emptyList()).forGetter(l -> l.itemRenders)

            ).apply(inst, EncyclopediaCodec::new)
    );
    private final String textLocation;
    private final String title;
    private final String pageToGo;
    private final List<EncyclopediaPictureCodec> pictures;
    private final List<RecipeCodec> recipes;
    private final  List<EntityRenderDataCodec> entityRenders;
    private final List<EntityLinkData> entityButtons;
    private final List<PlantLinkData> plantButtons;
    private final List<EntityIndexCodec> entityIndex;
    private final List<PlantIndexCodec> plantIndex;
    private final List<LinkData> linkButtons;
    private final List<ItemRenderData> itemRenders;
    public EncyclopediaCodec(String title,String pageToGo,String textLocation, List<EncyclopediaPictureCodec> pictures, List<RecipeCodec> recipes, List<EntityRenderDataCodec> entityRenders, List<EntityLinkData> entityButtons,List<PlantLinkData> plantButtons, List<EntityIndexCodec> entityIndex,List<PlantIndexCodec> plantIndex, List<LinkData> linkButtons, List<ItemRenderData> itemRenders) {
        this.textLocation = textLocation;
        this.plantButtons = plantButtons;
        this.title = title;
        this.pageToGo = pageToGo;
        this.pictures = pictures;
        this.recipes = recipes;
        this.entityRenders = entityRenders;
        this.entityButtons = entityButtons;
        this.entityIndex = entityIndex;
        this.plantIndex = plantIndex;
        this.linkButtons = linkButtons;
        this.itemRenders = itemRenders;
    }

    public String getTextLocation() {
        return textLocation;
    }


    public String getTitle() {
        return title;
    }

    public String getPageToGo() {
        return pageToGo;
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

    public List<PlantLinkData> getPlantButtons() {
        return plantButtons;
    }

    public List<PlantIndexCodec> getPlantIndex() {
        return plantIndex;
    }

    public static <T> Map<T, EncyclopediaCodec> convertToMap(Map<T, EncyclopediaCodec> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> new EncyclopediaCodec(
                        entry.getValue().getTitle(),
                        entry.getValue().getPageToGo(),
                        entry.getValue().getTextLocation(),
                        entry.getValue().getPictures(),
                        entry.getValue().getRecipes(),
                        entry.getValue().getEntityRenders(),
                        entry.getValue().getEntityButtons(),
                        entry.getValue().getPlantButtons(),
                        entry.getValue().getEntityIndex(),
                        entry.getValue().getPlantIndex(),
                        entry.getValue().getLinkButtons(),
                        entry.getValue().getItemRenders()
                )
        ));
    }

    public static <T> Map<T, EncyclopediaCodec> convertFromMap(Map<T, EncyclopediaCodec> map) {
        return map.entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> new EncyclopediaCodec(
                        entry.getValue().getTitle(),
                        entry.getValue().getPageToGo(),
                        entry.getValue().getTextLocation(),
                        entry.getValue().getPictures(),
                        entry.getValue().getRecipes(),
                        entry.getValue().getEntityRenders(),
                        entry.getValue().getEntityButtons(),
                        entry.getValue().getPlantButtons(),
                        entry.getValue().getEntityIndex(),
                        entry.getValue().getPlantIndex(),
                        entry.getValue().getLinkButtons(),
                        entry.getValue().getItemRenders()
                )
        ));
    }

}
