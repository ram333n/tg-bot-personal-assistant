package com.prokopchuk.tgbotpersonalassistant.commons;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class Pair<K, V> {

  K V1;
  V V2;

}
