package com.mayaccom.service.mapper;

import com.mayaccom.domain.*;
import com.mayaccom.service.dto.HostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Host and its DTO HostDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HostMapper extends EntityMapper<HostDTO, Host> {


    @Mapping(target = "accomodations", ignore = true)
    Host toEntity(HostDTO hostDTO);

    default Host fromId(Long id) {
        if (id == null) {
            return null;
        }
        Host host = new Host();
        host.setId(id);
        return host;
    }
}
