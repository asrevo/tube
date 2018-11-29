package org.revo.Service.Impl;

/*
import org.bson.types.ObjectId;
import org.revo.Domain.Media;
import org.revo.Domain.MediaGroup;
import org.revo.Domain.Status;
import org.revo.Repository.MediaGroupRepository;
import org.revo.Service.MediaGroupService;
import org.revo.Service.MediaService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaGroupServiceImpl implements MediaGroupService {
    @Autowired
    private MediaGroupRepository mediaGroupRepository;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private MediaService mediaService;
    @Autowired
    private UserService userService;

    @Override
    public Optional<MediaGroup> findOne(String id) {
        return mediaGroupRepository.findById(id);
    }

    @Override
    public void delete(String id) {
        mediaGroupRepository.deleteByIdAndAndUserId(id, userService.current().get());
    }

    @Override
    public MediaGroup save(MediaGroup mediaGroup) {
        return mediaGroupRepository.save(mediaGroup);
    }

    @Override
    public void addMediaToMediaGroup(MediaGroup mediaGroup, String media) {
        Query query = new Query(Criteria.where("id").is(new ObjectId(mediaGroup.getId()))
                .and("userId").is(mediaGroup.getUserId()));
        Update saveInfo = new Update().addToSet("media", media);
        mongoOperations.updateFirst(query, saveInfo, MediaGroup.class);
    }

    @Override
    public List<Media> GetAllMedia(MediaGroup mediaGroup) {
        return mediaService.findAll(Status.SUCCESS, findOne(mediaGroup.getId()).get().getMedia());
    }

    @Override
    public Media store(Media media) {
        if (media.getGroup() != null) {
            MediaGroup group = findOne(media.getGroup())
                    .orElseGet(() -> {
                        MediaGroup mediaGroup = new MediaGroup();
                        mediaGroup.setId(media.getGroup());
                        mediaGroup.setUserId(media.getUserId());
                        mediaGroup.setMeta(media.getMeta());
                        mediaGroup.setTitle(media.getMeta());
                        return save(mediaGroup);
                    });
            addMediaToMediaGroup(group, media.getId());
        }
        return mediaService.save(media);
    }
}
*/
