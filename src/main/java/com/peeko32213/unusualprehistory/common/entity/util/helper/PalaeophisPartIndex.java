package com.peeko32213.unusualprehistory.common.entity.util.helper;

public enum  PalaeophisPartIndex {

    HEAD(0F), NECK(0.5F), BODY(0.5F), TAIL(0.4F);

    private final float backOffset;

    PalaeophisPartIndex(float partOffset){
        this.backOffset = partOffset;
    }

    public static PalaeophisPartIndex fromOrdinal(int i) {
        return switch (i) {
            case 0 -> HEAD;
            case 1 -> NECK;
            case 3 -> TAIL;
            default -> BODY; // case 2 and others
        };

    }

    public static PalaeophisPartIndex sizeAt(int bodyindex) {
        return switch (bodyindex) {
            case 0 -> HEAD;
            case 1, 5, 6 -> NECK;
            case 7 -> TAIL;
            default -> BODY; // cases 2, 3, 4 and others
        };
    }

    public float getBackOffset() {
        return backOffset;
    }

}
