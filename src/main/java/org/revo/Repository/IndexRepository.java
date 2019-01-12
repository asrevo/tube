package org.revo.Repository;

import org.revo.Domain.Index;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.List;

public interface IndexRepository extends ReactiveCrudRepository<Index, String> {
    List<Index> findByMaster(String master);
}
