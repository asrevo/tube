package org.revo.Service.Impl;

import org.revo.Config.Env;
import org.revo.Domain.Index;
import org.revo.Repository.IndexRepository;
import org.revo.Service.IndexService;
import org.revo.Service.SignedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.nio.file.Paths.get;
import static org.revo.Util.Util.TOString;

@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexRepository indexRepository;
    @Autowired
    private SignedUrlService signedUrlService;
    @Autowired
    private Env env;

    @Override
    public Mono<Index> save(Index index) {
        return indexRepository.save(index);
    }

    @Override
    public Mono<Index> findOne(String id) {
        return indexRepository.findById(id);
    }

    @Override
    public List<Index> findByMaster(String master) {
        return indexRepository.findByMaster(master);
    }

    @Override
    public Mono<String> findOneParsed(String master, String index) {
        return findOne(index).map(it -> TOString(it.getTags(), s -> signedUrlService.generate(env.getBuckets().get("ts"), get("hls", master, index, s).toString())));
    }

}
