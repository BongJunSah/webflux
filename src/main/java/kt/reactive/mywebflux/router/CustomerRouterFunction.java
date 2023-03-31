package kt.reactive.mywebflux.router;

import kt.reactive.mywebflux.handler.CustomerHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class CustomerRouterFunction {
    // Servlet 에서의 Controller
    @Bean
    public RouterFunction<ServerResponse> routerFunction(CustomerHandler customerHandler) {
        return RouterFunctions
                .route(GET("/router/r2customers"), customerHandler::getCustomers)
                .andRoute(GET("/router/r2customers/{id}"), customerHandler::getCustomer)
                .andRoute(POST("/router/r2customers"), customerHandler::saveCustomer)
                .andRoute(PUT("/router/r2customers/{id}"), customerHandler::updateCustomer);
    }

}