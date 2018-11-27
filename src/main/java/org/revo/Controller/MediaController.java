package org.revo.Controller;

/*
import org.revo.Config.Processor;
import org.revo.Domain.*;
import org.revo.Service.IndexPlaylistService;
import org.revo.Service.MediaService;
import org.revo.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
*/


/**
 * Created by ashraf on 18/04/17.
 */
//@RestController
//@RequestMapping("api")
public class MediaController {
/*
    private final String masterURL = "{master}.m3u8/";
    private final String indexUrl = masterURL + "{resolution}/{index}.m3u8/";
    private final String keyUrl = indexUrl + "{master}.key/";
    @Autowired
    private MediaService mediaService;
    @Autowired
    private IndexPlaylistService indexPlaylistService;
    @Autowired
    private Processor processor;

    @GetMapping
    public Iterable<Media> findAll() {
        return mediaService.findAll(Status.SUCCESS);
    }

    @PostMapping("save")
    private void save(@RequestBody Media media) {
        Media save = mediaService.save(media);
        processor.ToFile().send(MessageBuilder.withPayload(save).build());
        processor.ToFeedBack_push().send(MessageBuilder.withPayload(new Stater(save, Queue.FILE, State.QUEUED)).build());
    }

    @GetMapping("{size}/{id}")
    public Iterable<Media> findAllPagining(@PathVariable int size, @PathVariable String id) {
        if (id.equals("0")) id = null;
        return mediaService.findAll(Status.SUCCESS, size, id, new Ids(new ArrayList<>()));
    }

    @PostMapping("{size}/{id}")
    public Iterable<Media> findAllPaginingWithnUsers(@PathVariable int size, @PathVariable String id, @RequestBody Ids ids) {
        if (id.equals("0")) id = null;
        return mediaService.findAll(Status.SUCCESS, size, id, ids);
    }

    @PostMapping
    public Iterable<Media> findAllByIds(@RequestBody Ids ids) {
        return mediaService.findAll(Status.SUCCESS, ids.getIds());
    }

    @GetMapping("user/{id}")
    public List<Media> findAllByUser(@PathVariable("id") String id) {
        return mediaService.findByUser(id, Status.SUCCESS);
    }

    @GetMapping("{id}")
    public Media findOne(@PathVariable("id") String id) {
        return mediaService.findOne(id);
    }

    @GetMapping(masterURL)
    public void findOneMaster(@PathVariable("master") String master, HttpServletResponse response) throws IOException {
        response.getWriter().print(mediaService.findOne(master).getMasterPlaylist());
        response.setContentType("application/x-mpegURL");
        response.setHeader("Content-disposition", "attachment; filename=" + master + ".m3u8");
    }

    @GetMapping(indexUrl)
    public void findOneIndex(@PathVariable("master") String master, @PathVariable("resolution") String resolution, @PathVariable("index") String index, HttpServletResponse response) throws IOException {
        response.getWriter().print(indexPlaylistService.findOneParsed(master, resolution, index));
        response.setContentType("application/x-mpegURL");
        response.setHeader("Content-disposition", "attachment; filename=" + master + ".m3u8");
    }

    @GetMapping(keyUrl)
    public void findOneKey(@PathVariable("master") String master, HttpServletResponse response) throws IOException {
        response.getOutputStream().write(Util.secret(mediaService.findOne(master).getSecret()));
        response.setContentType("application/pgp-keys");
        response.setHeader("Content-disposition", "attachment; filename=" + master + ".key");
    }
*/
}
