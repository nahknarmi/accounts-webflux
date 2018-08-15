package com.maquarie.accounts;

import com.maquarie.accounts.commands.RetrieveCommand;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.IntStream;

public class FooTest {

    @Test
    public void name() {
        WebClient webClient =
                WebClient.create("https://jsonplaceholder.typicode.com/todos/1");

        Mono<Map> mapMono = webClient.get().retrieve().bodyToMono(Map.class);

        Map block = mapMono.block();

        System.err.println(block);
    }

    @Test
    public void hystrix() throws InterruptedException {
        HystrixCommand.Setter config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("TodoService"));

        config.andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(700)
                .withCircuitBreakerSleepWindowInMilliseconds(2000)
                .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                .withCircuitBreakerEnabled(true)
                .withCircuitBreakerErrorThresholdPercentage(50)
//                        .withCircuitBreakerForceClosed(true)
                .withCircuitBreakerRequestVolumeThreshold(10)
        );

//        config.andThreadPoolPropertiesDefaults(
//                HystrixThreadPoolProperties.Setter()
//                        .withMaxQueueSize(5)
//                        .withCoreSize(3)
//                        .withQueueSizeRejectionThreshold(1));




        IntStream.range(1, 50).forEach(value -> {
            try {
                String baseUrl = "https://jsonplaceholder.typicode.com/todos/1";
                RetrieveCommand retrieveCommand = new RetrieveCommand(config, baseUrl);
                System.err.println("Response: " + retrieveCommand.execute().block());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(100000);
    }
}
