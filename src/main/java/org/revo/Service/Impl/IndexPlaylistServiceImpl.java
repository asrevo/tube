package org.revo.Service.Impl;

/*
import com.comcast.viper.hlsparserj.PlaylistFactory;
import com.comcast.viper.hlsparserj.PlaylistVersion;
import com.comcast.viper.hlsparserj.tags.UnparsedTag;
import org.revo.Config.Env;
import org.revo.Domain.Bucket;
import org.revo.Domain.IndexPlaylist;
import org.revo.Repository.IndexPlaylistRepository;
import org.revo.Service.IndexPlaylistService;
import org.revo.Service.SignedUrlService;
import org.revo.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.nio.file.Paths.get;

@Service
public class IndexPlaylistServiceImpl implements IndexPlaylistService {
    @Autowired
    private IndexPlaylistRepository indexPlaylistRepository;
    @Autowired
    private SignedUrlService signedUrlService;
    @Autowired
    private Env env;

    @Override
    public void save(IndexPlaylist indexPlaylist) {
        indexPlaylistRepository.save(indexPlaylist);
    }

    @Override
    public IndexPlaylist findOne(String id) {
        return indexPlaylistRepository.findById(id).get();
    }

    @Override
    public String findOneParsed(String master, String resolution, String index) {
        List<UnparsedTag> tags = PlaylistFactory.parsePlaylist(PlaylistVersion.TWELVE, findOne(index).getStream()).getTags();
        return Util.TOString(tags, s -> signedUrlService.generate(env.getBuckets().get("ts"), get("hls", master, resolution, index, s).toString()));
    }
}
*/
