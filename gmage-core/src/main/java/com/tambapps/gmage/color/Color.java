package com.tambapps.gmage.color;

import lombok.Value;

import java.util.function.Function;

/**
 * Represents a color
 * Alpha, red, green, blue value go from 0 to 255 included
 * Default Integer values are interpreted as RGB values (alpha is considered as its maximum by default)
 * other Number values are considered as ARGB
 *
 * Careful! int/Integer values represent RGB Color but other Numbers represents ARGB values!
 * This can sometimes be tricky in Groovy:
 * 0xffffffff is Long, so not Integer
 * 0x00ffffff is Integer
 * 0xffffff is Integer
 * cast explicitly if you want to be sure to have the desired constructor
 */
@Value
public class Color {

  public static final Color BLACK  = new Color(0);
  public static final Color WHITE  = new Color(0xffffff);
  public static final Color CLEAR  = new Color(0L);
  public static final Color RED  = new Color(255, 0, 0);
  public static final Color GREEN  = new Color(0, 255, 0);
  public static final Color BLUE  = new Color(0, 0, 255);

  public static float MAX_DISTANCE_2 = CLEAR.distance2(WHITE);

  /**
   * Constructs a Color from the supplied hexadecimal string. The string can start with a '#'.
   * It can represent a ARGB or RGB color
   * @param hex the hexadecimal string to decode
   * @return the decoded color
   */
  public static Color fromHexString(String hex) {
    if (hex.startsWith("#")) {
      hex = hex.substring(1);
    }
    switch (hex.length()) {
      case 6:
        return new Color(Integer.parseInt(hex.substring(0, 2), 16), Integer.parseInt(hex.substring(2, 4), 16), Integer.parseInt(hex.substring(4, 6), 16));
      case 8:
        return new Color(Integer.parseInt(hex.substring(0, 2), 16), Integer.parseInt(hex.substring(2, 4), 16), Integer.parseInt(hex.substring(4, 6), 16), Integer.parseInt(hex.substring(6, 8), 16));
      default:
        throw new IllegalArgumentException("Hex String must contain rgb or argb values");
    }
  }

  /**
   * Useful function to convert red, green, blue values to a RGB int
   * @param red red channel (between 0 and 255 included)
   * @param green green channel (between 0 and 255 included)
   * @param blue blue channel (between 0 and 255 included)
   * @return the rgb int
   */
  public static int toRgb(int red, int green, int blue) {
    int color = 0;
    color |= (red & 255) << 16;
    color |= (green & 255) << 8;
    color |= (blue & 255);
    return color;
  }

  /**
   * Useful function to convert alpha, red, green, blue values to an ARGB int
   * @param alpha alpha channel (between 0 and 255 included)
   * @param red red channel (between 0 and 255 included)
   * @param green green channel (between 0 and 255 included)
   * @param blue blue channel (between 0 and 255 included)
   * @return the rgb int
   */
  public static long toArgb(int alpha, int red, int green, int blue) {
    long color = 0;
    color |= (long) alpha << 24;
    color |= (long) red << 16;
    color |= (long) green << 8;
    color |= blue;
    return color;
  }

  int rgb;
  int alpha;

  /**
   * Construct a color from a RGB int value
   * @param rgb the rgb value
   */
  public Color(int rgb) {
    this(rgb, 255);
  }

  /**
   * Construct a color from alpha red, green and blue values
   * @param alpha alpha channel (between 0 and 255 included)
   * @param red red channel (between 0 and 255 included)
   * @param green green channel (between 0 and 255 included)
   * @param blue blue channel (between 0 and 255 included)
   */
  public Color(int alpha, int red, int green, int blue) {
    this.rgb = toRgb(red, green, blue);
    this.alpha = alpha;
  }

  /**
   * Construct a color from red, green and blue values
   * @param red red channel (between 0 and 255 included)
   * @param green green channel (between 0 and 255 included)
   * @param blue blue channel (between 0 and 255 included)
   */
  public Color(int red, int green, int blue) {
    this(255, red, green, blue);
  }

  /**
   * Construct a color from a ARGB int value
   * @param argb the argb value
   */
  public Color(long argb) {
    this.alpha = (int) ((argb >> 24L) & 255L);
    this.rgb = (int) (argb & 0xffffff);
  }

  /**
   * Construct a color from a ARGB int value
   * @param argb the argb value
   */
  public Color(Number argb) {
    this(argb.longValue());
  }

  /**
   * Construct a color from a RGB int value
   * @param rgb the rgb value
   */
  public Color(Integer rgb) {
    this(rgb.intValue());
  }

  /**
   * Construct a color from a RGB int value
   * @param rgb the rgb value
   * @param alpha the alpha channel (between 0 and 255 included)
   */
  public Color(int rgb, int alpha) {
    this.rgb = rgb & 0xffffff;
    this.alpha = alpha & 255;
  }

  /**
   * The red channel
   * @return the red channel
   */
  public int getRed() {
    return (rgb >> 16) & 255;
  }

  /**
   * The green channel
   * @return the green channel
   */
  public int getGreen() {
    return (rgb >> 8) & 255;
  }

  /**
   * The blue channel
   * @return the blue channel
   */
  public int getBlue() {
    return rgb & 255;
  }

  /**
   * The ARGB value of this color
   * @return the ARGB value
   */
  public long getArgb() {
    long argb = this.rgb;
    argb |= ((long)alpha) << 24L;
    return argb;
  }

  /**
   * Computes a binary AND
   * @param color a color
   * @return a color resulting of a binary AND between this and the color
   */
  public Color and(Color color) {
    return new Color(getArgb() & color.getArgb());
  }

  /**
   * Computes a binary AND
   * @param number a ARGB number
   * @return a color resulting of a binary AND between this and the ARGB number
   */
  public Color and(Number number) {
    return and(new Color(number));
  }

  /**
   * Computes a binary AND
   * @param number a RGB number
   * @return a color resulting of a binary AND between this and the RGB number
   */
  public Color and(Integer number) {
    return and(new Color(number));
  }

  /**
   * Computes a binary OR
   * @param color a color
   * @return a color resulting of a binary OR between this and the color
   */
  public Color or(Color color) {
    return new Color(getArgb() | color.getArgb());
  }

  /**
   * Computes a binary OR
   * @param number a ARGB number
   * @return a color resulting of a binary OR between this and the ARGB number
   */
  public Color or(Number number) {
    return or(new Color(number));
  }

  /**
   * Computes a binary OR
   * @param number a RGB number
   * @return a color resulting of a binary OR between this and the RGB number
   */
  public Color or(Integer number) {
    return or(new Color(number));
  }

  /**
   * Computes a color that is the sum of this and the argument (for all channels). All values
   * are bounded between 0 and 255 included
   * @param color a color
   * @return the bounded sum of this and the color
   */
  public Color plus(Color color) {
    return new Color(Math.min(255, getAlpha() + color.getAlpha()),
        Math.min(255, getRed() + color.getRed()),
        Math.min(255, getGreen() + color.getGreen()),
        Math.min(255, getBlue() + color.getBlue()));
  }

  /**
   * Computes a color that is the sum of this and the argument (for all channels). All values
   * are bounded between 0 and 255 included
   * @param number a ARGB number
   * @return the bounded sum of this and the ARGB number
   */
  public Color plus(Number number) {
    return plus(new Color(number));
  }

  /**
   * Computes a color that is the sum of this and the argument (for all channels). All values
   * are bounded between 0 and 255 included
   * @param number a RGB number
   * @return the bounded sum of this and the RGB number
   */
  public Color plus(Integer number) {
    return plus(new Color(number));
  }

  /**
   * Computes a color that is the difference between this and the argument (for all channels).
   * All values are bounded between 0 and 255 included
   * @param color a color
   * @return the bounded difference of this and the color
   */
  public Color minus(Color color) {
    return new Color(Math.max(0, getAlpha()),
        Math.max(0, getRed() - color.getRed()),
        Math.max(0, getGreen() - color.getGreen()),
        Math.max(0, getBlue() - color.getBlue()));
  }

  /**
   * Computes a color that is the difference between this and the argument (for all channels).
   * All values are bounded between 0 and 255 included
   * @param number a ARGB number
   * @return the bounded difference of this and the ARGB number
   */
  public Color minus(Number number) {
    return minus(new Color(number));
  }

  /**
   * Computes a color that is the difference between this and the argument (for all channels).
   * All values are bounded between 0 and 255 included
   * @param number a RGB number
   * @return the bounded difference of this and the RGB number
   */
  public Color minus(Integer number) {
    return minus(new Color(number));
  }

  /**
   * Returns the negative color (alpha unmodified).
   * @return the negative color (alpha unmodified)
   */
  public Color negative() {
    return new Color(getAlpha(), 255 - getRed(), 255 - getGreen(), 255 - getBlue());
  }

  /**
   * Returns the gray level, that is the some of the RGB channels divided by 3
   * @return the gray level
   */
  public int grayLevel() {
    return (getRed() + getGreen() + getBlue()) / 3;
  }

  /**
   * Returns the gray scale of this color
   * @return the gray scale
   */
  public Color grayScale() {
    int gray = grayLevel();
    return new Color(getAlpha(), gray, gray, gray);
  }

  /**
   * Computes the four dimensional (ARGB) squared distance between this and the argument.
   * @param color a color
   * @return the squared distance
   */
  public float distance2(Color color) {
    return pow2(color.getAlpha() - getAlpha()) +
        pow2(color.getRed() - getRed()) + pow2(color.getGreen() - getGreen()) + pow2(color.getBlue() - getBlue());
  }

  /**
   * Computes the four dimensional (ARGB) squared distance between this and the argument.
   * @param number a ARGB number
   * @return the squared distance
   */
  public float distance2(Number number) {
    return distance2(new Color(number));
  }

  /**
   * Computes the four dimensional (RGB) squared distance between this and the argument.
   * @param number a RGB number
   * @return the squared distance
   */
  public float distance2(Integer number) {
    return distance2(new Color(number));
  }

  /**
   * Computes the four dimensional (ARGB) distance between this and the argument.
   * @param color a color
   * @return the distance
   */
  public float distance(Color color) {
    return (float) Math.sqrt(distance2(color));
  }

  /**
   * Computes the four dimensional (ARGB) distance between this and the argument.
   * @param number a ARGB number
   * @return the distance
   */
  public float distance(Number number) {
    return distance(new Color(number));
  }

  /**
   * Computes the four dimensional (RGB) squared distance between this and the argument.
   * @param number a RGB number
   * @return the squared distance
   */
  public float distance(Integer number) {
    return distance(new Color(number));
  }

  /**
   * Computes the difference Color (every channels) between this and the argument
   * @param color a color
   * @return the difference color
   */
  public Color diff(Color color) {
    return new Color(color.getAlpha() - getAlpha(), color.getRed() - getRed(), color.getGreen() - getGreen(), color.getBlue() - getBlue());
  }

  /**
   * Computes the difference Color (every channels) between this and the argument
   * @param number a ARGB number
   * @return the difference color
   */
  public Color diff(Number number) {
    return diff(new Color(number));
  }

  /**
   * Computes the difference Color (every channels) between this and the argument
   * @param number a RGB number
   * @return the difference color
   */
  public Color diff(Integer number) {
    return diff(new Color(number));
  }

  /**
   * Computes the squared difference percentage between this and the argument.
   * This percentage is the squared of ( the distance between the two colors divided by
   * the maximum distance possible between two colors )
   * @param color a color
   * @return the squared difference percentage
   */
  public float differencePercentage2(Color color) {
    return distance2(color) / MAX_DISTANCE_2;
  }

  /**
   * Computes the squared difference percentage between this and the argument.
   * This percentage is the squared of ( the distance between the two colors divided by
   * the maximum distance possible between two colors )
   * @param number a ARGB number
   * @return the squared difference percentage
   */
  public float differencePercentage2(Number number) {
    return differencePercentage2(new Color(number));
  }

  /**
   * Computes the squared difference percentage between this and the argument.
   * This percentage is the squared of ( the distance between the two colors divided by
   * the maximum distance possible between two colors )
   * @param number a RGB number
   * @return the squared difference percentage
   */
  public float differencePercentage2(Integer number) {
    return differencePercentage2(new Color(number));
  }

  private float pow2(float a) {
    return a * a;
  }

  @Override
  public String toString() {
    return String.format("Color(A=%d, R=%d, G=%d, B=%d)", getAlpha(), getRed(), getGreen(), getBlue());
  }

  /**
   * Returns the ARGB hexadecimal (prefixed by '#') representation of this color
   * @return the hexadecimal representation of this color
   */
  public String toArgbHexString() {
    return "#" + componentString(getAlpha()) + componentString(getRed()) + componentString(getGreen()) + componentString(getBlue());
  }

  /**
   * Returns the RGB hexadecimal (prefixed by '#') representation of this color
   * @return the hexadecimal representation of this color
   */
  public String toRgbHexString() {
    return "#" + componentString(getRed()) + componentString(getGreen()) + componentString(getBlue());
  }

  private String componentString(int value) {
    String s = Integer.toHexString(value).toUpperCase();
    if (s.length() == 1) {
      return "0" + s;
    }
    return s;
  }
}
