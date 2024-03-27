package com.prokopchuk.tgbotpersonalassistant.translation;

import com.prokopchuk.tgbotpersonalassistant.commons.dto.translation.TranslationResultDto;

public interface TranslationService {

  TranslationResultDto translate(String text, String targetLang);

}
