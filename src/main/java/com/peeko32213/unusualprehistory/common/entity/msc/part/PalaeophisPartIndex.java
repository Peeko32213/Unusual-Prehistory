package com.peeko32213.unusualprehistory.common.entity.msc.part;

public enum PalaeophisPartIndex {
    HEAD(0.2F), NECK(0.5F), BODY(0.5F),BODY_FIN(0.5F), TAIL(0.4F), FIN(0.4F);

    private final float backOffset;

    PalaeophisPartIndex(float partOffset){
        this.backOffset = partOffset;
    }

    public static PalaeophisPartIndex fromOrdinal(int i) {
        return switch (i) {
            case 0 -> HEAD;
            case 1 -> NECK;
            case 3 -> BODY_FIN;
            case 4 -> TAIL;
            case 5 -> FIN;
            default -> BODY; // case 2 and others
        };

    }

    public static PalaeophisPartIndex sizeAt(int bodyindex) {
        return switch (bodyindex) {
            case 0 -> HEAD;
            case 1, 2 -> NECK;
            case 8,9 -> BODY_FIN;
            case 10,11,12,13 -> TAIL;
            case 14,15 -> FIN;
            default -> BODY; // cases 2, 3, 4 and others
        };
    }

    public float getBackOffset() {
        return backOffset;
    }
}
