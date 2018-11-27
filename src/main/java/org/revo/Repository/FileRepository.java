package org.revo.Repository;

import org.revo.Domain.File;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<File, String> {
}
