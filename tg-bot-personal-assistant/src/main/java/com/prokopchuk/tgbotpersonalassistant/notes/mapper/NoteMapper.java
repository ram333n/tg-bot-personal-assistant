package com.prokopchuk.tgbotpersonalassistant.notes.mapper;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notes.NoteDto;
import com.prokopchuk.tgbotpersonalassistant.notes.domain.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class NoteMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateModified", ignore = true)
  public abstract Note toEntity(NoteDto dto);

  public abstract NoteDto toDto(Note entity);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "dateCreated", ignore = true)
  @Mapping(target = "dateModified", ignore = true)
  public abstract void map(NoteDto src, @MappingTarget Note target);

}
