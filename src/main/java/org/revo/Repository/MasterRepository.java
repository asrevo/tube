package org.revo.Repository;

import org.revo.Domain.Master;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface MasterRepository extends ReactiveCrudRepository<Master, String> {
}
