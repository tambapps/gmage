package com.tambapps.gmage.bi;

import com.tambapps.gmage.Gmage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

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
  public static BufferedImage fromGmage(Gmage gmage) {
    return fromGmage(gmage, BufferedImage.TYPE_INT_RGB);
  }

  /**
   * Converts a Gmage to a BufferedImage with the provided image type
   *
   * @param gmage     the gmage to converted
   * @param imageType the BufferedImage image type
   * @return the converted buffered image
   */
  public static BufferedImage fromGmage(Gmage gmage, int imageType) {
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

  /**
   * Converts a BufferedImage to a Gmage
   *
   * @param image the buffered image to convert
   * @return the converted gmage
   */
  public static Gmage toGmage(BufferedImage image) {
    Raster raster = image.getData();
    int nbChannels = raster.getNumDataElements();
    if (nbChannels == 4) {
      return decodeRGBAImage(image.getData());
    }
    return decodeRGBImage(image);
  }

  private static Gmage decodeRGBImage(BufferedImage image) {
    int width = image.getWidth();
    int height = image.getHeight();
    com.tambapps.gmage.color.Color[] colors = new com.tambapps.gmage.color.Color[width * height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        colors[y * width + x] = new com.tambapps.gmage.color.Color(image.getRGB(x, y));
      }
    }
    return new Gmage(width, height, colors);
  }

  private static Gmage decodeRGBAImage(Raster raster) {
    int[] pixel = new int[4];
    int width = raster.getWidth();
    int height = raster.getHeight();
    com.tambapps.gmage.color.Color[] colors = new com.tambapps.gmage.color.Color[width * height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        raster.getPixel(x, y, pixel);
        colors[y * width + x] =
            new com.tambapps.gmage.color.Color(pixel[3], pixel[0], pixel[1], pixel[2]);
      }
    }
    return new Gmage(width, height, colors);
  }
}
