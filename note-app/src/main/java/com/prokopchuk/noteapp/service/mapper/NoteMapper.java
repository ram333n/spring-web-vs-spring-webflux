package com.prokopchuk.noteapp.service.mapper;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.noteapp.domain.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreated", ignore = true)
    @Mapping(target = "dateModified", ignore = true)
    Note toEntity(NoteDto dto);

    NoteDto toDto(Note entity);

    @Mapping(target = "id", ignore = true)
    void map(NoteDto dto, @MappingTarget Note entity);

}
