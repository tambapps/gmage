package com.tambapps.gmage.transformer;

import com.tambapps.gmage.color.Color;

/**
 * Interface used to map a color to another
 */
public interface ColorTransformer {

  /**
   * Transforms a Color
   *
   * @param color a color
   * @return the non-null transformed color
   */
  Color apply(Color color);

}