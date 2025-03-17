package com.prokopchuk.reactivenoteapp.service.mapper;

import com.prokopchuk.commons.dto.UserDto;
import com.prokopchuk.reactivenoteapp.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto dto);

    UserDto toDto(User entity);

    @Mapping(target = "id", ignore = true)
    void map(UserDto dto, @MappingTarget User entity);

}
