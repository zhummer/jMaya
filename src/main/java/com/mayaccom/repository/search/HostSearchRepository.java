package com.mayaccom.repository.search;

import com.mayaccom.domain.Host;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Host entity.
 */
public interface HostSearchRepository extends ElasticsearchRepository<Host, Long> {
}
