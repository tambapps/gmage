package com.tambapps.gmage.scaling;

import com.tambapps.gmage.AbstractGmage;

/**
 * Scaling algorithms
 */
public enum Scaling {
  // fast and simple
  NEAREST_NEIGHBOR {
    @Override
    public AbstractGmage scale(AbstractGmage original, int newWidth, int newHeight) {
      return ScalingAlgorithms.nearestNeighbour(original, newWidth, newHeight);
    }
  },
  // better than nearest neighbor in result
  BILINEAR_INTERPOLATION {
    @Override
    public AbstractGmage scale(AbstractGmage original, int newWidth, int newHeight) {
      return ScalingAlgorithms.bilinearInterpolation(original, newWidth, newHeight);
    }
  }

  // Bicubic interpolation, much slower but better
  ;

  /**
   * returned a scaled copy of the original image
   *
   * @param original  the original image
   * @param newWidth  the width to use when scaling
   * @param newHeight the height to use when scaling
   * @return a scaling image having dimension (newWidth, newHeight)
   */
  public AbstractGmage scale(AbstractGmage original, int newWidth, int newHeight) {
    throw new RuntimeException("The dev didn't override the method :(");
  }
}
