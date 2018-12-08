package org.revo.Repository;

import org.revo.Domain.Index;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IndexRepository extends CrudRepository<Index, String> {
    List<Index> findByMaster(String master);
}
