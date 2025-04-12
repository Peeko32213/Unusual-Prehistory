package com.peeko32213.unusualprehistory.client.animation.functions;

import com.eliotlash.mclib.math.IValue;
import com.eliotlash.mclib.math.functions.Function;

public class OrFunction extends Function {
    public OrFunction(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    public int getRequiredArguments() {
        return 2;
    }

    @Override
    public double get() {
        for (int i = 0; i < getRequiredArguments(); i++) {
            if (this.getArg(i) != 0.0) {
                return 1.0;
            }
        }
        return 0.0;
    }
}
