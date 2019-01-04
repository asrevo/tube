package org.revo.Controller;

import org.revo.Domain.Ids;
import org.revo.Domain.Master;
import org.revo.Domain.Status;
import org.revo.Service.IndexService;
import org.revo.Service.MasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

//import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
public class MasterController {
    @Autowired
    private MasterService masterService;
    @Autowired
    private IndexService indexService;
    private final String masterURL = "{master}.m3u8";
    private final String indexUrl = masterURL + "/{index}.m3u8";
    private final String keyUrl = masterURL + "/{master}.key";

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
    public ResponseEntity<String> findOneMaster(@PathVariable("master") String master, ServerHttpResponse response) throws IOException {
        return ResponseEntity.ok()
                .header("Content-Type", "application/x-mpegURL")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".m3u8")
                .body(masterService.getStream(master));
    }

    @GetMapping(indexUrl)
    public ResponseEntity<String> findOneIndex(@PathVariable("master") String master, @PathVariable("index") String index) throws IOException {
        return ResponseEntity.ok()
                .header("Content-Type", "application/x-mpegURL")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".m3u8")
                .body(indexService.findOneParsed(master, index));
    }

    @GetMapping(keyUrl)
    public ResponseEntity<String> findOneKey(@PathVariable("master") String master) throws IOException {
        return ResponseEntity.ok()
                .header("Content-Type", "application/x-mpegURL")
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + master + ".key")
                .body(masterService.findOne(master).map(Master::getSecret).orElse(""));
    }
}
