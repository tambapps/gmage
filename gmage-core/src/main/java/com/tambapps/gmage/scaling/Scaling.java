package com.tambapps.gmage.scaling;

import com.tambapps.gmage.Gmage;

public enum Scaling {
  // fast and simple
  NEAREST_NEIGHBOR {
    @Override
    public Gmage scale(Gmage original, int newWidth, int newHeight) {
      return ScalingAlgorithms.nearestNeighbour(original, newWidth, newHeight);
    }
  }
  // Bilinear, better than nearest neighbor

  // Bicubic interpolation, much slower but better
  ;

  public Gmage scale(Gmage original, int newWidth, int newHeight) {
    throw new RuntimeException("The dev didn't override the method :(");
  }
}
