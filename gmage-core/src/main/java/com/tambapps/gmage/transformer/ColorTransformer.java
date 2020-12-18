package com.tambapps.gmage.transformer;

import com.tambapps.gmage.pixel.Color;

/**
 * Interface used to map a color to another
 */
public interface ColorTransformer {

  Color apply(Color color);

}