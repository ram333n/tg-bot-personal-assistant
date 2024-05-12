package com.prokopchuk.tgbotpersonalassistant.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Pair<K, V> {

  private K V1;
  private V V2;

}
