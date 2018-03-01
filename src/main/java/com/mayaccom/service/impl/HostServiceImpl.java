package com.mayaccom.service.impl;

import com.mayaccom.service.HostService;
import com.mayaccom.domain.Host;
import com.mayaccom.repository.HostRepository;
import com.mayaccom.repository.search.HostSearchRepository;
import com.mayaccom.service.dto.HostDTO;
import com.mayaccom.service.mapper.HostMapper;
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
 * Service Implementation for managing Host.
 */
@Service
@Transactional
public class HostServiceImpl implements HostService {

    private final Logger log = LoggerFactory.getLogger(HostServiceImpl.class);

    private final HostRepository hostRepository;

    private final HostMapper hostMapper;

    private final HostSearchRepository hostSearchRepository;

    public HostServiceImpl(HostRepository hostRepository, HostMapper hostMapper, HostSearchRepository hostSearchRepository) {
        this.hostRepository = hostRepository;
        this.hostMapper = hostMapper;
        this.hostSearchRepository = hostSearchRepository;
    }

    /**
     * Save a host.
     *
     * @param hostDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public HostDTO save(HostDTO hostDTO) {
        log.debug("Request to save Host : {}", hostDTO);
        Host host = hostMapper.toEntity(hostDTO);
        host = hostRepository.save(host);
        HostDTO result = hostMapper.toDto(host);
        hostSearchRepository.save(host);
        return result;
    }

    /**
     * Get all the hosts.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HostDTO> findAll() {
        log.debug("Request to get all Hosts");
        return hostRepository.findAll().stream()
            .map(hostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one host by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public HostDTO findOne(Long id) {
        log.debug("Request to get Host : {}", id);
        Host host = hostRepository.findOne(id);
        return hostMapper.toDto(host);
    }

    /**
     * Delete the host by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Host : {}", id);
        hostRepository.delete(id);
        hostSearchRepository.delete(id);
    }

    /**
     * Search for the host corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<HostDTO> search(String query) {
        log.debug("Request to search Hosts for query {}", query);
        return StreamSupport
            .stream(hostSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(hostMapper::toDto)
            .collect(Collectors.toList());
    }
}
