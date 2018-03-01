package com.mayaccom.repository;

import com.mayaccom.domain.Host;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Host entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

}
