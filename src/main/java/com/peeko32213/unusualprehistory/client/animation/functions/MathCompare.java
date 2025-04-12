package com.peeko32213.unusualprehistory.client.animation.functions;

import com.eliotlash.mclib.math.IValue;
import com.eliotlash.mclib.math.functions.Function;

public class MathCompare extends Function {
    public MathCompare(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    public int getRequiredArguments() {
        return 2;
    }

    @Override
    public double get() {
        return this.getArg(0) == this.getArg(1) ? 1 : 0;
    }
}