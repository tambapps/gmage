package com.tambapps.gmage.pixel;

import lombok.Data;

/**
 * Represents the pixel of an image
 */
@Data // TODO ne stocker qu'un int argb et faire les conversion quand on requete r,g,b ou a
public class Pixel {
  private int a = 0xff;
  private int r;
  private int g;
  private int b;

  public void setARGB(Number value) {
    // TODO
  }

  public void setRGB(Number red, Number green, Number blue) {
    this.r = red.intValue();
    this.g = green.intValue();
    this.b = blue.intValue();
  }

  public void setARGB(Number alpha, Number red, Number green, Number blue) {
    this.a = alpha.intValue();
    this.r = red.intValue();
    this.g = green.intValue();
    this.b = blue.intValue();
  }

  public void set(Pixel value) {
    this.a = value.a;
    this.r = value.r;
    this.g = value.g;
    this.b = value.b;
  }

  public int toARGB() {
    return (a & 255) << 24 | (r & 255) << 16 | (g & 255) << 8 | (b & 255);
  }
}
