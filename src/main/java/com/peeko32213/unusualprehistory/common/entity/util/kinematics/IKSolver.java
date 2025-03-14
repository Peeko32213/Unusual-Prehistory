package com.peeko32213.unusualprehistory.common.entity.util.kinematics;

import com.peeko32213.unusualprehistory.MathHelpers;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public class IKSolver {
    private final LivingEntity entity;
    private final int numTailSegments;
    
    public Vec3 noseOffset;
    public Vec3[] tailOffsets;
    public Vec3 leftRefOffset;
    public Vec3 rightRefOffset;
    public Vec3 upRefOffset;
    public Vec3 downRefOffset;
    
    public Vec3 nosePoint;
    public Vec3[] tailPoints;
    public Vec3 leftRefPoint;
    public Vec3 rightRefPoint;
    public Vec3 upRefPoint;
    public Vec3 downRefPoint;
    
    public double bodyPitch = 0;
    public double currentBodyPitch = 0;
    public double[] tailAngles;
    public double[] tailPitches;

    public IKSolver(LivingEntity entity, int numTailSegments,
                    Vec3 noseOffset, 
                    Vec3[] tailOffsets,
                    Vec3 leftRefOffset, Vec3 rightRefOffset, Vec3 upRefOffset, Vec3 downRefOffset) {
        if (tailOffsets.length != numTailSegments) {
            throw new IllegalArgumentException("The length of tailOffsets must equal numTailSegments");
        }
        this.entity = entity;
        this.numTailSegments = numTailSegments;
        this.noseOffset = noseOffset;
        this.tailOffsets = tailOffsets;
        this.leftRefOffset = leftRefOffset;
        this.rightRefOffset = rightRefOffset;
        this.upRefOffset = upRefOffset;
        this.downRefOffset = downRefOffset;
        
        this.tailPoints = new Vec3[numTailSegments];
        this.tailAngles = new double[numTailSegments];
        this.tailPitches = new double[numTailSegments];
        
        initReferencePoints();
        initTailPoints();
    }

    private void initReferencePoints() {
        Vec3 pos = entity.position();
        double yRot = -entity.getYRot();

        leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(leftRefOffset), yRot);
        rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(rightRefOffset), yRot);
        upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(upRefOffset), yRot);
        downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(downRefOffset), yRot);

        // Initialize nose point using the provided nose offset.
        nosePoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(noseOffset), yRot);
    }

    private void initTailPoints() {
        Vec3 pos = entity.position();
        double yRot = -entity.getYRot();

        // Initialize the first tail point (tail0) relative to the entity.
        tailPoints[0] = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(tailOffsets[0]), yRot);

        // Chain the rotations for subsequent tail segments.
        for (int i = 1; i < numTailSegments; i++) {
            tailPoints[i] = MathHelpers.rotateAroundCenterFlatDeg(
                tailPoints[i - 1],
                tailPoints[i - 1].subtract(tailOffsets[i]),
                yRot
            );
        }
    }

    /**
     * Fully update the IK angles and re-calculate the rotated positions.
     */
    public void calculateTailAngles(LivingEntity entity) {

        for (int i = 1; i < numTailSegments - 1; i++) {
            tailAngles[i] = MathHelpers.angleClamp(
                MathHelpers.getAngleForLinkTopDownFlat(
                    tailPoints[i],
                    tailPoints[i - 1],
                    tailPoints[i + 1],
                    leftRefPoint,
                    rightRefPoint
                ),
                Math.PI * 0.75
            );
        }
        

        bodyPitch = Math.PI * MathHelpers.angleFromYdiff(nosePoint, entity.position(), tailPoints[0]);

        if (numTailSegments > 1) {
            tailPitches[1] = Math.PI * MathHelpers.angleFromYdiff(entity.position(), tailPoints[0], tailPoints[1]);
        }

        for (int i = 2; i < numTailSegments; i++) {
            tailPitches[i] = Math.PI * MathHelpers.angleFromYdiff(tailPoints[i - 2], tailPoints[i - 1], tailPoints[i]);
        }
        

        nosePoint = MathHelpers.rotateAroundCenter3dDeg(
            entity.position(),
            entity.position().subtract(noseOffset),
            -entity.getYRot(),
            -entity.getXRot()
        );
        
        // Update the first tail point (tail0) with 3D rotation.
        tailPoints[0] = MathHelpers.rotateAroundCenter3dDeg(
            entity.position(),
            entity.position().subtract(tailOffsets[0]),
            -entity.getYRot(),
            -entity.getXRot()
        );
        
        // Chain-update subsequent tail points.
        for (int i = 1; i < numTailSegments; i++) {
            Vec3 previousPoint = tailPoints[i - 1];

            Vec3 currentPoint = tailPoints[i];
            float angleY = -MathHelpers.angleTo(previousPoint, currentPoint).y;
            float angleX = -MathHelpers.angleTo(previousPoint, currentPoint).x;
            
            tailPoints[i] = MathHelpers.rotateAroundCenter3dDeg(
                previousPoint,
                previousPoint.subtract(tailOffsets[i]),
                angleY,
                angleX
            );
        }
        
        // --- Update Reference Points (They Do Not Change Vertically) ---
        double flatYRot = -entity.getYRot();
        Vec3 pos = entity.position();
        leftRefPoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(leftRefOffset), flatYRot);
        rightRefPoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(rightRefOffset), flatYRot);
        upRefPoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(upRefOffset), flatYRot);
        downRefPoint = MathHelpers.rotateAroundCenterFlatDeg(pos, pos.subtract(downRefOffset), flatYRot);
    }

    public double[] getTailAngles() {
        return tailAngles;
    }

    public double[] getTailPitches() {
        return tailPitches;
    }

    public double getBodyPitch() {
        return bodyPitch;
    }

    public double getCurrentBodyPitch() {
        return currentBodyPitch;
    }
}
