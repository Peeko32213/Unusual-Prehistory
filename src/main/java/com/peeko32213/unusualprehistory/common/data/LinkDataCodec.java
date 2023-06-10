package com.peeko32213.unusualprehistory.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.peeko32213.unusualprehistory.client.screen.util.LinkData;

public class LinkDataCodec {

    public static final Codec<LinkData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("linked_page").forGetter(l -> l.linked_page),
            Codec.STRING.fieldOf("text").forGetter(t -> t.text),
            Codec.INT.fieldOf("x").forGetter(x -> x.x),
            Codec.INT.fieldOf("y").forGetter(y -> y.y),
            Codec.INT.fieldOf("page").forGetter(p -> p.page)
    ).apply(instance, LinkData::new));
}
