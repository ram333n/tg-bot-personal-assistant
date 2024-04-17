package com.prokopchuk.tgbotpersonalassistant.commons.dto.translation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public enum Language { //TODO: add more languages

  ENGLISH("English", "en"),
  UKRAINIAN("Ukrainian", "uk"),
  GERMAN("German", "de"),
  FRENCH("French", "fr"),
  SPANISH("Spanish", "es"),
  ITALIAN("Italian", "it"),
  PORTUGUESE("Portuguese", "pt"),
  DUTCH("Dutch", "nl"),
  SWEDISH("Swedish", "sv"),
  POLISH("Polish", "pl"),
  GREEK("Greek", "el"),
  NORWEGIAN("Norwegian", "no"),
  DANISH("Danish", "da"),
  CZECH("Czech", "cs"),
  HUNGARIAN("Hungarian", "hu"),
  FINNISH("Finnish", "fi"),
  ROMANIAN("Romanian", "ro"),
  CATALAN("Catalan", "ca"),
  SERBIAN("Serbian", "sr");

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
