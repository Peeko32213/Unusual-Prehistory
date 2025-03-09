package com.peeko32213.unusualprehistory.core.registry.util;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import java.util.function.Function;
import java.util.regex.Pattern;

public class CodecUtils {

    /** Codec to validate and read an integer as a hex string **/
    public static final Codec<Integer> HEX_INT_CODEC = hexIntCodec();
    /** Codec to accept either a hex string or a raw integer **/
    public static final Codec<Integer> HEX_OR_INT_CODEC = Codec.either(HEX_INT_CODEC, Codec.INT)
            .xmap(either -> either.map(Function.identity(), Function.identity()),
                    Either::right);


    /**
     * @return a codec that converts between hex formatted strings and packed decimal integers
     */
    private static Codec<Integer> hexIntCodec() {
        final Pattern pattern = Pattern.compile("[0-9a-fA-F]+");
        Function<String, DataResult<String>> function = (s) -> {
            if(s.isEmpty()) {
                return DataResult.error(() -> "Failed to parse hex int from empty string");
            }
            if(!pattern.matcher(s).matches()) {
                return DataResult.error(()-> "Invalid hex int " + s);
            }
            return DataResult.success(s);
        };
        return Codec.STRING.flatXmap(function, function).xmap(s -> Integer.valueOf(s, 16), Integer::toHexString);
    }
}
