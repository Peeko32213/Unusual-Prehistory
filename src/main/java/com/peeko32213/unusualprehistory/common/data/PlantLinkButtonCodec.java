package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.client.screen.PlantLinkData;
import com.peeko32213.unusualprehistory.client.screen.util.EntityLinkData;
import net.minecraft.core.Registry;

public class PlantLinkButtonCodec {

    public static final Codec<PlantLinkData> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.STRING.fieldOf("plant").forGetter(e -> e.plant),
                    Codec.INT.fieldOf("x").forGetter(x -> x.x),
                    Codec.INT.fieldOf("y").forGetter(y -> y.y),
                    Codec.DOUBLE.fieldOf("scale").forGetter(s -> s.scale),
                    Codec.DOUBLE.fieldOf("plant_scale").forGetter(s -> s.plant_scale),
                    Codec.INT.fieldOf("page").forGetter(p -> p.page),
                    Codec.STRING.optionalFieldOf("linked_page", "").forGetter(h -> h.linked_page),
                    Codec.STRING.fieldOf("hover_text").forGetter(h -> h.hover_text),
                    Codec.FLOAT.fieldOf("offset_x").forGetter(off -> off.offset_x),
                    Codec.FLOAT.fieldOf("offset_y").forGetter(off -> off.offset_y)
            ).apply(inst, PlantLinkData::new)
    );
}
