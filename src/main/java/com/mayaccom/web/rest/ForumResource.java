package com.mayaccom.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mayaccom.service.ForumService;
import com.mayaccom.web.rest.errors.BadRequestAlertException;
import com.mayaccom.web.rest.util.HeaderUtil;
import com.mayaccom.service.dto.ForumDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Forum.
 */
@RestController
@RequestMapping("/api")
public class ForumResource {

    private final Logger log = LoggerFactory.getLogger(ForumResource.class);

    private static final String ENTITY_NAME = "forum";

    private final ForumService forumService;

    public ForumResource(ForumService forumService) {
        this.forumService = forumService;
    }

    /**
     * POST  /forums : Create a new forum.
     *
     * @param forumDTO the forumDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new forumDTO, or with status 400 (Bad Request) if the forum has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forums")
    @Timed
    public ResponseEntity<ForumDTO> createForum(@RequestBody ForumDTO forumDTO) throws URISyntaxException {
        log.debug("REST request to save Forum : {}", forumDTO);
        if (forumDTO.getId() != null) {
            throw new BadRequestAlertException("A new forum cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ForumDTO result = forumService.save(forumDTO);
        return ResponseEntity.created(new URI("/api/forums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forums : Updates an existing forum.
     *
     * @param forumDTO the forumDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated forumDTO,
     * or with status 400 (Bad Request) if the forumDTO is not valid,
     * or with status 500 (Internal Server Error) if the forumDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forums")
    @Timed
    public ResponseEntity<ForumDTO> updateForum(@RequestBody ForumDTO forumDTO) throws URISyntaxException {
        log.debug("REST request to update Forum : {}", forumDTO);
        if (forumDTO.getId() == null) {
            return createForum(forumDTO);
        }
        ForumDTO result = forumService.save(forumDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, forumDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forums : get all the forums.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of forums in body
     */
    @GetMapping("/forums")
    @Timed
    public List<ForumDTO> getAllForums() {
        log.debug("REST request to get all Forums");
        return forumService.findAll();
        }

    /**
     * GET  /forums/:id : get the "id" forum.
     *
     * @param id the id of the forumDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the forumDTO, or with status 404 (Not Found)
     */
    @GetMapping("/forums/{id}")
    @Timed
    public ResponseEntity<ForumDTO> getForum(@PathVariable Long id) {
        log.debug("REST request to get Forum : {}", id);
        ForumDTO forumDTO = forumService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(forumDTO));
    }

    /**
     * DELETE  /forums/:id : delete the "id" forum.
     *
     * @param id the id of the forumDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forums/{id}")
    @Timed
    public ResponseEntity<Void> deleteForum(@PathVariable Long id) {
        log.debug("REST request to delete Forum : {}", id);
        forumService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/forums?query=:query : search for the forum corresponding
     * to the query.
     *
     * @param query the query of the forum search
     * @return the result of the search
     */
    @GetMapping("/_search/forums")
    @Timed
    public List<ForumDTO> searchForums(@RequestParam String query) {
        log.debug("REST request to search Forums for query {}", query);
        return forumService.search(query);
    }

}
