package org.revo.Service;

import org.revo.Domain.Ids;
import org.revo.Domain.Index;
import org.revo.Domain.Master;
import org.revo.Domain.Status;

import java.util.List;
import java.util.Optional;

public interface MasterService {
    Master saveInfo(Master master);

    void append(Index index);

    Master save(Master master);

    Optional<Master> findOne(String master);

    String getStream(String master);

    List<Master> findAll(Status status, int size, String id, Ids userIds, Ids masterIds);
}
