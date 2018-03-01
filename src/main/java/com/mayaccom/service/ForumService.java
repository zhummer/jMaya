package com.mayaccom.service;

import com.mayaccom.service.dto.ForumDTO;
import java.util.List;

/**
 * Service Interface for managing Forum.
 */
public interface ForumService {

    /**
     * Save a forum.
     *
     * @param forumDTO the entity to save
     * @return the persisted entity
     */
    ForumDTO save(ForumDTO forumDTO);

    /**
     * Get all the forums.
     *
     * @return the list of entities
     */
    List<ForumDTO> findAll();

    /**
     * Get the "id" forum.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ForumDTO findOne(Long id);

    /**
     * Delete the "id" forum.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the forum corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ForumDTO> search(String query);
}
