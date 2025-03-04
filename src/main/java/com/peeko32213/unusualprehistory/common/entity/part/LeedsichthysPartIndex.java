package com.peeko32213.unusualprehistory.common.entity.part;

public enum LeedsichthysPartIndex {
    BODY(0.5F),
    TAIL(0.4F);

    private final float backOffset;

    LeedsichthysPartIndex(float partOffset){
        this.backOffset = partOffset;
    }

    public static LeedsichthysPartIndex fromOrdinal(int i) {
        return switch (i) {
            case 0 -> TAIL;
            default -> BODY; // case 2 and others
        };

    }

    public static LeedsichthysPartIndex sizeAt(int bodyindex) {
        return switch (bodyindex) {

            case 0 -> TAIL;
            default -> BODY; // cases 2, 3, 4 and others
        };
    }

    public float getBackOffset() {
        return backOffset;
    }
}
