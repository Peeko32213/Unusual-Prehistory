package com.peeko32213.unusualprehistory.client.animation.functions;

import com.eliotlash.mclib.math.IValue;
import com.eliotlash.mclib.math.functions.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class MathSmoothDamp extends Function {
    private final Minecraft minecraft;

    public MathSmoothDamp(IValue[] values, String name) throws Exception {
        super(values, name);
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public int getRequiredArguments() {
        return 4;
    }

    @Override
    public double get() {
        return smoothDamp(this.getArg(0), this.getArg(1), this.getArg(2), this.getArg(3), minecraft.getPartialTick());
    }

    public double smoothDamp(double current, double target, double currentVelocity, double smoothTime, double deltaTime) {
        // Based on Game Programming Gems 4 Chapter 1.10
        double maxSpeed = Double.MAX_VALUE; // Adjust as needed
        smoothTime = Math.max(0.0001F, smoothTime);
        double omega = 2F / smoothTime;

        double x = omega * deltaTime;
        double exp = 1F / (1F + x + 0.48F * x * x + 0.235F * x * x * x);
        double change = current - target;
        double originalTo = target;

        // Clamp maximum speed
        double maxChange = maxSpeed * smoothTime;
        change = Mth.clamp(change, -maxChange, maxChange);
        target = current - change;

        double temp = (currentVelocity + omega * change) * deltaTime;
        currentVelocity = (currentVelocity - omega * temp) * exp;
        double output = target + (change + temp) * exp;

        // Prevent overshooting
        if (originalTo - current > 0.0F == output > originalTo)
        {
            output = originalTo;
            currentVelocity = (output - originalTo) / deltaTime;
        }

        return output;
    }
}
