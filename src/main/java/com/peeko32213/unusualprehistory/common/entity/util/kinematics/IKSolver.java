package com.peeko32213.unusualprehistory.common.entity.util.kinematics;

import com.peeko32213.unusualprehistory.MathHelpers;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class IKSolver {
    public double prevYHeadRot = 0;
    public double deltaYHeadRot = 0;

    private final LivingEntity entity;
    private final int nodeCount;
    private final double stiffness = 60;


    private Vec3[] nodes = {};
    private enum nodeLimits {POS_LIMIT, NEG_LIMIT}
    private int nodeDist;


    private double bodyPitch = 0;
    private double currentBodyPitch = 0;
    private double[] tailYaws = {};
    private double[] tailPitches = {};
    private double[] currentTailYaws = {};
    private double[] currentTailPitches = {};


    private Vec3 torsoFront;
    private Vec3 torsoFrontOffset = new Vec3(0, 0, -1);

    private Vec3 torsoBack;
    private Vec3 torsoBackOffset = new Vec3(0, 0, 1);

    private Vec3 rightRefPoint;
    private final Vec3 rightRefOffset = new Vec3(1, 0, 0);

    private Vec3 leftRefPoint;
    private final Vec3 leftRefOffset = new Vec3(-1, 0, 0);

    private Vec3 upRefPoint;
    private final Vec3 upRefOffset = new Vec3(0, -1, 0);

    private Vec3 downRefPoint;
    private final Vec3 downRefOffset = new Vec3(0, 1, 0);


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
        torsoFront = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(torsoFrontOffset), (double) -entity.getYHeadRot());
        torsoBack = MathHelpers.rotateAroundCenterFlatDeg(entity.position(), entity.position().subtract(torsoBackOffset), (double) -entity.getYHeadRot());


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

        deltaYHeadRot = prevYHeadRot-entity.getYHeadRot();
        prevYHeadRot = entity.getYHeadRot();

        System.out.println(deltaYHeadRot);

        // torsoFront corresponds to the start of the body, torsoBack correspond to the back of the body(start of the tail).
        torsoFront = MathHelpers.rotateAroundCenter3dDeg(entity.position(), entity.position().subtract(torsoFrontOffset), -entity.getYHeadRot(), -entity.getXRot());
        torsoBack = MathHelpers.rotateAroundCenter3dDeg(entity.position(), entity.position().subtract(torsoBackOffset),  -entity.getYHeadRot(), -entity.getXRot());


        double entityDir = MathHelpers.makeAlwaysPositive((double) MathHelpers.angleTo(entity.position(), torsoBack).y);
        System.out.println(entityDir);
        //heading angle of the entity
        //51 = facing 51 degrees to the west of south

        double node0Angle = MathHelpers.makeAlwaysPositive((double) MathHelpers.angleTo(torsoBack, nodes[0]).y);
        System.out.println(node0Angle);
        //heading angle of the tail segment(from back to front, which direction it's pointing)
        //51 = facing 51 degrees to the west of south(first variable is the front)

        double relativeAngle = entityDir - node0Angle;
        System.out.println(relativeAngle);
        //angle of the tail relative to the body(how much it's bent)
        //57 = bent to the right by 57 degrees

        double constrainedAngle = MathHelpers.constrainAngle(relativeAngle, stiffness);
        System.out.println(constrainedAngle);
        //constrain the amount of bend

        double node0AngleReal = (constrainedAngle - entityDir);
        System.out.println(node0AngleReal);
        //get the angle relative to the world the tail will have after it has been bent
        //this should correspond to node0Angle but negative

        nodes[0] = MathHelpers.rotateAroundCenter3dDeg(torsoBack, torsoBack.subtract(0, 0, nodeDist), (float) node0AngleReal, -MathHelpers.angleTo(torsoBack, nodes[0]).x);
        //rotate the node according to node0AngleReal



        double node0Dir = MathHelpers.makeAlwaysPositive((double) MathHelpers.angleTo(torsoBack, nodes[0]).y);
        //System.out.println(node0Dir);
        //heading angle of the entity
        //51 = facing 51 degrees to the west of south

        double node1Angle = MathHelpers.makeAlwaysPositive((double) MathHelpers.angleTo(nodes[0], nodes[1]).y);
        //System.out.println(node1Angle);
        //heading angle of the tail segment(from back to front, which direction it's pointing)
        //51 = facing 51 degrees to the west of south(first variable is the front)

        double relativeAngle1 = node0Dir - node1Angle;
        //System.out.println(relativeAngle1);
        //angle of the tail relative to the body(how much it's bent)
        //57 = bent to the right by 57 degrees

        double constrainedAngle1 = MathHelpers.constrainAngle(relativeAngle1, stiffness);
        //System.out.println(constrainedAngle1);
        //constrain the amount of bend

        double node1AngleReal = (constrainedAngle1 - entityDir);
        //System.out.println(node1AngleReal);
        //get the angle relative to the world the tail will have after it has been bent
        //this should correspond to node0Angle but negative

        nodes[1] = MathHelpers.rotateAroundCenter3dDeg(nodes[0], nodes[0].subtract(0, 0, nodeDist), (float) -node1AngleReal, -MathHelpers.angleTo(nodes[0], nodes[1]).x);
        //rotate the node according to node0AngleReal


        // Chain-update subsequent tail points.
        for (int i = 2; i < nodeCount; i++) {

            double prevNodeDir = MathHelpers.makeAlwaysPositive((double) MathHelpers.angleTo(nodes[i - 2], nodes[i - 1]).y);
            //System.out.println(prevNodeDir);
            //heading angle of the entity
            //51 = facing 51 degrees to the west of south

            double nodeiAngle = MathHelpers.makeAlwaysPositive((double) MathHelpers.angleTo(nodes[i - 1], nodes[i]).y);
            //System.out.println(nodeiAngle);
            //heading angle of the tail segment(from back to front, which direction it's pointing)
            //51 = facing 51 degrees to the west of south(first variable is the front)

            double relativeiAngle = prevNodeDir - nodeiAngle;
            //System.out.println(relativeiAngle);
            //angle of the tail relative to the body(how much it's bent)
            //57 = bent to the right by 57 degrees

            double constrainediAngle = MathHelpers.constrainAngle(relativeiAngle, stiffness);
            //System.out.println(constrainediAngle);
            //constrain the amount of bend

            double nodeiAngleReal = (constrainediAngle - prevNodeDir);
            //System.out.println(nodeiAngleReal);
            //get the angle relative to the world the tail will have after it has been bent
            //this should correspond to node0Angle but negative

            nodes[i] = MathHelpers.rotateAroundCenter3dDeg(nodes[i - 1], nodes[i - 1].subtract(0, 0, nodeDist), (float) -nodeiAngleReal, -MathHelpers.angleTo(nodes[i - 1], nodes[i]).x);
        }

        System.out.println("-------------------------");
        //everything above takes in and outputs degrees




        // Update Geckolib - usable bone angles for each node.
        tailYaws[0] = Math.toRadians(MathHelpers.angleTo(entity.position(), torsoBack).y - MathHelpers.angleTo(torsoBack, nodes[0]).y);
        System.out.println(tailYaws[0] * Mth.RAD_TO_DEG);
        System.out.println("---------------------------------------------------------------------------------------------");
        tailYaws[1] = Math.toRadians(MathHelpers.angleTo(torsoBack, nodes[0]).y - MathHelpers.angleTo(nodes[0], nodes[1]).y);

        for (int i = 2; i < nodes.length; i++) {
            //tailYaws[i] = (MathHelpers.getAngleForLinkTopDownFlat(this.nodes[i - 2], this.nodes[i - 1], this.nodes[i], leftRefPoint, rightRefPoint));
            tailYaws[i] = Math.toRadians(MathHelpers.angleTo(nodes[i - 2], nodes[i - 1]).y - MathHelpers.angleTo(nodes[i - 1], nodes[i]).y);
        }
        //Yaw

        tailPitches[0] = ((float) (Mth.PI * MathHelpers.angleFromYdiff(torsoBack, this.nodes[0], this.nodes[1])));;
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

            L.sendParticles(ParticleTypes.BUBBLE_POP, (nodes[0].x), (nodes[0].y + 2), (nodes[0].z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            for (int i = 1; i < nodeCount; i++) {
                L.sendParticles(ParticleTypes.BUBBLE, (nodes[i].x), (nodes[i].y + 2), (nodes[i].z), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

}
