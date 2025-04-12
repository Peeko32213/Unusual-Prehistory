package com.peeko32213.unusualprehistory.client.animation.functions;

import com.eliotlash.mclib.math.IValue;
import com.eliotlash.mclib.math.functions.Function;

public class MathInverse extends Function {
    public MathInverse(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    public int getRequiredArguments() {
        return 1;
    }

    @Override
    public double get() {
        if(this.getArg(0) > 1 || this.getArg(0) < 0) throw new  UnsupportedOperationException("math.inv value" + this.getArg(0) +  " was greater than 1 or smaller than 0");
        int val = (int) this.getArg(0);
        return val == 1 ? 0 : 1;
    }
}