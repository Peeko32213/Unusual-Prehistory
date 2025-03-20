package com.peeko32213.unusualprehistory.common.entity.util.kinematics;

import com.peeko32213.unusualprehistory.MathHelpers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Collections;

public class IKSolver {
    private final LivingEntity entity;
    private final int nodeCount;
    private final double stiffness = Mth.PI*0.50;


    private Vec3[] nodes = {};
    private int nodeDist;


    private double bodyPitch = 0;
    private double currentBodyPitch = 0;
    private double[] tailYaws = {};
    private double[] tailPitches = {};
    private double[] currentTailYaws = {};
    private double[] currentTailPitches = {};


    private Vec3 nosePoint;
    private Vec3 noseOffset = new Vec3(0, 0, -1);
    private Vec3 rightRefPoint;
    private Vec3 rightRefOffset = new Vec3(1, 0, 0);

    private Vec3 leftRefPoint;
    private Vec3 leftRefOffset = new Vec3(-1, 0, 0);

    private Vec3 upRefPoint;
    private Vec3 upRefOffset = new Vec3(0, -1, 0);

    private Vec3 downRefPoint;
    private Vec3 downRefOffset = new Vec3(0, 1, 0);


    public IKSolver(LivingEntity entity, int nodeCount, int nodeDist) {

        this.entity = entity;
        this.nodeCount = nodeCount;
        //number of nodes^
        this.nodeDist = nodeDist;
        //distance between each node^
        this.nodes = new Vec3[nodeCount];

        this.tailYaws = new double[nodeCount];
        this.tailPitches = new double[nodeCount];
        this.currentTailYaws = new double[nodeCount];
        this.currentTailPitches = new double[nodeCount];

        leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(leftRefOffset), (double) -entity.getYHeadRot());
        rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(rightRefOffset), (double) -entity.getYHeadRot());
        upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(upRefOffset), (double) -entity.getYHeadRot());
        downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(downRefOffset), (double) -entity.getYHeadRot());
        nosePoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(noseOffset), (double) -entity.getYHeadRot());


        initTailPoints();
    }


    private void initTailPoints() {
        Vec3 pos = entity.position();
        double yRot = -entity.getYHeadRot();

        // Initialize the first tail point (tail0) relative to the entity and the nose point
        nosePoint = MathHelpers.rotateAroundCenter3dDeg(this.entity.position(), this.entity.position().subtract(noseOffset), -this.entity.getYHeadRot(), -this.entity.getXRot());
        nodes[0] = MathHelpers.rotateAroundCenterFlatDeg(this.entity.position(), this.entity.position().subtract(nodeDist, 0, nodeDist), yRot);

        // Chain the rotations for subsequent tail segments.
        for (int i = 1; i < nodeCount; i++) {
            nodes[i] = MathHelpers.rotateAroundCenterFlatDeg(
                    nodes[i - 1],
                    nodes[i - 1].subtract(nodeDist, 0, nodeDist),
                yRot
            );
        }
    }

    /**
     * Fully update the IK angles and re-calculate the rotated positions.
     */
    public void calculateTailAngles(LivingEntity entity) {

        // Update the first node that can be attributed to the tail(the root of the tail) and nosepoint.
        nosePoint = MathHelpers.rotateAroundCenter3dDeg(entity.position(), entity.position().subtract(noseOffset), -entity.getYHeadRot(), -entity.getXRot());
        nodes[0] = MathHelpers.distConstraint(entity.position().subtract(0, 0, 0), nodes[0], nodeDist);

        // Chain-update subsequent tail points.
        for (int i = 1; i < nodeCount; i++) {
            nodes[i] = MathHelpers.distConstraint(nodes[i - 1], nodes[i], nodeDist);
        }

        double node0Angle = MathHelpers.constrainAngle(MathHelpers.getAngleForLinkTopDownFlat(this.nosePoint, entity.position(), this.nodes[0], this.leftRefPoint, this.rightRefPoint), stiffness);
        System.out.println(node0Angle);
        nodes[0] = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), nodes[0],  (node0Angle*Mth.RAD_TO_DEG));
        System.out.println(MathHelpers.getAngleForLinkTopDownFlat(this.nosePoint, entity.position(), this.nodes[0], this.leftRefPoint, this.rightRefPoint));

        double node1Angle = MathHelpers.constrainAngle(MathHelpers.getAngleForLinkTopDownFlat(entity.position(), this.nodes[0], this.nodes[1], this.leftRefPoint, this.rightRefPoint), stiffness);
        System.out.println(node1Angle);
        nodes[1] = MathHelpers.rotateAroundCenterFlatDeg(nodes[0], nodes[1],  (node1Angle*Mth.RAD_TO_DEG));
        System.out.println(MathHelpers.getAngleForLinkTopDownFlat(entity.position(), this.nodes[0], this.nodes[1], this.leftRefPoint, this.rightRefPoint));

        for (int i = 2; i < nodes.length; i++) {
            double nodeAngle = MathHelpers.constrainAngle(MathHelpers.getAngleForLinkTopDownFlat(this.nodes[i - 2], this.nodes[i - 1], this.nodes[i], this.leftRefPoint, this.rightRefPoint), stiffness);
            System.out.println(nodeAngle);
            nodes[i] = MathHelpers.rotateAroundCenterFlatDeg(nodes[i - 1], nodes[i], (nodeAngle*Mth.RAD_TO_DEG));
            System.out.println(node1Angle);
        }

        System.out.println("---------------------------------------------------------------------------------------------");






        // Update Geckolib - usable bone angles for each node.
        tailYaws[0] = ((MathHelpers.getAngleForLinkTopDownFlat(entity.position(), this.nosePoint, this.nodes[0], this.leftRefPoint, this.rightRefPoint)));
        tailYaws[1] = ((MathHelpers.getAngleForLinkTopDownFlat(this.nodes[0], this.entity.position(), this.nodes[1], this.leftRefPoint, this.rightRefPoint)));

        for (int i = 2; i < nodes.length; i++) {
            tailYaws[i] = ((MathHelpers.getAngleForLinkTopDownFlat(this.nodes[i - 2], this.nodes[i - 1], this.nodes[i], this.leftRefPoint, this.rightRefPoint)));
        }
        //Yaw

        tailPitches[0] = ((float) (Mth.PI * MathHelpers.angleFromYdiff(entity.position(), this.nodes[0], this.nodes[1])));;
        for (int i = 1; i < nodes.length - 1; i++) {
            tailYaws[i] = ((MathHelpers.getAngleForLinkTopDownFlat(this.nodes[i - 1], this.nodes[i], this.nodes[i + 1], this.leftRefPoint, this.rightRefPoint)));
        }
        //Pitch


        //side refs don't move vertically
        leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), this.entity.position().subtract(leftRefOffset), (double) -entity.getYHeadRot());
        rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), this.entity.position().subtract(rightRefOffset), (double) -entity.getYHeadRot());
        upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), this.entity.position().subtract(upRefOffset), (double) -entity.getYHeadRot());
        downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), this.entity.position().subtract(downRefOffset), (double) -entity.getYHeadRot());
        //END of IK

    }

    public double[] getTailYaws() {
        return tailYaws;
    }

    public double[] getTailPitches() {
        return tailPitches;
    }

    public double[] getCurrentTailYaws() {
        return currentTailYaws;
    }

    public double[] getCurrentTailPitches() {
        return currentTailPitches;
    }

    public double getBodyPitch() {
        return bodyPitch;
    }

    public double getCurrentBodyPitch() {
        return currentBodyPitch;
    }

    public void visualizeNodes(Level level) {
        if (!level.isClientSide()) {
            ServerLevel L = (ServerLevel) level;
            L.sendParticles(ParticleTypes.BUBBLE, (entity.getX()), (entity.getY() + 2), (entity.getZ()), 1, 0.0D, 0.0D, 0.0D, 0.0D);

            for (int i = 0; i < nodeCount; i++) {
                L.sendParticles(ParticleTypes.BUBBLE, (nodes[i].x), (nodes[i].y + 2), (nodes[i].z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
