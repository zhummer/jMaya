package com.mayaccom.service.mapper;

import com.mayaccom.domain.*;
import com.mayaccom.service.dto.ForumDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Forum and its DTO ForumDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ForumMapper extends EntityMapper<ForumDTO, Forum> {


    @Mapping(target = "topics", ignore = true)
    Forum toEntity(ForumDTO forumDTO);

    default Forum fromId(Long id) {
        if (id == null) {
            return null;
        }
        Forum forum = new Forum();
        forum.setId(id);
        return forum;
    }
}
