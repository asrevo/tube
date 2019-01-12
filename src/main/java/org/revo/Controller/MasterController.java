package org.revo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.revo.Domain.Ids;
import org.revo.Domain.Master;
import org.revo.Domain.Status;
import org.revo.Service.IndexService;
import org.revo.Service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
@Slf4j
public class MasterController {
    @Autowired
    private MasterService masterService;
    @Autowired
    private IndexService indexService;
    private final String masterURL = "{master}.m3u8";
    private final String indexUrl = masterURL + "/{index}.m3u8";
    private final String keyUrl = masterURL + "/{master_id}.key";

    @GetMapping("who")
    public Authentication ss(@AuthenticationPrincipal Jwt jwt) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @GetMapping("{size}/{id}")
    public Iterable<Master> findAllPagining(@PathVariable int size, @PathVariable String id) {
        if (id.equals("0")) id = null;
        return masterService.findAll(Status.SUCCESS, size, id, new Ids(), new Ids());
    }

    @PostMapping("{size}/{id}")
    public Iterable<Master> findAllPaginingWithnUsers(@PathVariable int size, @PathVariable String id, @RequestBody Ids ids) {
        if (id.equals("0")) id = null;
        return masterService.findAll(Status.SUCCESS, size, id, ids, new Ids());
    }

    @PostMapping
    public Iterable<Master> findAllByIds(@RequestBody Ids ids) {
        return masterService.findAll(Status.SUCCESS, 1000, null, new Ids(), ids);
    }

    @GetMapping("user/{id}")
    public List<Master> findAllByUser(@PathVariable("id") String id) {
        Ids ids = new Ids();
        ids.setIds(Arrays.asList(id));
        return masterService.findAll(Status.SUCCESS, 1000, null, ids, new Ids());
    }

    @GetMapping("one/{id}")
    public Optional<Master> findOne(@PathVariable("id") String id) {
        return masterService.findOne(id);
    }

    @GetMapping(masterURL)
    public ResponseEntity<InputStreamResource> findOneMaster(@PathVariable("master") String master) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/x-mpegURL")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".m3u8")
                .body(new InputStreamResource(IOUtils.toInputStream(masterService.getStream(master))));
    }

    @GetMapping(indexUrl)
    public ResponseEntity<InputStreamResource> findOneIndex(@PathVariable("master") String master, @PathVariable("index") String index) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/x-mpegURL")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".m3u8")
                .body(new InputStreamResource(IOUtils.toInputStream(indexService.findOneParsed(master, index))));
    }

    @GetMapping(keyUrl)
    public ResponseEntity<InputStreamResource> findOneKey(@PathVariable("master") String master) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/pgp-keys")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".key")
                .body(new InputStreamResource(IOUtils.toInputStream(masterService.findOne(master).map(Master::getSecret).orElse(""))));
    }
}
