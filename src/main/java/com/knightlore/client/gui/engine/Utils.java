package com.knightlore.client.gui.engine;

import java.util.List;

class Utils {

  private Utils() {}

  static float[] listToArray(List<Float> list) {
    int size = list != null ? list.size() : 0;
    float[] floatArr = new float[size];
    for (int i = 0; i < size; i++) {
      floatArr[i] = list.get(i);
    }
    return floatArr;
  }
}
