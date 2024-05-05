package com.prokopchuk.tgbotpersonalassistant.session.domain;

import com.prokopchuk.tgbotpersonalassistant.commons.orm.AuditableEntity;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(
    name = "users_sessions",
    uniqueConstraints = {
        @UniqueConstraint(name = "un$users_sessions$chat_id", columnNames = {"chat_id"})
    }
)
public class UserSession extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id") //TODO: useless??
  private Long userId;

  @Column(name = "chat_id", nullable = false, unique = true)
  private Long chatId;

  @Enumerated(EnumType.STRING)
  @Column(name = "state", nullable = false)
  private ConversationState state;

  @Column(name = "state_data", columnDefinition = "TEXT")
  private String stateData;

}
