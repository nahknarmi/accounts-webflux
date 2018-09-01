package com.maquarie.java8.functions;

import org.junit.Test;

import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


public class Java8_Supplier {

    @Test
    public void supplier_get() {
        Supplier supplier = () -> "Hello";
        assertThat(supplier.get(), is("Hello"));
    }


    @Test
    public void booleanSupplier() {
        BooleanSupplier booleanSupplier = () -> false;
        Function<BooleanSupplier, Integer> booleanSupplierObjectFunction = (BooleanSupplier a) -> a.getAsBoolean() ?  1: 2;

        Integer someInteger = booleanSupplierObjectFunction.apply(booleanSupplier);

        assertThat(someInteger, equalTo(2));
    }
}
