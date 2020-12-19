package com.tambapps.gmage.scaling;

import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;

class ScalingAlgorithms {

  public static Gmage nearestNeighbour(Gmage source, int newWidth, int newHeight) {
    Color[] pixels = new Color[newWidth * newHeight];
    int sourceWidth = source.getWidth();
    int sourceHeight = source.getHeight();

    // hack to avoid using floats and still use ints
    int widthRatio = ((sourceWidth<<16)/newWidth);
    int heightRatio = ((sourceHeight<<16)/newHeight);

    int electedX;
    int electedY;
    for (int y = 0; y < newHeight; y++) {
      for (int x = 0; x < newWidth; x++) {
        // MAGIC
        electedX = ((x*widthRatio)>>16);
        electedY = ((y*heightRatio)>>16);
        pixels[y * newWidth + x] = source.getAt(electedX, electedY);
      }
    }
    return new Gmage(newWidth, newHeight, pixels);
  }

}
