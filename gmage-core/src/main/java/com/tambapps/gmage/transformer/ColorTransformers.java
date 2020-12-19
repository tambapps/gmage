package com.tambapps.gmage.transformer;

import com.tambapps.gmage.color.Color;

public final class ColorTransformers {

  private ColorTransformers() {
    throw new RuntimeException("You can't touch this");
  }

  static final ColorTransformer GRAY_SCALE = (Color c) -> {
    int gray = (c.getRed() + c.getGreen() + c.getBlue()) / 3;
    return new Color(c.getAlpha(), gray, gray, gray);
  };

  static final ColorTransformer RED_FILTER = (Color c) -> new Color(c.getArgb() & 0xffff0000);

  static final ColorTransformer GREEN_FILTER = (Color c) -> new Color(c.getArgb() & 0xff00ff00);

  static final ColorTransformer BLUE_FILTER = (Color c) -> new Color(c.getArgb() & 0xff0000ff);

}
