package org.revo.Service.Impl;

import org.revo.Domain.File;
import org.revo.Repository.FileRepository;
import org.revo.Service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository fileRepository;

    @Override
    public Mono<File> save(File file) {
        return fileRepository.save(file);
    }
}
