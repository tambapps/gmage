package com.tambapps.gmage.transformer;

import com.tambapps.gmage.color.Color;

/**
 * Class providing static functions for common color transformations
 */
public final class ColorTransformers {

  private ColorTransformers() {
    throw new RuntimeException("You can't touch this");
  }

  private static final ColorTransformer GRAY_SCALE = Color::grayScale;

  private static final ColorTransformer RED_FILTER = Color.RED::and;

  private static final ColorTransformer GREEN_FILTER = Color.GREEN::and;

  private static final ColorTransformer BLUE_FILTER = Color.BLUE::and;

  private static final ColorTransformer NEGATIVE_TRANSFORMER = Color::negative;

  /**
   * A gray scale transformer
   * @return a gray scale transformer
   */
  public static ColorTransformer grayScale() {
    return GRAY_SCALE;
  }

  /**
   * A red filtering transformer
   * @return a red filtering transformer
   */
  public static ColorTransformer redFilter() {
    return RED_FILTER;
  }

  /**
   * A green filtering transformer
   * @return a green filtering transformer
   */
  public static ColorTransformer greenFilter() {
    return GREEN_FILTER;
  }

  /**
   * A blue filtering transformer
   * @return a blue filtering transformer
   */
  public static ColorTransformer blueFilter() {
    return BLUE_FILTER;
  }

  /**
   * A negative transformer
   * @return a negative transformer
   */
  public static ColorTransformer negative() {
    return NEGATIVE_TRANSFORMER;
  }

  /**
   * A duo tone transformer. Basically, it consits of a gray scale is followed by a logical
   * AND with the provided color
   *
   * @param color the color to duo-tone with when transforming
   * @return a duo-tone transformer
   */
  public static ColorTransformer duoTone(Color color) {
    return (c) -> c.grayScale().and(color);
  }

  /**
   * A bi-color transformer. Each gmage transformed will have pixels either black or white
   * @return a bi-color transformer
   */
  public static ColorTransformer biColor() {
    return biColor(Color.BLACK, Color.WHITE);
  }

  /**
   * A bi-color transformer. Each gmage transformed will have pixels either black or white
   * @param threshold the threshold (between 0 and 255 included) applied each pixel
   *                  (using gray scale) to decide whether the color should be black or white
   * @return a bi-color transformer
   */
  public static ColorTransformer biColor(int threshold) {
    return biColor(Color.BLACK, Color.WHITE, threshold);
  }

  /**
   * A bi-color transformer. Each gmage transformed will have pixels either black color or white color
   * @param blackColor the color to used instead of black
   * @param whiteColor the color to used instead of white
   * @return a bi-color transformer
   */
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
   * @return a bi-color transformer
   */
  public static ColorTransformer biColor(Color blackColor, Color whiteColor, int threshold) {
    return (c) -> c.grayLevel() < threshold ? blackColor : whiteColor;
  }

  /**
   * A transformer replacing the targetColor pixels to the replacedColor
   * @param targetColor the target color
   * @param replacedColor the replace color
   * @return a replacing color transformer
   */
  public static ColorTransformer replaceColor(Color targetColor, Color replacedColor) {
    return replaceColor(targetColor, replacedColor, 0f);
  }

  /**
   * A transformer replacing the targetColor pixels to the replacedColor
   * @param targetColor the target color
   * @param replacedColor the replace color
   * @param maxDifferencePercentage the max difference percentage to consider when deciding to replace color or not (between 0 and 100)
   * @return a replacing color transformer
   */
  public static ColorTransformer replaceColor(Color targetColor, Color replacedColor, float maxDifferencePercentage) {
    if (maxDifferencePercentage < 0 || maxDifferencePercentage > 100) {
      throw new IllegalArgumentException("The differencePercentage should be between 0 and 100 (included)");
    }
    final float differencePercentage2 = maxDifferencePercentage * maxDifferencePercentage;
    return (c) -> c.differencePercentage2(targetColor) <= differencePercentage2 ? replacedColor : c;
  }

  /**
   * A transformer replacing the targetColor pixels to the replacedColor. For each pixel,
   * The difference between the pixel and the targetColor will be echoed when replacing color with
   * the target color
   * @param targetColor the target color
   * @param replacedColor the replace color
   * @param maxDifferencePercentage the max difference percentage to consider when deciding to replace color or not (between 0 and 100)
   * @return a replacing color transformer
   */
  public static ColorTransformer replaceColorIncludingDiffs(Color targetColor, Color replacedColor, float maxDifferencePercentage) {
    if (maxDifferencePercentage < 0 || maxDifferencePercentage > 100) {
      throw new IllegalArgumentException("The differencePercentage should be between 0 and 100 (included)");
    }
    final float differencePercentage2 = maxDifferencePercentage * maxDifferencePercentage;
    return (c) -> c.differencePercentage2(targetColor) <= differencePercentage2 ? replacedColor.plus(targetColor.diff(c)) : c;
  }
}
