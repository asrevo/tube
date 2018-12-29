package org.revo.Service.Impl;

import com.comcast.viper.hlsparserj.tags.UnparsedTag;
import org.bson.types.ObjectId;
import org.revo.Domain.*;
import org.revo.Repository.MasterRepository;
import org.revo.Service.IndexService;
import org.revo.Service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.TypedAggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.revo.Util.Util.generateKey;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class MasterServiceImpl implements MasterService {
    @Autowired
    private MasterRepository masterRepository;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private IndexService indexService;

    @Override
    public Master saveInfo(Master master) {
        return masterRepository.findById(master.getId()).map(it -> {
            it.setTime(master.getTime());
            it.setResolution(master.getResolution());
            it.setImage(master.getImage());
            it.setImpls(master.getImpls());
            it.setSplits(master.getSplits());
            return it;
        }).map(it -> masterRepository.save(it)).orElse(null);
    }

    @Override
    public void append(Index index) {
        findOne(index.getMaster())
                .map(it -> {
                    List<IndexImpl> indexList = it.getImpls().stream().filter(i -> i.getIndex() != null && !i.getIndex().equals(index.getId())).collect(toList());
                    indexList.add(new IndexImpl(index.getId(), index.getResolution(), Status.SUCCESS, index.getExecution()));
                    it.setImpls(indexList);
                    return masterRepository.save(it);
                });
    }

    private static String getParsedTag(Index index) {
        UnparsedTag unparsedTag = new UnparsedTag();
        unparsedTag.setTagName("EXT-X-STREAM-INF");
        HashMap<String, String> attributes = new HashMap<>();
        if (index.getAverage_bandwidth() != null && !index.getAverage_bandwidth().trim().isEmpty())
            attributes.put("AVERAGE-BANDWIDTH", index.getAverage_bandwidth());
        if (index.getBandwidth() != null && !index.getBandwidth().trim().isEmpty())
            attributes.put("BANDWIDTH", index.getBandwidth());
        if (index.getCodecs() != null && !index.getCodecs().trim().isEmpty())
            attributes.put("CODECS", "\"" + index.getCodecs() + "\"");
        if (index.getResolution() != null && !index.getResolution().trim().isEmpty())
            attributes.put("RESOLUTION", index.getResolution().toLowerCase());
        unparsedTag.setURI(Paths.get(index.getMaster() + ".m3u8/", index.getId() + ".m3u8").toString());
        return "#" + unparsedTag.getTagName() + ":" + attributes.entrySet().stream().map(it -> it.getKey() + "=" + it.getValue()).collect(Collectors.joining(",")) + "\n" + unparsedTag.getURI() + "\n";
    }

    @Override
    public Master save(Master master) {
        master.setSecret(generateKey());
        return masterRepository.save(master);
    }

    @Override
    public Optional<Master> findOne(String master) {
        return masterRepository.findById(master);
    }

    @Override
    public String getStream(String master) {
        return "#EXTM3U\n#EXT-X-VERSION:4\n# Media Playlists\n" + indexService.findByMaster(master).stream().map(MasterServiceImpl::getParsedTag).collect(Collectors.joining());
    }

    @Override
    public List<Master> findAll(Status status, int size, String id, Ids userIds, Ids masterIds) {
        Criteria lt = where("impls.status").is(status.toString());
        if (masterIds.getIds().size() == 0)
            lt.and("id").lt(Optional.ofNullable(id).map(ObjectId::new).orElse(new ObjectId()));
        if (userIds.getIds().size() > 0) lt.and("userId").in(userIds.getIds());
        if (masterIds.getIds().size() > 0) lt.and("id").in(masterIds.getIds());
        TypedAggregation<Master> agg = newAggregation(Master.class,
                unwind("impls"),
                match(lt),
                group("id").push("impls").as("impls")
                        .first("id").as("id")
                        .first("title").as("title")
                        .first("meta").as("meta")
                        .first("userId").as("userId")
                        .first("createdDate").as("createdDate")
                        .first("image").as("image")
//                        .first("stream").as("stream")
//                        .first("secret").as("secret")
                        .first("file").as("file")
                        .first("time").as("time")
                        .first("resolution").as("resolution")
                , sort(DESC, "id")
                , limit(size));

        return mongoOperations.aggregate(agg, Master.class).getMappedResults();
    }
}
