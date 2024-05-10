package com.prokopchuk.tgbotpersonalassistant.notification.mapper;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.notification.NotificationDto;
import com.prokopchuk.tgbotpersonalassistant.notification.domain.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class NotificationMapper {

  @Mapping(target = "id", ignore = true)
  public abstract Notification toEntity(NotificationDto dto);

  public abstract NotificationDto toDto(Notification entity);

}
