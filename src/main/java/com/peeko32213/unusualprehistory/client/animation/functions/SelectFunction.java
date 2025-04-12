package com.peeko32213.unusualprehistory.client.animation.functions;

import com.eliotlash.mclib.math.IValue;
import com.eliotlash.mclib.math.functions.Function;

public class SelectFunction extends Function {
    public SelectFunction(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    public int getRequiredArguments() {
        return 3;
    }

    @Override
    public double get() {
        return this.getArg(0) == 1 ? this.getArg(1) : this.getArg(2);
    }
}
