package com.maquarie.accounts;

import com.maquarie.accounts.handlers.TodoHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
//@EnableHystrix
//@EnableHystrixDashboard
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> monoRouterFunction(TodoHandler todoHandler) {
        return route(GET("/todos"), todoHandler::todos);
    }
}
