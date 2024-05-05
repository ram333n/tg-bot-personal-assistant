package com.prokopchuk.tgbotpersonalassistant.commons.orm;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {

  @CreatedDate
  @Column(name = "date_created")
  protected LocalDateTime dateCreated;

  @LastModifiedDate
  @Column(name = "date_modified")
  protected LocalDateTime dateModified;

}
