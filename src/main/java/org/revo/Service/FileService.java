package org.revo.Service;

import org.revo.Domain.File;
import reactor.core.publisher.Mono;

public interface FileService {
    Mono<File> save(File file);
}
