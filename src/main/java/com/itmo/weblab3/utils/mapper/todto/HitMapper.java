package com.itmo.weblab3.utils.mapper.todto;

import com.itmo.weblab3.dto.HitDTO;
import com.itmo.weblab3.model.Hit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Mapper
public interface HitMapper {
    HitMapper INSTANCE = Mappers.getMapper(HitMapper.class);

    HitDTO toDTO(Hit hit);
}
