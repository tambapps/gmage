package com.tambapps.gmage.blur;

import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.region.Region;

// TODO implement Gaussian Blur
// TODO allow blurring on a surface


/**
 * Interface representing a bluring operation
 */
public interface Blur {

  /**
   * Creates a copy of the gmage supplied and apply the
   * blur on it. the gmage passed will not be modified
   *
   * @param gmage the gmage to blur
   * @return the blurred copy
   */
  Gmage apply(Gmage gmage);

  /**
   * Apply directly the blur on the supplied gmage
   * Note that not all Blur supports this operation
   *
   * @param gmage the gmage to apply the blur on
   */
  default void applyOn(Gmage gmage) {
    throw new UnsupportedOperationException(getClass().getSimpleName() +
        "Cannot directly apply the blur on a gmage. (Use the apply function)");
  }

  /**
   * Apply directly the blur on the supplied gmage, in the given region
   * Note that not all Blur supports this operation
   *
   * @param gmage  the gmage to apply the blur on
   * @param region the region in which to apply the blur
   */
  default void applyOn(Gmage gmage, Region region) {
    throw new UnsupportedOperationException(getClass().getSimpleName() +
        "Cannot directly apply the blur on a gmage. (Use the apply function)");
  }
}
