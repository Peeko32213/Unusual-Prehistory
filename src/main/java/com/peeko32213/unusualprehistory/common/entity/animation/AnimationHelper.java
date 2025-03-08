package com.peeko32213.unusualprehistory.common.entity.animation;

import software.bernie.geckolib.core.animation.RawAnimation;

public class AnimationHelper {

    private static int controllerCount = 0;
    private final String controllerName;
    private final String animName;
    private final String getRawAnimName;
    private final RawAnimation animation;

    public AnimationHelper(String animName, RawAnimation animation, String animEName) {
        String controllerName = "controller" + (++controllerCount);
        this.animName = animName;
        this.animation = animation;
        this.controllerName = controllerName;
        this.getRawAnimName = animEName;
    }

    public AnimationHelper(String controllerName, String animName, RawAnimation animation, String animEName){
        this.controllerName = controllerName;
        this.animName = animName;
        this.animation = animation;
        this.getRawAnimName = animEName;
    }


    public static AnimationHelper loopingAnimation(String animName, String animEName) {
        RawAnimation anim = RawAnimation.begin().thenLoop(animEName);
        return new  AnimationHelper(animName, anim, animEName);
    }

    public static AnimationHelper loopingAnimation(String animName) {
        RawAnimation anim = RawAnimation.begin().thenLoop(animName);
        return new  AnimationHelper(animName, anim, animName);
    }


    public static AnimationHelper loopingAnimationWController(String controllerName, String animName, String animEName) {
        RawAnimation anim = RawAnimation.begin().thenLoop(animEName);
        return new  AnimationHelper(controllerName,animName, anim, animEName);
    }

    public static AnimationHelper loopingAnimationWController(String controllerName, String animName) {
        RawAnimation anim = RawAnimation.begin().thenLoop(animName);
        return new  AnimationHelper(controllerName,animName, anim, animName);
    }



    public static AnimationHelper playAnimation(String animName, String animEName) {
        RawAnimation anim = RawAnimation.begin().thenPlay(animEName);
        return new  AnimationHelper(animName, anim, animEName);
    }

    public static AnimationHelper playAnimation(String animName) {
        RawAnimation anim = RawAnimation.begin().thenPlay(animName);
        return new  AnimationHelper(animName, anim, animName);
    }

    public static AnimationHelper playAnimationWController(String controllerName,String animName, String animEName) {
        RawAnimation anim = RawAnimation.begin().thenPlay(animEName);
        return new  AnimationHelper(controllerName,animName, anim, animEName);
    }

    public static AnimationHelper playAnimationWController(String controllerName,String animName) {
        RawAnimation anim = RawAnimation.begin().thenPlay(animName);
        return new  AnimationHelper(controllerName,animName, anim, animName);
    }


    public AnimationHelper addLoop(String animName) {
        this.animation.thenLoop(animName);
        return this;
    }

    public AnimationHelper addPlay(String animName) {
        this.animation.thenPlay(animName);
        return this;
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

    public String getGetRawAnimName() {
        return getRawAnimName;
    }
}
