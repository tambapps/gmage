package com.tambapps.gmage.bi;

import com.tambapps.gmage.Gmage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImageUtils {

  public static BufferedImage toBufferedImage(Gmage gmage) {
    return toBufferedImage(gmage, BufferedImage.TYPE_INT_RGB);
  }

    public static BufferedImage toBufferedImage(Gmage gmage, int imageType) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    BufferedImage image = new BufferedImage(width, height, imageType);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        com.tambapps.gmage.color.Color color = gmage.getAt(x, y);
        image.setRGB(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).getRGB());
      }
    }
    return image;
  }

}
