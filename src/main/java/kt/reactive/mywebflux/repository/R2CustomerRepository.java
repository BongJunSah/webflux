package kt.reactive.mywebflux.repository;

import kt.reactive.mywebflux.entity.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface R2CustomerRepository extends ReactiveCrudRepository<Customer,Long> {
    @Query("SELECT * FROM CUSTOMER WHERE last_name = :lastName")
    Flux<Customer> findByLastName(String lastName);
}