package org.revo.Controller;

/*
import org.revo.Domain.Media;
import org.revo.Domain.MediaGroup;
import org.revo.Service.MediaGroupService;
import org.revo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
*/

/*
@RestController
@RequestMapping("api/group")
*/
public class MediaGroupController {
/*
    @Autowired
    private MediaGroupService mediaGroupService;
    @Autowired
    private UserService userService;

    @PostMapping("save")
    private MediaGroup groupSave(@RequestBody MediaGroup mediaGroup) {
        return mediaGroupService.save(mediaGroup);
    }

    @GetMapping("add/{group}/{media}")
    private void groupSave(@PathVariable("group") String group, @PathVariable("media") String media) {
        MediaGroup mediaGroup = new MediaGroup();
        mediaGroup.setId(group);
        mediaGroup.setUserId(userService.current().get());
        mediaGroupService.addMediaToMediaGroup(mediaGroup, media);
    }

    @GetMapping("{group}/media")
    private List<Media> findAll(@PathVariable("group") String group) {
        MediaGroup mediaGroup = new MediaGroup();
        mediaGroup.setId(group);
        return mediaGroupService.GetAllMedia(mediaGroup);
    }

    @GetMapping("{group}")
    private Optional<MediaGroup> findOne(@PathVariable("group") String group) {
        return mediaGroupService.findOne(group);
    }

*/
}
