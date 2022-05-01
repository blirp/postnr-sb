package org.larma.postnr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostnrResource {
    @Autowired
    private PostnrService service;

    @RequestMapping(
        value = "/postnr",
        method = RequestMethod.GET,
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public List<PostnrInfo> search(
            @RequestParam(value = "pn", required = false) final String postnr,
            @RequestParam(value = "ps", required = false) final String poststed,
            @RequestParam(value = "kn", required = false) final String kommunenr,
            @RequestParam(value = "k", required = false) final String kommune)
    {
        final PostnrInfo q = new PostnrInfo(postnr, poststed, kommunenr, kommune);
        return service.search(q);
    }

    @RequestMapping(
        value = "/postnr",
        method = RequestMethod.POST,
        consumes = { MediaType.APPLICATION_JSON_VALUE },
        produces = { MediaType.APPLICATION_JSON_VALUE }
    )
    public List<PostnrInfo> postSearch(
            @RequestBody PostnrInfo q)
    {
        return service.search(q);
    }
}

