package com.prokopchuk.tgbotpersonalassistant.notes.domain;

import com.prokopchuk.tgbotpersonalassistant.commons.orm.AuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notes")
public class Note extends AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "content", columnDefinition = "VARCHAR(2000)", nullable = false)
  private String content;

  @Column(name = "chat_id", nullable = false)
  private Long chatId;

}
