package com.tambapps.gmage.pixel;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Represents the pixel of an image.
 * Alpha, red, green, blue value go from 0 to 255 included
 */
@AllArgsConstructor
@NoArgsConstructor
public class Pixel {

  // note that white is actually -1
  public static final int WHITE = 0xffffffff;

  private int argb = 0xff000000;

  public void setARGB(Number value) {
    this.argb = value.intValue();
  }

  public int getAlpha() {
    return (argb >> 24) & 255;
  }

  public int getRed() {
    return (argb >> 16) & 255;
  }

  public int getGreen() {
    return (argb >> 8) & 255;
  }

  public int getBlue() {
    return argb & 255;
  }

  public void setAlpha(int alpha) {
    argb = toARGB(alpha, getRed(), getGreen(), getBlue());
  }

  public void setRed(int red) {
    argb = toARGB(getAlpha(), red, getGreen(), getBlue());
  }

  public void setGreen(int green) {
    argb = toARGB(getAlpha(), getRed(), green, getBlue());
  }

  public void setBlue(int blue) {
    argb = toARGB(getAlpha(), getRed(), getGreen(), blue);
  }

  public void setRGB(Number red, Number green, Number blue) {
    argb = toARGB(getAlpha(), red.intValue(), green.intValue(), blue.byteValue());
  }

  public void setRGB(Number number) {
    argb = 0xff000000 | number.intValue();
  }

  public void setRGB(int number) {
    argb = 0xff000000 | number;
  }

  public void setRGB(int red, int green, int blue) {
    argb = toARGB(getAlpha(), red, green, blue);
  }

  public void setARGB(Number alpha, Number red, Number green, Number blue) {
    argb = toARGB(alpha.intValue(), red.intValue(), green.intValue(), blue.intValue());
  }

  public void setARGB(int alpha, int red, int green, int blue) {
    argb = toARGB(alpha, red, green, blue);
  }

  public void set(Pixel value) {
    argb = value.argb;
  }

  public int toARGB(int alpha, int red, int green, int blue) {
    int color = 0;
    color |= alpha << 24;
    color |= red << 16;
    color |= green << 8;
    color |= blue;
    return color;
  }

  public int getARGB() {
    return argb;
  }

  public int getRGB() {
    int rgb = getRed();
    rgb = (rgb << 8) + getGreen();
    rgb = (rgb << 8) + getBlue();
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
