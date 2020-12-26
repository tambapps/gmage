package com.tambapps.gmage.blur;

import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;

public class PixelationBlur implements Blur {

  private final int pixelSize;

  public PixelationBlur(Number pixelSize) {
    this(pixelSize.intValue());
  }

  public PixelationBlur(int pixelSize) {
    if (pixelSize < 1) {
      throw new IllegalArgumentException("The pixel size must be greater than 0");
    }
    this.pixelSize = pixelSize;
  }

  @Override
  public Gmage apply(Gmage gmage) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    Color[] pixels = new Color[gmage.getHeight() * gmage.getWidth()];
    int y, x = 0;
    for (y = 0; y < height; y += pixelSize) {
      for (x = 0; x < width; x += pixelSize) {
        put(gmage, pixels, x, y);
      }
    }
    y = y - pixelSize + 1;
    x = x - pixelSize + 1;
    while (y < height) {
      while (x < width) {
        pixels[y * width + x] = gmage.getAt(x, y);
        x++;
      }
      y++;
    }
    return new Gmage(width, height, pixels);
  }

  @Override
  public void applyOn(Gmage gmage) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    for (int y = 0; y < height; y += pixelSize) {
      for (int x = 0; x < width; x += pixelSize) {
        put(gmage, x, y);
      }
    }
  }

  private void put(Gmage gmage, int startX, int startY) {
    Color color = gmage.getAt(startX, startY);
    for (int y = 0; startY + y < gmage.getHeight() && y < pixelSize; y++) {
      for (int x = 0; startX + x < gmage.getWidth() && x < pixelSize; x++) {
        gmage.putPixel(startX + x, startY + y, color);
      }
    }
  }

  private void put(Gmage gmage, Color[] pixels, int startX, int startY) {
    Color color = gmage.getAt(startX, startY);
    for (int y = 0; startY + y < gmage.getHeight() && y < pixelSize; y++) {
      for (int x = 0; startX + x < gmage.getWidth() && x < pixelSize; x++) {
        pixels[ (startY + y) * gmage.getWidth() + startX + x] = color;
      }
    }
  }
}
