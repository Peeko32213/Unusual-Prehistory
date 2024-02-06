package com.peeko32213.unusualprehistory.common.entity.msc.anim_goal;

import software.bernie.geckolib.core.animation.RawAnimation;

public class AnimationHelper {

    private static int controllerCount = 0;
    private final String controllerName;
    private final String animName;
    private final RawAnimation animation;

    public AnimationHelper(String animName, RawAnimation animation) {
        String controllerName = "controller" + (++controllerCount);
        this.animName = animName;
        this.animation = animation;
        this.controllerName = controllerName;
    }

    public AnimationHelper(String controllerName, String animName, RawAnimation animation){
        this.controllerName = controllerName;
        this.animName = animName;
        this.animation = animation;
    }



    public static AnimationHelper loopingAnimation(String animName, String animEName) {
        RawAnimation anim = RawAnimation.begin().thenLoop(animEName);
        return new  AnimationHelper(animName, anim);
    }

    public static AnimationHelper loopingAnimation(String animName) {
        RawAnimation anim = RawAnimation.begin().thenLoop(animName);
        return new  AnimationHelper(animName, anim);
    }


    public RawAnimation getAnimation() {
        return animation;
    }

    public String getAnimName() {
        return animName;
    }

    public String getControllerName() {
        return controllerName;
    }
}
