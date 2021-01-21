package com.tambapps.gmage.region;

import com.tambapps.gmage.Gmage;

/**
 * Represents a region of a Gmage
 */
public abstract class Region {

  public static Region whole(Gmage gmage) {
    return new BoxRegion(0, 0, gmage.getWidth(), gmage.getHeight());
  }

  /**
   * Returns whether the pixel at coordinates (x,y) is contained by this region
   * @param x the pixel x
   * @param y the pixel y
   * @return whether the pixel at coordinates (x,y) is contained by this region
   */
  public abstract boolean contains(int x, int y);

}
