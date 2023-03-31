package kt.reactive.mywebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MywebfluxAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MywebfluxAppApplication.class, args);
    }

    @Bean
    WebProperties.Resources resources() {
        return new WebProperties.Resources();
    }
}
