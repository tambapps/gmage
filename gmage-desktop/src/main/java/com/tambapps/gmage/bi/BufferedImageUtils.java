package com.tambapps.gmage.bi;

import com.tambapps.gmage.Gmage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Class containing useful functions to convert BufferedImage from/to Gmage
 */
public class BufferedImageUtils {

  /**
   * Converts a Gmage to a RGB BufferedImage
   *
   * @param gmage the gmage to converted
   * @return the converted buffered image
   */
  public static BufferedImage toBufferedImage(Gmage gmage) {
    return toBufferedImage(gmage, BufferedImage.TYPE_INT_RGB);
  }

  /**
   * Converts a Gmage to a BufferedImage with the provided image type
   *
   * @param gmage     the gmage to converted
   * @param imageType the BufferedImage image type
   * @return the converted buffered image
   */
  public static BufferedImage toBufferedImage(Gmage gmage, int imageType) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    BufferedImage image = new BufferedImage(width, height, imageType);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        com.tambapps.gmage.color.Color color = gmage.getAt(x, y);
        image.setRGB(x, y,
            new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .getRGB());
      }
    }
    return image;
  }

}
