package org.revo.Controller;

import org.revo.Domain.Ids;
import org.revo.Domain.Master;
import org.revo.Domain.Status;
import org.revo.Service.IndexService;
import org.revo.Service.MasterService;
import org.revo.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
public class MasterController {
    @Autowired
    private MasterService masterService;
    @Autowired
    private IndexService indexService;
    private final String masterURL = "{master}.m3u8/";
    private final String indexUrl = masterURL + "{index}.m3u8/";
    private final String keyUrl = indexUrl + "{master}.key/";

    @GetMapping("{size}/{id}")
    public Iterable<Master> findAllPagining(@PathVariable int size, @PathVariable String id) {
        if (id.equals("0")) id = null;
        return masterService.findAll(Status.SUCCESS, size, id, new Ids());
    }

    @PostMapping("{size}/{id}")
    public Iterable<Master> findAllPaginingWithnUsers(@PathVariable int size, @PathVariable String id, @RequestBody Ids ids) {
        if (id.equals("0")) id = null;
        return masterService.findAll(Status.SUCCESS, size, id, ids);
    }

    @PostMapping
    public Iterable<Master> findAllByIds(@RequestBody Ids ids) {
        return masterService.findAll(Status.SUCCESS, 1000, null, ids);
    }

    @GetMapping("user/{id}")
    public List<Master> findAllByUser(@PathVariable("id") String id) {
        Ids ids = new Ids();
        ids.setIds(Arrays.asList(id));
        return masterService.findAll(Status.SUCCESS, 1000, null, ids);
    }

    @GetMapping("one/{id}")
    public Optional<Master> findOne(@PathVariable("id") String id) {
        return masterService.findOne(id);
    }

    @GetMapping(masterURL)
    public void findOneMaster(@PathVariable("master") String master, HttpServletResponse response) throws IOException {
        response.getWriter().print(masterService.findOne(master).get().getStream());
        response.setContentType("application/x-mpegURL");
        response.setHeader("Content-disposition", "attachment; filename=" + master + ".m3u8");
    }

    @GetMapping(indexUrl)
    public void findOneIndex(@PathVariable("master") String master, @PathVariable("index") String index, HttpServletResponse response) throws IOException {
        response.getWriter().print(indexService.findOneParsed(master, index));
        response.setContentType("application/x-mpegURL");
        response.setHeader("Content-disposition", "attachment; filename=" + master + ".m3u8");
    }

    @GetMapping(keyUrl)
    public void findOneKey(@PathVariable("master") String master, HttpServletResponse response) throws IOException {
        response.getOutputStream().write(Util.secret(masterService.findOne(master).map(Master::getSecret).orElse("")));
        response.setContentType("application/pgp-keys");
        response.setHeader("Content-disposition", "attachment; filename=" + master + ".key");
    }
}
