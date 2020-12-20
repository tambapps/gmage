package com.tambapps.gmage.transformer;

import com.tambapps.gmage.color.Color;

public final class ColorTransformers {

  private ColorTransformers() {
    throw new RuntimeException("You can't touch this");
  }

  private static final ColorTransformer GRAY_SCALE = Color::grayScale;

  private static final ColorTransformer RED_FILTER = Color.RED::and;

  private static final ColorTransformer GREEN_FILTER = Color.GREEN::and;

  private static final ColorTransformer BLUE_FILTER = Color.BLUE::and;

  public static ColorTransformer grayScale() {
    return GRAY_SCALE;
  }

  public static ColorTransformer redFilter() {
    return RED_FILTER;
  }

  public static ColorTransformer greenFilter() {
    return GREEN_FILTER;
  }

  public static ColorTransformer blueFilter() {
    return BLUE_FILTER;
  }

  public static ColorTransformer duoTone(Color color) {
    return (c) -> c.grayScale().and(color);
  }
}
