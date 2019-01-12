package org.revo.Repository;

import org.revo.Domain.File;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FileRepository extends ReactiveCrudRepository<File, String> {
}
