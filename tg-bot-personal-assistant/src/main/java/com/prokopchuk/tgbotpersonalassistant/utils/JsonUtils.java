package com.prokopchuk.tgbotpersonalassistant.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonUtils {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  public static String writeAsJson(Object obj) {
    try {
      return OBJECT_MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Unable write object as JSON string", e);
    }
  }

}
