package com.peeko32213.unusualprehistory.common.entity.util.interfaces;

public  interface IHatchableEntity {
    /**
     * Determines the variant of the entity based on the provided variant change value.
     * The variant change value is used to determine the specific variant of the entity.
     * The method sets the appropriate attributes and variant number based on the variant change value.
     *
     * @param variant The variant change value used to determine the entity's variant.
     *                      The value should be within the range [0, 100].
     */
     void determineVariant(int variant);
}
