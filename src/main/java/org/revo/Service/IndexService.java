package org.revo.Service;

import org.revo.Domain.Index;

import java.util.Optional;

public interface IndexService {
    Index save(Index index);

    String findOneParsed(String master, String index);

    Optional<Index> findOne(String id);
}
