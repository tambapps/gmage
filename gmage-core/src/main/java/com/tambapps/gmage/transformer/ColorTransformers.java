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
  public static ColorTransformer biColor() {
    return biColor(Color.BLACK, Color.WHITE);
  }

  public static ColorTransformer biColor(int threshold) {
    return biColor(Color.BLACK, Color.WHITE, threshold);
  }

  public static ColorTransformer biColor(Color blackColor, Color whiteColor) {
    return biColor(blackColor, whiteColor, 255 / 2);
  }

  /**
   * Transforms a gmage to an image having pixels only of whiteColor or blackColor
   * The average of the RGB channels is computed. If it's greater than the threshold,
   * the color will be replaced by the white color, otherwise the black color
   *
   * @param blackColor the color used for averages below the threshold
   * @param whiteColor the color used for averages equal or above the threshold
   * @param threshold the threshold
   * @return a transformer transforming a gmage into a gmage with only two colors
   */
  public static ColorTransformer biColor(Color blackColor, Color whiteColor, int threshold) {
    return (c) -> c.grayLevel() < threshold ? blackColor : whiteColor;
  }
}
