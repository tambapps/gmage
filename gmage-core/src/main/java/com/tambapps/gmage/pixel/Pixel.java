package com.tambapps.gmage.pixel;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the pixel of an image.
 * Alpha, red, green, blue value go from 0 to 255 included
 */
public class Pixel {

  // note that white is actually -1
  public static final int WHITE = 0xffffff;

  public static Pixel black() {
    return new Pixel(0);
  }

  public static Pixel of(int rgb) {
    return new Pixel(rgb);
  }

  public static long toARGB(int alpha, int red, int green, int blue) {
    long color = 0;
    color |= (long) alpha << 24;
    color |= (long) red << 16;
    color |= (long) green << 8;
    color |= blue;
    return color;
  }

  private int rgb;
  @Getter
  private int alpha;

  private Pixel(int rgb, int alpha) {
    this.rgb = rgb;
    this.alpha = alpha;
  }

  private Pixel(int rgb) {
    this(rgb, 255);
  }

  public void setARGB(Number value) {
    long extractedAlpha = value.longValue() & 0xff000000;
    this.alpha = (int) extractedAlpha;
    this.rgb = (int) (value.longValue() & 0xffffff);
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
    rgb = toRGB(red, getGreen(), getBlue());
  }

  public void setGreen(int green) {
    rgb = toRGB(getRed(), green, getBlue());
  }

  public void setBlue(int blue) {
    rgb = toRGB(getRed(), getGreen(), blue);
  }

  public void setAlpha(Number alpha) {
    this.alpha = alpha.intValue() & 255;
  }

  public void setAlpha(int alpha) {
    this.alpha = alpha & 255;
  }

  public void setRGB(Number red, Number green, Number blue) {
    rgb = toRGB(red.intValue(), green.intValue(), blue.byteValue());
  }

  public void setRGB(Number number) {
    rgb = number.intValue() & WHITE;
  }

  public void setRGB(int number) {
    rgb = number & WHITE;
  }

  public void setRGB(int red, int green, int blue) {
    rgb = toRGB(red, green, blue);
  }

  public void setARGB(Number alpha, Number red, Number green, Number blue) {
    this.alpha = alpha.intValue() & 255;
    this.rgb = toRGB(red.intValue(), green.intValue(), blue.intValue());
  }

  public void setARGB(int alpha, int red, int green, int blue) {
    this.alpha = alpha & 255;
    this.rgb = toRGB(red, green, blue);
  }

  public void set(Pixel value) {
    rgb = value.rgb;
    alpha = value.alpha;
  }

  public int toRGB(int red, int green, int blue) {
    int color = 0;
    color |= red << 16;
    color |= green << 8;
    color |= blue;
    return color;
  }

  public long getARGB() {
    long argb = this.rgb;
    argb |= ((long)alpha) << 24L;
    return argb;
  }

  public int getRGB() {
    return rgb;
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
