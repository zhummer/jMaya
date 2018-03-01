package com.mayaccom.repository.search;

import com.mayaccom.domain.Forum;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Forum entity.
 */
public interface ForumSearchRepository extends ElasticsearchRepository<Forum, Long> {
}
