package com.tambapps.gmage.pixel;

import lombok.Value;

/**
 * Represents the pixel of an image.
 * Alpha, red, green, blue value go from 0 to 255 included
 * Default Integer values are interpreted as RGB values (alpha is considered as its maximum by default)
 * other Number values are considered as ARGB
 */
@Value
public class Pixel {

  public static final Pixel BLACK  = new Pixel(0);
  public static final Pixel WHITE  = new Pixel(0xffffff);
  public static final Pixel CLEAR  = new Pixel(0L);

  public static Pixel copy(Pixel pixel) {
    return new Pixel(pixel.rgb, pixel.alpha);
  }

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

  public Pixel(int rgb) {
    this(rgb, 255);
  }

  public Pixel(int alpha, int red, int green, int blue) {
    this.rgb = toRgb(red, green, blue);
    this.alpha = alpha;
  }

  public Pixel(int red, int green, int blue) {
    this(255, red, green, blue);
  }

  public Pixel(long rgb) {
    this.alpha = (int) ((rgb >> 24L) & 255L);
    this.rgb = (int) (rgb & 0xffffff);
  }

  public Pixel(Number rgb) {
    this(rgb.longValue());
  }

  public Pixel(Integer rgb) {
    this(rgb.intValue());
  }

  public Pixel(int rgb, int alpha) {
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

  public Pixel and(Pixel pixel) {
    return new Pixel(getArgb() & pixel.getArgb());
  }

  public Pixel and(Number number) {
    return new Pixel(getArgb() & number.intValue());
  }

  public Pixel and(Integer number) {
    return new Pixel(getArgb() & (number.longValue() | 0xff000000L));
  }

  public Pixel or(Pixel pixel) {
    return new Pixel(getArgb() | pixel.getArgb());
  }

  public Pixel or(Number number) {
    return new Pixel(getArgb() | number.intValue());
  }

  public Pixel or(Integer number) {
    return new Pixel(getArgb() | number.longValue() | 0xff000000L);
  }

  @Override
  public String toString() {
    return "#" + componentString(getAlpha()) + componentString(getRed()) + componentString(
        getGreen()) + componentString(getBlue());
  }

  private String componentString(int value) {
    String s = Integer.toHexString(value).toUpperCase();
    if (s.length() == 1) {
      return "0" + s;
    }
    return s;
  }
}
