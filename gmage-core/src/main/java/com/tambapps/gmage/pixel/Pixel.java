package com.tambapps.gmage.pixel;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Represents the pixel of an image.
 * Alpha, red, green, blue value go from 0 to 255 included
 */
@EqualsAndHashCode
public class Pixel {

  public static final int WHITE = 0xffffff;

  public static Pixel black() {
    return new Pixel(0);
  }

  public static Pixel of(int rgb) {
    return new Pixel(rgb);
  }

  public static Pixel of(int rgb, int alpha) {
    return new Pixel(rgb, alpha);
  }

  public static long toARGB(int alpha, int red, int green, int blue) {
    long color = 0;
    color |= (long) alpha << 24;
    color |= (long) red << 16;
    color |= (long) green << 8;
    color |= blue;
    return color;
  }

  @Getter
  private int rgb;
  @Getter
  private int alpha;

  private Pixel(int rgb, int alpha) {
    this.rgb = rgb & WHITE;
    this.alpha = alpha & 255;
  }

  private Pixel(int rgb) {
    this(rgb, 255);
  }

  public void setArgb(Number value) {
    this.alpha = (int) ((value.longValue() >> 24L) & 255L);
    this.rgb = (int) (value.longValue() & WHITE);
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

  public void setRed(int red) {
    rgb = toRgb(red, getGreen(), getBlue());
  }

  public void setGreen(int green) {
    rgb = toRgb(getRed(), green, getBlue());
  }

  public void setBlue(int blue) {
    rgb = toRgb(getRed(), getGreen(), blue);
  }

  public void setAlpha(Number alpha) {
    this.alpha = alpha.intValue() & 255;
  }

  public void setAlpha(int alpha) {
    this.alpha = alpha & 255;
  }

  public void setRgb(Number red, Number green, Number blue) {
    setRgb(red.intValue(), green.intValue(), blue.intValue());
  }

  public void setRgb(Number number) {
    setRgb(number.intValue());
  }

  public void setRgb(int number) {
    rgb = number & WHITE;
  }

  public void setRgb(int red, int green, int blue) {
    rgb = toRgb(red, green, blue);
  }

  public void setArgb(Number alpha, Number red, Number green, Number blue) {
    setArgb(alpha.intValue(), red.intValue(), green.intValue(), blue.intValue());
  }

  public void setArgb(int alpha, int red, int green, int blue) {
    this.alpha = alpha & 255;
    this.rgb = toRgb(red, green, blue);
  }

  public void set(Pixel value) {
    rgb = value.rgb;
    alpha = value.alpha;
  }

  public int toRgb(int red, int green, int blue) {
    int color = 0;
    color |= red << 16;
    color |= green << 8;
    color |= blue;
    return color;
  }

  public long getArgb() {
    long argb = this.rgb;
    argb |= ((long)alpha) << 24L;
    return argb;
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
