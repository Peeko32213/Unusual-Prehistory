package com.peeko32213.unusualprehistory.common.entity.util.helper;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class MathHelpers  {

    public static Vec2 OrizontalAimVector(Vec3 aim){

        Vec2 vec = new Vec2((float)(aim.x/(1-Math.abs(aim.y))), (float)(aim.z/(1-Math.abs(aim.y))));

        return vec;
    }

    public static Vec3 AimVector(Vec3 pos, Vec3 targetPos){

        double d = Math.sqrt((targetPos.x - pos.x)*(targetPos.x - pos.x) + (targetPos.z - pos.z)*(targetPos.z - pos.z) + (targetPos.y - pos.y)*(targetPos.y - pos.y));


        Vec3 aim = new Vec3((targetPos.x - pos.x)/d, (targetPos.y - pos.y)/d, (targetPos.z - pos.z)/d);


        return aim;


    }

    @Nullable
    public static Vec3 getRandomSwimmablePosThatIsntTheSameDepth(PathfinderMob pPathfinder, int pRadius, int pVerticalDistance) {
        Vec3 testPos = DefaultRandomPos.getPos(pPathfinder, pRadius, pVerticalDistance);
        int MaxSearchAmount = pRadius*pRadius*pRadius;

        for (int x = 0; testPos != null && x < MaxSearchAmount; testPos = DefaultRandomPos.getPos(pPathfinder, pRadius, pVerticalDistance), x ++) {

            if (testPos != null) {

                if (Math.abs(Math.abs(testPos.y) - Math.abs(pPathfinder.position().y())) > 1) {
                    return testPos;
                }

                if (x == MaxSearchAmount - 1) {
                    return testPos;
                    //basically this keeps the entity moving if it can't find somewhere to go
                    //might be useful for drowned farms or smthing
                }
            }
        }

        return null;
    }

}