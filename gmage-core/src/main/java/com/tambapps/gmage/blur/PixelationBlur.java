package com.tambapps.gmage.blur;

import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;
import com.tambapps.gmage.region.Region;

/**
 * Implementation of a pixelation effect blur
 */
public class PixelationBlur implements Blur {

  private final int pixelSize;

  /**
   * Construct a PixelationBlur with the given pixel size
   *
   * @param pixelSize the size of the pixels when blurring
   */
  public PixelationBlur(Number pixelSize) {
    this(pixelSize.intValue());
  }

  /**
   * Construct a PixelationBlur with the given pixel size
   *
   * @param pixelSize the size of the pixels when blurring
   */
  public PixelationBlur(int pixelSize) {
    if (pixelSize < 1) {
      throw new IllegalArgumentException("The pixel size must be greater than 0");
    }
    this.pixelSize = pixelSize;
  }

  /**
   * Apply on a new gmage this blur
   *
   * @param gmage the gmage to blur
   * @return the blurred gmage
   */
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

  @Override
  public void applyOn(Gmage gmage, Region region) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    for (int y = 0; y < height; y += pixelSize) {
      for (int x = 0; x < width; x += pixelSize) {
        if (region.contains(x, y)) {
          put(gmage, region, x, y);
        }
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
        pixels[(startY + y) * gmage.getWidth() + startX + x] = color;
      }
    }
  }

  private void put(Gmage gmage, Region region, int startX, int startY) {
    Color color = gmage.getAt(startX, startY);
    for (int y = 0; startY + y < gmage.getHeight() && y < pixelSize; y++) {
      for (int x = 0; startX + x < gmage.getWidth() && x < pixelSize; x++) {
        if (region.contains(startX + x, startY + y)) {
          gmage.putPixel(startX + x, startY + y, color);
        }
      }
    }
  }
}
