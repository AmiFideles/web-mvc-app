package com.itmo.weblab3.utils.mapper.toentity;

import com.itmo.weblab3.dto.HitDTO;
import com.itmo.weblab3.model.Hit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface HitDTOMapper {
    HitDTOMapper INSTANCE = Mappers.getMapper(HitDTOMapper.class);

    Hit toHit(HitDTO hitDTO);
}
