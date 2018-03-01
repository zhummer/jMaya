package com.mayaccom.repository;

import com.mayaccom.domain.Forum;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Forum entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ForumRepository extends JpaRepository<Forum, Long> {

}
