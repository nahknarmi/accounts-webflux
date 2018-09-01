package com.maquarie.accounts.commands;

import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;
import reactor.retry.RetryContext;

import java.time.Duration;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

public class RetrieveCommand extends HystrixCommand<Mono<Optional<Todo>>> {
    private static final Logger logger = LoggerFactory.getLogger(RetrieveCommand.class);
    private static final Duration firstBackoff = Duration.ofMillis(200);
    private static final Duration maxBackoff = Duration.ofMillis(1000);
    private final String requestUrl;

    public RetrieveCommand(Setter config, String requestUrl) {
        super(config);
        this.requestUrl = requestUrl;
        logger.info(isCircuitBreakerOpen() ? "Fallback will be called" : "Call to client");
    }

    @Override
    protected Mono<Optional<Todo>> run() {
        return WebClient.create(requestUrl)
                .get()
                .retrieve()
                .onStatus(onStatusPredicate(), response -> Mono.just(new FailedCallException("404 error!")))
                .bodyToMono(Todo.class)
                .retryWhen(whenFactory())
                .log()
                .map(Optional::ofNullable);
    }

    private Retry<Object> whenFactory() {
        return Retry
                .onlyIf(retryOnlyWhenPredicate())
                .exponentialBackoff(firstBackoff, maxBackoff)
                .retryMax(4)
                .doOnRetry(x -> logger.info(x + " (" + Thread.currentThread().getName() + ")"));
    }

    private Predicate<HttpStatus> onStatusPredicate() {
        return httpStatus -> httpStatus.is4xxClientError();
    }

    private Predicate<RetryContext<Object>> retryOnlyWhenPredicate() {
        return retryContext ->
                ofNullable(retryContext.exception())
                        .orElse(new Exception())
                        .getClass().equals(FailedCallException.class);
    }

    @Override
    protected Mono<Optional<Todo>> getFallback() {
        logger.info("Failing back in hystrix");
        return Mono.empty();
    }
}
