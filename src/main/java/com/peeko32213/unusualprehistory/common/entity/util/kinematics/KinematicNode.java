package com.peeko32213.unusualprehistory.common.entity.util.kinematics;

import com.mojang.serialization.Codec;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class KinematicNode  {

    public final Vec2 direction;
    public final Vec3 position;


    public KinematicNode(double pX, double pY, double pZ, Vec2 direction) {
        this.position = new Vec3(pX, pY, pZ);
        this.direction = direction;
    }

    public KinematicNode(Vec3 vector3) {
        this.position = vector3;
        this.direction = Vec2.ZERO;
    }

    public KinematicNode(Vector3f vector3) {
        this.position = new Vec3(vector3);
        this.direction = Vec2.ZERO;
    }
}
