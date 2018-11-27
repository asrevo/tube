package org.revo.Service.Impl;

/*
import org.bson.types.ObjectId;
import org.revo.Domain.*;
import org.revo.Repository.MediaRepository;
import org.revo.Service.IndexPlaylistService;
import org.revo.Service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;
import static org.revo.Util.Util.generateKey;
import static org.springframework.data.mongodb.core.query.Criteria.where;

*/
/**
 * Created by ashraf on 15/04/17.
 *//*

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaRepository mediaRepository;
    @Autowired
    private IndexPlaylistService indexPlaylistService;


    @Override
    public Media save(Media media) {
        media.setSecret(generateKey());
        return mediaRepository.save(media);
    }

    @Override
    public Media save(MediaPlaylist mediaPlaylist) {
        Media one = mediaRepository.findById(mediaPlaylist.getMasterId()).get();
        if (Objects.equals(mediaPlaylist.getMasterId(), mediaPlaylist.getId())) {
            one.setTime(mediaPlaylist.getTime());
            one.setStatus(mediaPlaylist.getStatus());
        }
        one.setMasterPlaylist(one.getMasterPlaylist() + mediaPlaylist.getMasterTag().getRawTag() + "\n" + mediaPlaylist.getMasterTag().getURI() + "\n");
        one.setPublishDate(new Date());
        Media saved = save(one);
        IndexPlaylist indexPlaylist = new IndexPlaylist();
        indexPlaylist.setId(mediaPlaylist.getId());
        indexPlaylist.setMasterId(mediaPlaylist.getMasterId());
        indexPlaylist.setStream(mediaPlaylist.getStream());
        indexPlaylistService.save(indexPlaylist);
        return saved;
    }

    @Override
    public Media findOne(String id) {
        return mediaRepository.findById(id).get();
    }

    @Override
    public List<Media> findByUser(String id, Status status) {
        return hideData(mediaRepository.findByUserIdAndStatus(id, status));
    }

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public List<Media> findAll(Status status) {
        return hideData(mediaRepository.findByStatus(status));
    }

    @Override
    public List<Media> findAll(Status status, List<String> ids) {
        return hideData(mediaRepository.findByStatusAndIdIn(status, ids));
    }

    @Override
    public List<Media> findAll(Status status, int size, String id, Ids ids) {
        Query query = new Query();
        if (id != null && !id.trim().isEmpty())
            query.addCriteria(where("id").lt(new ObjectId(id)));
        query.addCriteria(where("status").is(status));
        if (ids.getIds().size() > 0) query.addCriteria(where("userId").in(ids.getIds()));
        query.with(new Sort(Sort.Direction.DESC, "id"));
        query.limit(size);
        return hideData(mongoOperations.find(query, Media.class));
    }


    private List<Media> hideData(List<Media> media) {
        return media.stream().map(it -> {
            it.setSecret(null);
            it.setMasterPlaylist(null);
            return it;
        }).collect(toList());
    }
}
*/
