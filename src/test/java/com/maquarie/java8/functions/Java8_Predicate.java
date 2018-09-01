package com.maquarie.java8.functions;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class Java8_Predicate {


    @Test
    public void testMethod() {
        Predicate<Integer> evenNumbers = x -> x % 2 == 0;

        boolean test = evenNumbers.test(2);
        assertThat(test, is(true));
    }

    @Test
    public void andMethod() {
        Predicate<Integer> evenNumbers = x -> x % 2 == 0;
        Predicate<Integer> greaterThan10 = x -> x > 10;

        Predicate<Integer> combinedAndFunction = evenNumbers.and(greaterThan10);

        assertThat(combinedAndFunction.test(1), is(false));
        assertThat(combinedAndFunction.test(11), is(false));
        assertThat(combinedAndFunction.test(2), is(false));
        assertThat(combinedAndFunction.test(12), is(true));
        assertThat(combinedAndFunction.test(10), is(false));
    }

    @Test
    public void negateMethod() {
        Predicate<Integer> evenNumbers = x -> x % 2 == 0;
        Predicate<Integer> negated = evenNumbers.negate();

        assertThat(negated.test(11), is(true));
    }

    @Test
    public void biPredicate() {
        BiPredicate<String, Integer> biPredicate = (a, i) -> a.isEmpty() && i < 10;

        boolean test = biPredicate.test("", 100);

    }
}
