package com.tambapps.gmage.region;

import com.tambapps.gmage.AbstractGmage;

/**
 * Represents a region of a Gmage
 */
public abstract class Region {

  /**
   * Returns a region representing the whole gmage
   *
   * @param gmage the gmage
   * @return a region representing the whole gmage
   */
  public static Region whole(AbstractGmage gmage) {
    return new BoxRegion(0, 0, gmage.getWidth(), gmage.getHeight());
  }

  /**
   * Returns whether the pixel at coordinates (x,y) is contained by this region
   *
   * @param x the pixel x
   * @param y the pixel y
   * @return whether the pixel at coordinates (x,y) is contained by this region
   */
  public abstract boolean contains(int x, int y);

}
