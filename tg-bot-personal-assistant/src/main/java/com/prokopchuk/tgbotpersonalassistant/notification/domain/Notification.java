package com.prokopchuk.tgbotpersonalassistant.notification.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "notifications")
public class Notification {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "chat_id", nullable = false)
  private Long chatId;

  @Column(name = "description", columnDefinition = "VARCHAR(500)")
  private String description;

  @Column(name = "process_id", nullable = false)
  private UUID processId;

  @Column(name = "scheduling_time", nullable = false)
  private LocalDateTime schedulingTime;

  @CreatedDate
  @Column(name = "date_created", nullable = false)
  private LocalDateTime dateCreated;

}
