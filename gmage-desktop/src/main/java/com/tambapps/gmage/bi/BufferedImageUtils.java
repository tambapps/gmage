package com.tambapps.gmage.bi;

import com.tambapps.gmage.Gmage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BufferedImageUtils {

  public static BufferedImage toBufferedImage(Gmage gmage) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        com.tambapps.gmage.color.Color color = gmage.getAt(x, y);
        image.setRGB(x, y, new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).getRGB());
      }
    }
    return image;
  }

}
