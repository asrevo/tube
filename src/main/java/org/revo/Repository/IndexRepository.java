package org.revo.Repository;

import org.revo.Domain.Index;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IndexRepository extends ReactiveCrudRepository<Index, String> {
    Flux<Index> findByMaster(String master);
}
