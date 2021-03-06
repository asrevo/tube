package org.revo.Service;

import org.revo.Domain.Index;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IndexService {
    Mono<Index> save(Index index);

    Mono<String> findOneParsed(String master, String index);

    Mono<Index> findOne(String id);

    Flux<Index> findByMaster(String master);
}
