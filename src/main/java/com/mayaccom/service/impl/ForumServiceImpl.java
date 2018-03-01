package com.mayaccom.service.impl;

import com.mayaccom.service.ForumService;
import com.mayaccom.domain.Forum;
import com.mayaccom.repository.ForumRepository;
import com.mayaccom.repository.search.ForumSearchRepository;
import com.mayaccom.service.dto.ForumDTO;
import com.mayaccom.service.mapper.ForumMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Forum.
 */
@Service
@Transactional
public class ForumServiceImpl implements ForumService {

    private final Logger log = LoggerFactory.getLogger(ForumServiceImpl.class);

    private final ForumRepository forumRepository;

    private final ForumMapper forumMapper;

    private final ForumSearchRepository forumSearchRepository;

    public ForumServiceImpl(ForumRepository forumRepository, ForumMapper forumMapper, ForumSearchRepository forumSearchRepository) {
        this.forumRepository = forumRepository;
        this.forumMapper = forumMapper;
        this.forumSearchRepository = forumSearchRepository;
    }

    /**
     * Save a forum.
     *
     * @param forumDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ForumDTO save(ForumDTO forumDTO) {
        log.debug("Request to save Forum : {}", forumDTO);
        Forum forum = forumMapper.toEntity(forumDTO);
        forum = forumRepository.save(forum);
        ForumDTO result = forumMapper.toDto(forum);
        forumSearchRepository.save(forum);
        return result;
    }

    /**
     * Get all the forums.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ForumDTO> findAll() {
        log.debug("Request to get all Forums");
        return forumRepository.findAll().stream()
            .map(forumMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one forum by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ForumDTO findOne(Long id) {
        log.debug("Request to get Forum : {}", id);
        Forum forum = forumRepository.findOne(id);
        return forumMapper.toDto(forum);
    }

    /**
     * Delete the forum by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Forum : {}", id);
        forumRepository.delete(id);
        forumSearchRepository.delete(id);
    }

    /**
     * Search for the forum corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ForumDTO> search(String query) {
        log.debug("Request to search Forums for query {}", query);
        return StreamSupport
            .stream(forumSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(forumMapper::toDto)
            .collect(Collectors.toList());
    }
}
