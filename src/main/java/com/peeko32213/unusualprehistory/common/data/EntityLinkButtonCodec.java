package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.client.screen.util.EntityLinkData;

public class EntityLinkButtonCodec {

    public static final Codec<EntityLinkData> CODEC = RecordCodecBuilder.create(inst -> inst
            .group(
                    Codec.STRING.fieldOf("entity").forGetter(e -> e.entity),
                    Codec.INT.fieldOf("x").forGetter(x -> x.x),
                    Codec.INT.fieldOf("y").forGetter(y -> y.y),
                    Codec.DOUBLE.fieldOf("scale").forGetter(s -> s.scale),
                    Codec.DOUBLE.fieldOf("entity_scale").forGetter(s -> s.entity_scale),
                    Codec.INT.fieldOf("page").forGetter(p -> p.page),
                    Codec.STRING.optionalFieldOf("linked_page", "").forGetter(h -> h.linked_page),
                    Codec.STRING.fieldOf("hover_text").forGetter(h -> h.hover_text),
                    Codec.FLOAT.fieldOf("offset_x").forGetter(off -> off.offset_x),
                    Codec.FLOAT.fieldOf("offset_y").forGetter(off -> off.offset_y)
            ).apply(inst, EntityLinkData::new)
    );
}
