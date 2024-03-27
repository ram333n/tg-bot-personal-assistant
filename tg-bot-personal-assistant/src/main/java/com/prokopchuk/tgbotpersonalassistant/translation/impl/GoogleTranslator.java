package com.prokopchuk.tgbotpersonalassistant.translation.impl;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;
import com.prokopchuk.tgbotpersonalassistant.commons.dto.translation.TranslationResultDto;
import com.prokopchuk.tgbotpersonalassistant.translation.TranslationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoogleTranslator implements TranslationService {

  private final Translate translator;

  public GoogleTranslator(@Value("${translation.google.api-key}") String apiKey) {
    this.translator = TranslateOptions.newBuilder()
        .setApiKey(apiKey)
        .build()
        .getService();
  }

  @Override
  public TranslationResultDto translate(String text, String targetLang) {
    log.info("Request on translating. Target language: {}, text: {}", targetLang, text); //TODO: log on higher level?
    Translation translation = translator.translate(text, Translate.TranslateOption.targetLanguage(targetLang));

    return toDto(translation, targetLang);
  }

  private TranslationResultDto toDto(Translation translation, String targetLang) {
    TranslationResultDto result = new TranslationResultDto();
    result.setSourceLang(translation.getSourceLanguage());
    result.setTargetLang(targetLang);
    result.setTranslation(translation.getTranslatedText());

    return result;
  }

}
