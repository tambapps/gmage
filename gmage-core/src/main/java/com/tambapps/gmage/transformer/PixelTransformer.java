package com.tambapps.gmage.transformer;

import com.tambapps.gmage.pixel.Pixel;

public interface PixelTransformer {

  Pixel apply(Pixel pixel);

}