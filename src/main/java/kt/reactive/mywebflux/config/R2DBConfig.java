package kt.reactive.mywebflux.config;

import io.r2dbc.spi.ConnectionFactory;
import kt.reactive.mywebflux.entity.Customer;
import kt.reactive.mywebflux.repository.R2CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;

@Configuration
@EnableR2dbcAuditing
@Slf4j
public class R2DBConfig {
    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        return initializer;
    }

    @Bean
    public CommandLineRunner customer_insert_find(R2CustomerRepository repository) {
        return (args) -> {
            //delete all customers
            //Sub 안하면 Publisher에만 데이터가 있기 때문에 실제로 안지워진다. subscribe 필수
//            Mono<Void> deleteAll = repository.deleteAll();
//            deleteAll.doOnSuccess(result -> System.out.println(" deleteAll ok "))
//                            .subscribe();

            // save a few customers
            repository.saveAll(Arrays.asList(
                            new Customer("Jack", "Bauer"),
                            new Customer("Chloe", "O'Brian"),
                            new Customer("Kim", "Bauer"),
                            new Customer("David", "Palmer"),
                            new Customer("Michelle", "Dessler")))
                    //마지막 요소를 획득 할 때까지 block : Flux#blockLas 비동기로 이루어지기 때문에 뒤에거가 먼제 실행 되는 것을 방지
                    .blockLast(Duration.ofSeconds(10));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            repository.findAll().doOnNext(customer -> {
                log.info(customer.toString());
            }).blockLast(Duration.ofSeconds(10));

            log.info("");

            // fetch an individual customer by ID
            repository.findById(1L).doOnNext(customer -> {
                        log.info("Customer found with findById(1L):");
                        log.info("--------------------------------");
                        log.info(customer.toString());
                        log.info("");
                    })
                    .defaultIfEmpty(new Customer("test","kim"))
                    .block(Duration.ofSeconds(10));


            // fetch customers by last name
            log.info("Customer found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByLastName("Bauer").doOnNext(bauer -> {
                log.info(bauer.toString());
            }).blockLast(Duration.ofSeconds(10));;
            log.info("");
        };
    }
}