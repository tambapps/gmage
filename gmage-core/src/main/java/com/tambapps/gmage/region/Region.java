package com.tambapps.gmage.region;

import com.tambapps.gmage.Gmage;

public abstract class Region {

  public static Region whole(Gmage gmage) {
    return new BoxRegion(0, 0, gmage.getWidth(), gmage.getHeight());
  }

  public abstract boolean contains(int x, int y);

}
