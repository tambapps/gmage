package com.tambapps.gmage.pixel;

import lombok.Data;

/**
 * Represents the pixel of an image
 */
@Data // TODO ne stocker qu'un int argb et faire les conversion quand on requete r,g,b ou a
public class Pixel {
  private int alpha = 0xff;
  private int red;
  private int green;
  private int blue;

  public void setARGB(Number value) {
    // TODO
  }

  public void setRGB(Number red, Number green, Number blue) {
    this.red = red.intValue();
    this.green = green.intValue();
    this.blue = blue.intValue();
  }

  public void setARGB(Number alpha, Number red, Number green, Number blue) {
    this.alpha = alpha.intValue();
    this.red = red.intValue();
    this.green = green.intValue();
    this.blue = blue.intValue();
  }

  public void set(Pixel value) {
    this.alpha = value.alpha;
    this.red = value.red;
    this.green = value.green;
    this.blue = value.blue;
  }

  public int toARGB() {
    return (alpha & 255) << 24 | (red & 255) << 16 | (green & 255) << 8 | (blue & 255);
  }
}
