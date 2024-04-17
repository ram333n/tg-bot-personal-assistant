package com.prokopchuk.tgbotpersonalassistant.commons.dto.translation;

import lombok.Data;

@Data
public class TranslationResultDto {

  private String sourceLang;
  private String targetLang;
  private String translation;

}
