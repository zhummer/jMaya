package com.mayaccom.service;

import com.mayaccom.service.dto.HostDTO;
import java.util.List;

/**
 * Service Interface for managing Host.
 */
public interface HostService {

    /**
     * Save a host.
     *
     * @param hostDTO the entity to save
     * @return the persisted entity
     */
    HostDTO save(HostDTO hostDTO);

    /**
     * Get all the hosts.
     *
     * @return the list of entities
     */
    List<HostDTO> findAll();

    /**
     * Get the "id" host.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HostDTO findOne(Long id);

    /**
     * Delete the "id" host.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the host corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<HostDTO> search(String query);
}
