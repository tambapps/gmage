package com.tambapps.gmage.transformer;

import com.tambapps.gmage.color.Color;

public final class ColorTransformers {

  private ColorTransformers() {
    throw new RuntimeException("You can't touch this");
  }

  static final ColorTransformer GRAY_SCALE = Color::grayScale;

  static final ColorTransformer RED_FILTER = Color.RED::and;

  static final ColorTransformer GREEN_FILTER = Color.GREEN::and;

  static final ColorTransformer BLUE_FILTER = Color.BLUE::and;

}
