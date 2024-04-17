package com.prokopchuk.tgbotpersonalassistant.session.mapper;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.UserSessionDto;
import com.prokopchuk.tgbotpersonalassistant.session.domain.UserSession;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserSessionMapper {

  @Mapping(target = "id", ignore = true)
  public abstract UserSession toEntity(UserSessionDto dto);

  public abstract UserSessionDto toDto(UserSession entity);

}
