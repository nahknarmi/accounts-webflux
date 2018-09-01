package com.maquarie.accounts;

import org.junit.Test;

import java.util.concurrent.*;

public class BarTest {
    ExecutorService executorService = Executors.newCachedThreadPool();

    @Test
    public void futureTest() throws ExecutionException, InterruptedException {
        final Callable<String> callable = () -> "foof";
        Future<String> future = executorService.submit(callable);

        System.err.println(future.get());
    }

    @Test
    public void name() {

    }
}
