package com.maquarie.java8.stream;

import io.vavr.Tuple2;
import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.functions.Func2;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.IntStream;

public class Java8_Streams {

    private List<Student> students;

    @Before
    public void setUp() throws Exception {
        students = Arrays.asList(
                new Student(Arrays.asList(99), "John"),
                new Student(Arrays.asList(50), "Louise"));
    }

    @Test
    public void map_filter_count() {
        long count = IntStream.rangeClosed(0, 10)
                .mapToObj(String::valueOf)
                .filter(s -> s.length() > 1)
                .count();

        System.err.println(count);
    }

    @Test
    public void name() {
        int reduce = IntStream.rangeClosed(0, 5)
                .reduce((accumulator, element) -> accumulator + element)
                .orElse(-1);

        System.err.println(reduce);
    }


    @Test
    public void flatMap() {
        double average = students
                .stream()
                .flatMapToInt(s -> s.grades.stream().mapToInt(Integer::intValue))
                .summaryStatistics()
                .getAverage();

        System.err.println(average);
    }

    @Test
    public void collect() {
        OptionalDouble average = students
                .stream()
                .flatMapToInt(student -> student.grades.stream().mapToInt(Integer::valueOf))
                .average();

        System.err.println(average.orElse(-1));
    }


    @Test
    public void s() {
        Observable<Integer> just = Observable.just(1);
        Observable<Integer> just2 = Observable.just(1);

        Observable
                .zip(just, just2, (Func2<Integer, Integer, Tuple2>) Tuple2::new)
                .subscribe(System.err::println);

    }
}


class Student {
    List<Integer> grades;
    String name;

    public Student(List<Integer> grades, String name) {
        this.grades = grades;
        this.name = name;
    }
}
