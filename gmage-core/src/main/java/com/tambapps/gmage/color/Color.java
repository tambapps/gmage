package com.tambapps.gmage.color;

import lombok.Value;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a color
 * Alpha, red, green, blue value go from 0 to 255 included
 * Default Integer values are interpreted as RGB values (alpha is considered as its maximum by default)
 * other Number values are considered as ARGB
 */
@Value
public class Color {

  public static final Color BLACK  = new Color(0);
  public static final Color WHITE  = new Color(0xffffff);
  public static final Color CLEAR  = new Color(0L);
  public static final Color RED  = new Color(255, 0, 0);
  public static final Color GREEN  = new Color(0, 255, 0);
  public static final Color BLUE  = new Color(0, 0, 255);

  public static int toRgb(int red, int green, int blue) {
    int color = 0;
    color |= (red & 255) << 16;
    color |= (green & 255) << 8;
    color |= (blue & 255);
    return color;
  }

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

  public Color(int rgb) {
    this(rgb, 255);
  }

  public Color(int alpha, int red, int green, int blue) {
    this.rgb = toRgb(red, green, blue);
    this.alpha = alpha;
  }

  public Color(int red, int green, int blue) {
    this(255, red, green, blue);
  }

  public Color(long argb) {
    this.alpha = (int) ((argb >> 24L) & 255L);
    this.rgb = (int) (argb & 0xffffff);
  }

  public Color(Number argb) {
    this(argb.longValue());
  }

  public Color(Integer rgb) {
    this(rgb.intValue());
  }

  public Color(int rgb, int alpha) {
    this.rgb = rgb & 0xffffff;
    this.alpha = alpha & 255;
  }

  public int getRed() {
    return (rgb >> 16) & 255;
  }

  public int getGreen() {
    return (rgb >> 8) & 255;
  }

  public int getBlue() {
    return rgb & 255;
  }

  public long getArgb() {
    long argb = this.rgb;
    argb |= ((long)alpha) << 24L;
    return argb;
  }

  public Color and(Color color) {
    return new Color(getArgb() & color.getArgb());
  }

  public Color and(Number number) {
    return and(new Color(number));
  }

  public Color and(Integer number) {
    return and(new Color(number));
  }

  public Color or(Color color) {
    return new Color(getArgb() | color.getArgb());
  }

  public Color or(Number number) {
    return or(new Color(number));
  }

  public Color or(Integer number) {
    return or(new Color(number));
  }

  public Color plus(Color color) {
    return new Color(Math.min(255, getAlpha() + color.getAlpha()),
        Math.min(255, getRed() + color.getRed()),
        Math.min(255, getGreen() + color.getGreen()),
        Math.min(255, getBlue() + color.getBlue()));
  }

  public Color plus(Number number) {
    return plus(new Color(number));
  }

  public Color plus(Integer number) {
    return plus(new Color(number));
  }

  public Color minus(Color color) {
    return new Color(Math.max(0, getAlpha() - color.getAlpha()),
        Math.max(0, getRed() - color.getRed()),
        Math.max(0, getGreen() - color.getGreen()),
        Math.max(0, getBlue() - color.getBlue()));
  }

  public Color minus(Number number) {
    return minus(new Color(number));
  }

  public Color minus(Integer number) {
    return minus(new Color(number));
  }

  @Override
  public String toString() {
    return String.format("Color(A=%d, R=%d, G=%d, B=%d)", getAlpha(), getRed(), getGreen(), getBlue());
  }

  public String toHexString() {
    return "#" + Stream.of(getAlpha(), getRed(), getGreen(), getBlue())
        .map(this::componentString)
        .collect(Collectors.joining());
  }

  private String componentString(int value) {
    String s = Integer.toHexString(value).toUpperCase();
    if (s.length() == 1) {
      return "0" + s;
    }
    return s;
  }
}