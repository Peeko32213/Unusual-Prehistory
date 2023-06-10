package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.client.screen.util.ItemRenderData;

public class ItemRenderDataCodec {
    public static final Codec<ItemRenderData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("item").forGetter(i -> i.item),
            Codec.INT.fieldOf("x").forGetter(x -> x.x),
            Codec.INT.fieldOf("y").forGetter(y ->y.y),
            Codec.DOUBLE.fieldOf("scale").forGetter(s -> s.scale),
            Codec.INT.fieldOf("page").forGetter(p -> p.page)
    ).apply(instance, ItemRenderData::new));


}
