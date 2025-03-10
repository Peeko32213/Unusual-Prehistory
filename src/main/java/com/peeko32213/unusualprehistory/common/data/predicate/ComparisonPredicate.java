package com.peeko32213.unusualprehistory.common.data.predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.function.BiPredicate;

public class ComparisonPredicate {
    public enum Operator {
        EQUALS("equals", Integer::equals),
        NOT_EQUALS("not_equals", (a, b) -> !a.equals(b)),
        GREATER_THAN("greater_than", (a, b) -> a > b),
        GREATER_THAN_OR_EQUAL("greater_than_or_equal", (a, b) -> a >= b),
        LESS_THAN("less_than", (a, b) -> a < b),
        LESS_THAN_OR_EQUAL("less_than_or_equal", (a, b) -> a <= b);

        private final String symbol;
        private final BiPredicate<Integer, Integer> predicate;

        Operator(String symbol, BiPredicate<Integer, Integer> predicate) {
            this.symbol = symbol;
            this.predicate = predicate;
        }

        public boolean compare(int a, int b) {
            return predicate.test(a, b);
        }

        public static final Codec<Operator> CODEC = Codec.STRING.xmap(
                symbol -> {
                    for (Operator op : values()) {
                        if (op.symbol.equals(symbol)) {
                            return op;
                        }
                    }
                    throw new IllegalArgumentException("Unknown operator: " + symbol);
                },
                operator -> operator.symbol
        );
    }

    public static final Codec<ComparisonPredicate> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.fieldOf("value").forGetter(ComparisonPredicate::getValue),
                    Operator.CODEC.fieldOf("operator").forGetter(ComparisonPredicate::getOperator)
            ).apply(instance, ComparisonPredicate::new)
    );

    private final int value;
    private final Operator operator;

    public ComparisonPredicate(int value, Operator operator) {
        this.value = value;
        this.operator = operator;
    }

    public boolean check(int actualValue) {
        return operator.compare(actualValue, value);
    }

    public Operator getOperator() {
        return operator;
    }

    public int getValue() {
        return value;
    }
}
