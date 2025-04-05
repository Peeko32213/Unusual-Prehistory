package com.peeko32213.unusualprehistory.common.entity.util.kinematics;

import com.peeko32213.unusualprehistory.MathHelpers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import javax.vecmath.Quat4d;
import java.util.ArrayList;

public class IKSolver {
    public double prevYHeadRot = 0;
    public double deltaYHeadRot = 0;
    public boolean prevHasFlipped = false;

    private final LivingEntity entity;
    private final int nodeCount;
    private final double stiffness = 60;


    private Vec3[] nodes = {};
    private enum nodeLimits {POS_LIMIT, NEG_LIMIT}
    private int nodeDist;
    private int bodyLength;


    private double bodyPitch = 0;
    private double currentBodyPitch = 0;
    private double[] tailYaws = {};
    private double[] tailPitches = {};
    private double[] currentTailYaws = {};
    private double[] currentTailPitches = {};


    private Vec3 torsoFront;
    private Vec3 torsoFrontOffset = new Vec3(0, 0, -1);;

    private Vec3 torsoBack;
    private Vec3 torsoBackOffset = new Vec3(0, 0, 1);;

    private Vec3 rightRefPoint;
    private Vec3 rightRefOffset = new Vec3(1, 0, 0);

    private Vec3 leftRefPoint;
    private Vec3 leftRefOffset = new Vec3(-1, 0, 0);

    private Vec3 upRefPoint;
    private final Vec3 upRefOffset = new Vec3(0, -1, 0);

    private Vec3 downRefPoint;
    private final Vec3 downRefOffset = new Vec3(0, 1, 0);

    private Boolean shiftNodes = false;

    public IKSolver(LivingEntity entity, int nodeCount, int nodeDist) {

        this.entity = entity;
        this.nodeCount = nodeCount;
        //number of nodes^
        this.nodeDist = nodeDist;
        //distance between each node^
        //this.bodyLength = Math.min(nodeDist, (int) (entity.getBoundingBox().getXsize()/2));
        //Length of the body used to calculate body hitbox
        this.nodes = new Vec3[nodeCount];

        //this.torsoFrontOffset = new Vec3(0, 0, -bodyLength);
        //this.torsoBackOffset = new Vec3(0, 0, bodyLength);

        this.tailYaws = new double[nodeCount];
        this.tailPitches = new double[nodeCount];
        this.currentTailYaws = new double[nodeCount];
        this.currentTailPitches = new double[nodeCount];

        leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(leftRefOffset), (double) -entity.getYHeadRot());
        rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(rightRefOffset), (double) -entity.getYHeadRot());
        upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(upRefOffset), (double) -entity.getYHeadRot());
        downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(downRefOffset), (double) -entity.getYHeadRot());
        torsoFront = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(torsoFrontOffset), (double) -entity.getYHeadRot());
        torsoBack = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(torsoBackOffset), (double) -entity.getYHeadRot());


        initTailPoints();
    }

    public IKSolver(LivingEntity entity, int nodeCount, int nodeDist, boolean shiftNodes) {

        this.entity = entity;
        this.nodeCount = nodeCount;
        //number of nodes^
        this.nodeDist = nodeDist;
        //distance between each node^
        //this.bodyLength = Math.min(nodeDist, (int) (entity.getBoundingBox().getXsize()/2));
        //Length of the body used to calculate body hitbox
        this.nodes = new Vec3[nodeCount];

        //this.torsoFrontOffset = new Vec3(0, 0, -bodyLength);
        //this.torsoBackOffset = new Vec3(0, 0, bodyLength);

        this.tailYaws = new double[nodeCount];
        this.tailPitches = new double[nodeCount];
        this.currentTailYaws = new double[nodeCount];
        this.currentTailPitches = new double[nodeCount];

        leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(leftRefOffset), (double) -entity.getYHeadRot());
        rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(rightRefOffset), (double) -entity.getYHeadRot());
        upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(upRefOffset), (double) -entity.getYHeadRot());
        downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(downRefOffset), (double) -entity.getYHeadRot());
        torsoFront = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(torsoFrontOffset), (double) -entity.getYHeadRot());
        torsoBack = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(torsoBackOffset), (double) -entity.getYHeadRot());

        if (shiftNodes == true) {
            torsoFrontOffset = new Vec3(0, -1, -1);;
            torsoBackOffset = new Vec3(0, -1, 1);;
            this.shiftNodes = true;
        }

        initTailPoints();
    }


    private void initTailPoints() {
        Vec3 pos = entity.position();
        double yRot = -entity.getYHeadRot();

        // Initialize the first tail point (tail0) relative to the entity and the nose point
        torsoFront = MathHelpers.rotateAroundCenter3dDeg(this.entity.position(), this.entity.position().subtract(torsoFrontOffset), -this.entity.getYHeadRot(), -this.entity.getXRot());
        torsoBack = MathHelpers.rotateAroundCenter3dDeg(this.entity.position(), this.entity.position().subtract(torsoBackOffset), -this.entity.getYHeadRot(), -this.entity.getXRot());

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
        if (entity.level().isClientSide()) {

            // torsoFront corresponds to the start of the body, torsoBack correspond to the back of the body(start of the tail).
            torsoFront = MathHelpers.rotateAroundCenter3dDeg(entity.position(), entity.position().subtract(torsoFrontOffset), -entity.getYHeadRot(), -entity.getXRot());
            torsoBack = MathHelpers.rotateAroundCenter3dDeg(entity.position(), entity.position().subtract(torsoBackOffset), -entity.getYHeadRot(), -entity.getXRot());

            ArrayList<Vec3> prevChain = new ArrayList<Vec3>();
            prevChain.add(torsoFront);
            prevChain.add(entity.position());
            prevChain.add(torsoBack);
            //adds the chain that represents the creature's body first

            // Chain-update subsequent tail points after no longer needing torso segments.
            for (int i = 0; i < nodeCount; i++) {
                nodes[i] = shiftNodes ? nodes[i] = nodes[i].subtract(0, -1, 0) : nodes[i];
                nodes[i] = MathHelpers.distConstraint(prevChain, nodes[i], nodeDist);
                prevChain.add(nodes[i]);
            }

            // Update Geckolib - usable bone angles for each node.
            tailYaws[0] = Math.toRadians(MathHelpers.angleTo(entity.position(), torsoBack).y - MathHelpers.angleTo(torsoBack, nodes[0]).y);
            tailYaws[1] = Math.toRadians(MathHelpers.angleTo(torsoBack, nodes[0]).y - MathHelpers.angleTo(nodes[0], nodes[1]).y);

            for (int i = 2; i < nodes.length; i++) {
                tailYaws[i] = Math.toRadians(MathHelpers.angleTo(nodes[i - 2], nodes[i - 1]).y - MathHelpers.angleTo(nodes[i - 1], nodes[i]).y);
            }
            //Yaw

            tailPitches[0] = ((float) (Mth.PI * MathHelpers.angleFromYdiff(torsoBack, this.nodes[0], this.nodes[1])));
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

    }

    public void calculateTailAnglesNoConstraint(LivingEntity entity) {
        if (entity.level().isClientSide()) {

            // torsoFront corresponds to the start of the body, torsoBack correspond to the back of the body(start of the tail).
            torsoFront = MathHelpers.rotateAroundCenter3dDeg(entity.position(), entity.position().subtract(torsoFrontOffset), -entity.getYHeadRot(), -entity.getXRot());
            torsoBack = MathHelpers.rotateAroundCenter3dDeg(entity.position(), entity.position().subtract(torsoBackOffset), -entity.getYHeadRot(), -entity.getXRot());

            // Chain-update subsequent tail points after no longer needing torso segments.
            nodes[0] = nodes[0].subtract(0, -1, 0);
            nodes[0] = MathHelpers.distConstraintSingle(torsoBack, nodes[0], nodeDist);
            nodes[0] = nodes[0].subtract(0, -1, 0);
            for (int i = 1; i < nodeCount; i++) {
                nodes[i] = nodes[i].subtract(0, -1, 0);
                nodes[i] = MathHelpers.distConstraintSingle(nodes[i - 1], nodes[i], nodeDist);
                nodes[i] = nodes[i].subtract(0, -1, 0);
            }

            // Update Geckolib - usable bone angles for each node.
            tailYaws[0] = Math.toRadians(MathHelpers.angleTo(entity.position(), torsoBack).y - MathHelpers.angleTo(torsoBack, nodes[0]).y);
            tailYaws[1] = Math.toRadians(MathHelpers.angleTo(torsoBack, nodes[0]).y - MathHelpers.angleTo(nodes[0], nodes[1]).y);

            for (int i = 2; i < nodes.length; i++) {
                tailYaws[i] = Math.toRadians(MathHelpers.angleTo(nodes[i - 2], nodes[i - 1]).y - MathHelpers.angleTo(nodes[i - 1], nodes[i]).y);
            }
            //Yaw

            tailPitches[0] = ((float) (Mth.PI * MathHelpers.angleFromYdiff(torsoBack, this.nodes[0], this.nodes[1])));
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

    }

    private boolean isSuspectedCompletedRotation(float lastRotation) {
        //lastRotation = Math.abs(lastRotation) - 270;
        float rotations = Mth.abs(lastRotation / (90f * Mth.DEG_TO_RAD));
        float partialRotation = 1 - (rotations - (int)rotations);

        return partialRotation < 0.026 * rotations;
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
            //ServerLevel L = (ServerLevel) level;
            //L.sendParticles(ParticleTypes.BUBBLE_POP, (entity.getX()), (entity.getY() + 2), (entity.getZ()), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            //L.sendParticles(ParticleTypes.BUBBLE, (torsoFront.x), (torsoFront.y + 2), (torsoFront.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            //L.sendParticles(ParticleTypes.BUBBLE_POP, (torsoBack.x), (torsoBack.y + 2), (torsoBack.z), 1, 0.0D, 0.0D, 0.0D, 0.0D);

            //L.sendParticles(ParticleTypes.BUBBLE_POP, (nodes[0].x), (nodes[0].y + 2), (nodes[0].z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            //for (int i = 1; i < nodeCount; i++) {
            //    L.sendParticles(ParticleTypes.BUBBLE, (nodes[i].x), (nodes[i].y + 2), (nodes[i].z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            //}
        }
    }

}
