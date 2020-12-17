package com.tambapps.gmage.transformer;

import com.tambapps.gmage.pixel.Pixel;

public class Filters {

  static final PixelTransformer GRAY_SCALE =  (Pixel p) ->  {
    int gray = (p.getR() + p.getG() + p.getB()) / 3;
    p.setR(gray);
    p.setG(gray);
    p.setB(gray);
  };

}
