package org.revo.Service;

import org.revo.Domain.Ids;
import org.revo.Domain.Index;
import org.revo.Domain.Master;
import org.revo.Domain.Status;
import reactor.core.publisher.Mono;

import java.util.List;

public interface MasterService {
    Mono<Master> saveInfo(Master master);

    Mono<Master> append(Mono<Index> index);

    Mono<Master> save(Master master);

    Mono<Master> findOne(String master);

    String getStream(String master);

    List<Master> findAll(Status status, int size, String id, Ids userIds, Ids masterIds);
}
