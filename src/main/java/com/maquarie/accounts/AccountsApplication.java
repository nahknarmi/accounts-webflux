package com.maquarie.accounts;

import com.maquarie.accounts.handlers.FooHandler;
import com.maquarie.accounts.handlers.TodoHandler;
import io.micrometer.prometheus.PrometheusConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.metrics.export.prometheus.PrometheusFunctions;
import org.springframework.metrics.instrument.MeterRegistry;
import org.springframework.metrics.instrument.binder.JvmGcMetrics;
import org.springframework.metrics.instrument.binder.JvmMemoryMetrics;
import org.springframework.metrics.instrument.prometheus.PrometheusMeterRegistry;
import org.springframework.metrics.instrument.scheduling.ExecutorServiceMetrics;
import org.springframework.metrics.instrument.web.RouterFunctionMetrics;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
//@EnableHystrix
//@EnableHystrixDashboard
//@EnablePrometheusMetrics
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(TodoHandler todoHandler, FooHandler fooHandler) {
        PrometheusMeterRegistry registry = new PrometheusMeterRegistry();
        RouterFunctionMetrics metrics = new RouterFunctionMetrics(registry);

//        new JvmMemoryMetrics().bindTo(registry);
//        new JvmGcMetrics().bindTo(registry);

        return route(GET("/todos"), todoHandler::todos)
                .andRoute(GET("/foo"), fooHandler::items).filter(metrics.timer("server.requests"))
                .andRoute(GET( "/prometheus"), PrometheusFunctions.scrape(registry))
                ;
    }
}
