package com.maquarie.accounts.handlers;

import com.maquarie.accounts.commands.RetrieveCommand;
import com.maquarie.accounts.commands.Todo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Component
public class TodoHandler {

    @NotNull
    public Mono<ServerResponse> todos(ServerRequest serverRequest) {
        HystrixCommand.Setter config = HystrixCommand
                .Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("TodoService"));

        config.andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                        .withExecutionTimeoutInMilliseconds(700)
                        .withCircuitBreakerSleepWindowInMilliseconds(2000)
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                        .withCircuitBreakerEnabled(true)
                        .withCircuitBreakerErrorThresholdPercentage(5)
//                        .withCircuitBreakerForceClosed(true)
                        .withCircuitBreakerRequestVolumeThreshold(10)
        );

//        config.andThreadPoolPropertiesDefaults(
//                HystrixThreadPoolProperties.Setter()
//                        .withMaxQueueSize(5)
//                        .withCoreSize(3)
//                        .withQueueSizeRejectionThreshold(1));

        String baseUrl = String.format("https://jsonplaceholder.typicode.com/todos/1");


        return new RetrieveCommand(config, baseUrl)
                .execute()
                .flatMap(e ->
                        e.map(x -> ServerResponse.ok().body(Mono.just(x), Todo.class)).orElse(ServerResponse.notFound().build()));
    }

}
