package com.maquarie.java8.functions;

import org.junit.Test;

import java.util.function.BinaryOperator;

public class Java8_BinaryOperator {

    @Test
    public void name() {
        BinaryOperator<String> stringBinaryOperator = (s, s2) -> s + " " + s2;

        String apply = stringBinaryOperator.apply("a", "b");
        System.err.println(apply);
    }
}
