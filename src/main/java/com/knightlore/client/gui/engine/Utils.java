package com.knightlore.client.gui.engine;

import java.util.List;

/**
 * Utilities needed in gui engine
 * 
 * @author Joseph
 *
 */
class Utils {

	/**
	 * 
	 */
  private Utils() {}

  /**
   * Converts a float list to a float array
   * 
   * @param list Float list
   * @return Float array
   */
  static float[] listToArray(List<Float> list) {
    int size = list != null ? list.size() : 0;
    float[] floatArr = new float[size];
    for (int i = 0; i < size; i++) {
      floatArr[i] = list.get(i);
    }
    return floatArr;
  }
}
