package com.maquarie.java8.functions;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8_Functions {


    @Test
    public void map() {
        List<Integer> list = Stream.of(1, 2, 3).map(x -> x * 2).collect(Collectors.toList());

        System.err.println(list);
    }

    @Test
    public void filter() {
        List<Integer> list = Stream.of(1, 2, 3).filter(x -> x % 2 == 0).collect(Collectors.toList());

        System.err.println(list);
    }

    @Test
    public void flatMapInts() {
        List<Float> list = Stream.of(1, 2, 3).flatMap((z) -> Stream.of(z.floatValue())).collect(Collectors.toList());
        System.err.println(list);
    }

    @Test
    public void flatMapListOfInts() {
        long count = Stream.of(Arrays.asList(1, 1), Arrays.asList(2, 2), Arrays.asList(3, 3))
                .flatMap(x -> Stream.of(x.stream().mapToInt(z -> z).sum()))
                .mapToLong(x -> x).sum();

        System.err.println(count);
    }

    @Test
    public void name() {
        IntStream firstStream = IntStream.range(1, 10);
        IntStream secondStream = IntStream.range(-10, 1);
    }

    @Test
    public void functionUsingApply() {
        //function<String,String> (function is a class with method apply)
        Function<String, String> addFullStopFunction = p -> p + ".";
        String appliedValue = addFullStopFunction.apply("some value");

        System.err.println(appliedValue);
    }


    @Test
    public void composeFunctions_RunsPassedFunctionBefore() {
        Function<String, String> yell = p -> p + " ahhhhh!!!";
        Function<String, String> capitalizeFunction = String::toUpperCase;

        Function<String, String> composedFunction = yell.compose(capitalizeFunction);

        System.err.println(composedFunction.apply("Hello World"));
    }


    @Test
    public void andThenFunctions_Afterwards() {
        Function<String, String> yell = p -> p + " ahhhhh!!!";
        Function<? super String, ?> after = String::toUpperCase;

        Function<String, ?> andThenFunction = yell.andThen(after);

        System.err.println(andThenFunction.apply("Hello World"));
    }
}
