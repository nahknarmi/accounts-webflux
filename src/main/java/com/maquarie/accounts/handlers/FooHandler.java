package com.maquarie.accounts.handlers;

import com.google.common.collect.ImmutableMap;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.retry.Retry;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Component
public class FooHandler {


    @NotNull
    public Mono<ServerResponse> items(ServerRequest serverRequest) {
        CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("backendName");
        Retry retry = Retry.ofDefaults("id");



        Mono<Map> transform = WebClient.create("https://jsonplaceholder.typicode.com/todos/1")
                .get()
                .retrieve()
                .bodyToMono(Map.class)
                .log()
                .onErrorReturn(ImmutableMap.of())
                .transform(CircuitBreakerOperator.of(circuitBreaker));


        Bulkhead bulkhead = Bulkhead.of("test", BulkheadConfig.ofDefaults());

//        transform.transform(BulkHeadOpe)



        return ServerResponse.ok().body(transform, Map.class);
    }
}
