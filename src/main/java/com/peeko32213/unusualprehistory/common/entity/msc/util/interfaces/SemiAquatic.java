package com.peeko32213.unusualprehistory.common.entity.msc.util.interfaces;

public interface SemiAquatic {

    boolean shouldEnterWater();

    boolean shouldLeaveWater();

    boolean shouldStopMoving();

    int getWaterSearchRange();
}
