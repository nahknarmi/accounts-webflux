package com.maquarie.java8.functions;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.function.BiFunction;

import static org.junit.Assert.assertThat;

public class Java8_Bifunction_Operator {

    @Test
    public void name() {
        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;

        assertThat(sum.apply(1, 1), Matchers.is(2));
    }
}
