package com.prokopchuk.tgbotpersonalassistant.session.domain;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.session.ConversationState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_sessions")
public class UserSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", nullable = false)
  private Long userId;

  @Column(name = "chat_id", nullable = false)
  private Long chatId;

  @Enumerated(EnumType.STRING)
  @Column(name = "state", nullable = false)
  private ConversationState state;

  @Column(name = "state_data", columnDefinition = "TEXT")
  private String stateData;

}
