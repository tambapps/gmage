package com.tambapps.gmage.scaling;

import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;

class ScalingAlgorithms {

  public static Gmage nearestNeighbour(Gmage source, int targetWidth, int targetHeight) {
    Color[] pixels = new Color[targetWidth * targetHeight];
    int sourceWidth = source.getWidth();
    int sourceHeight = source.getHeight();

    // hack to avoid using floats and still use ints
    int widthRatio = ((sourceWidth<<16)/targetWidth);
    int heightRatio = ((sourceHeight<<16)/targetHeight);

    int electedX;
    int electedY;
    for (int y = 0; y < targetHeight; y++) {
      for (int x = 0; x < targetWidth; x++) {
        // MAGIC
        electedX = ((x*widthRatio)>>16);
        electedY = ((y*heightRatio)>>16);
        pixels[y * targetWidth + x] = source.getAt(electedX, electedY);
      }
    }
    return new Gmage(targetWidth, targetHeight, pixels);
  }

  public static Gmage bilinearInterpolation(Gmage source, int targetWidth, int targetHeight) {
    Color[] pixels = new Color[targetWidth*targetHeight];
    int sourceWidth = source.getWidth();
    int sourceHeight = source.getHeight();
    Color a, b, c, d;
    int x, y, index ;
    float widthRatio = ((float)(sourceWidth-1))/targetWidth;
    float heightRatio = ((float)(sourceHeight-1))/targetHeight;
    float xDiff, yDiff;
    int offset = 0;
    for (int i = 0; i < targetHeight; i++) {
      for (int j = 0; j < targetWidth; j++) {
        x = (int)(widthRatio * j) ;
        y = (int)(heightRatio * i) ;
        xDiff = (widthRatio * j) - x ;
        yDiff = (heightRatio * i) - y ;
        index = (y*sourceWidth+x) ;
        a = source.getAt(index);
        b = source.getAt(index + 1) ;
        c = source.getAt(index + sourceWidth);
        d = source.getAt(index + sourceWidth + 1);

        // Color = A*(1-w)*(1-h) + B*(w)*(1-h) + C*(h)*(1-w) + D*(w*h)
        pixels[offset++] = new Color(bilinearTransform(a.getAlpha(), b.getAlpha(), c.getAlpha(), d.getAlpha(), xDiff, yDiff),
            bilinearTransform(a.getRed(), b.getRed(), c.getRed(), d.getRed(), xDiff, yDiff),
            bilinearTransform(a.getGreen(), b.getGreen(), c.getGreen(), d.getGreen(), xDiff, yDiff),
            bilinearTransform(a.getBlue(), b.getBlue(), c.getBlue(), d.getBlue(), xDiff, yDiff));
      }
    }
    return new Gmage(targetWidth, targetHeight, pixels);
  }

  private static int bilinearTransform(int a, int b, int c, int d, float x_diff, float y_diff) {
    return (int) (a*(1-x_diff)*(1-y_diff) + b*(x_diff)*(1-y_diff) +
        c*(y_diff)*(1-x_diff)   + d*(x_diff*y_diff));
  }
}
