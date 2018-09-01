package com.maquarie.java8.functions;

import org.junit.Test;

import java.util.function.Consumer;

public class Java8_Consumer {


    @Test
    public void accept() {
        Consumer<Integer> consumer = (x) -> System.err.println(x);
        consumer.accept(1);
    }

    @Test
    public void andThen() {
        Consumer<Integer> consumer = (x) -> System.err.println(x);
        Consumer<Integer> andThen = consumer.andThen(x -> System.out.println(x));

        andThen.accept(1);
    }
}
