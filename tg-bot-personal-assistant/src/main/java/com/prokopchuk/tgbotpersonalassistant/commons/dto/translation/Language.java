package com.prokopchuk.tgbotpersonalassistant.commons.dto.translation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum Language {

  ENGLISH("English", "en"),
  UKRAINIAN("Ukrainian", "uk"),
  GERMAN("German", "de"),
  FRENCH("French", "fr"),
  SPANISH("Spanish", "es"),
  ITALIAN("Italian", "it"),
  PORTUGUESE("Portuguese", "pt"),
  DUTCH("Dutch", "nl"),
  JAPANESE("Japanese", "ja"),
  SWEDISH("Swedish", "sv"),
  POLISH("Polish", "pl"),
  ARABIC("Arabic", "ar"),
  GREEK("Greek", "el"),
  NORWEGIAN("Norwegian", "no"),
  DANISH("Danish", "da"),
  CZECH("Czech", "cs"),
  HUNGARIAN("Hungarian", "hu"),
  FINNISH("Finnish", "fi"),
  ROMANIAN("Romanian", "ro"),
  CATALAN("Catalan", "ca"),
  SERBIAN("Serbian", "sr"),
  CHINESE_SIMPLIFIED("Chinese (Simplified)", "zh-CN"),
  CHINESE_TRADITIONAL("Chinese (Traditional)", "zh-TW"),
  KOREAN("Korean", "ko"),
  HINDI("Hindi", "hi"),
  TURKISH("Turkish", "tr"),
  VIETNAMESE("Vietnamese", "vi"),
  THAI("Thai", "th"),
  HEBREW("Hebrew", "he"),
  INDONESIAN("Indonesian", "id"),
  SLOVAK("Slovak", "sk"),
  CROATIAN("Croatian", "hr"),
  LITHUANIAN("Lithuanian", "lt"),
  SLOVENIAN("Slovenian", "sl"),
  LATVIAN("Latvian", "lv"),
  BULGARIAN("Bulgarian", "bg"),
  ESTONIAN("Estonian", "et"),
  ICELANDIC("Icelandic", "is"),
  MALAY("Malay", "ms"),
  MALTESE("Maltese", "mt"),
  MACEDONIAN("Macedonian", "mk"),
  PERSIAN("Persian", "fa"),
  SWAHILI("Swahili", "sw"),
  FILIPINO("Filipino", "fil"),
  URDU("Urdu", "ur"),
  TAMIL("Tamil", "ta"),
  TELUGU("Telugu", "te"),
  GUJARATI("Gujarati", "gu"),
  KANNADA("Kannada", "kn"),
  MAORI("Maori", "mi");

  private static final Map<String, Language> LANGUAGES_BY_CODE = new HashMap<>();
  private static final Map<String, Language> LANGUAGES_BY_NAME = new HashMap<>();

  static {
    Arrays.stream(values())
        .forEach(v -> {
          LANGUAGES_BY_CODE.putIfAbsent(v.getCode(), v);
          LANGUAGES_BY_NAME.putIfAbsent(v.getName(), v);
        });
  }

  private final String name;
  private final String code;

  Language(String name, String code) {
    this.name = name;
    this.code = code;
  }

  public static boolean isSupportedByCode(String code) {
    return LANGUAGES_BY_CODE.containsKey(code);
  }

  public static boolean isSupportedByName(String name) {
    return LANGUAGES_BY_NAME.containsKey(name);
  }

  public static boolean isNotSupportedByName(String name) {
    return !isSupportedByName(name);
  }

  public static Language fromName(String name) {
    return LANGUAGES_BY_NAME.get(name);
  }

  public static Language fromCode(String code) {
    return LANGUAGES_BY_CODE.get(code);
  }

}
