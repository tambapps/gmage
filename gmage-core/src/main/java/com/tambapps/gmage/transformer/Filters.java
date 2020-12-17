package com.tambapps.gmage.transformer;

import com.tambapps.gmage.pixel.Pixel;

public class Filters {

  static final PixelTransformer GRAY_SCALE = (Pixel p) -> {
    int gray = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
    p.setRGB(gray, gray, gray);
  };

}
