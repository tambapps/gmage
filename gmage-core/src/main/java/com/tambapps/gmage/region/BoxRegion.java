package com.tambapps.gmage.region;

public class BoxRegion extends Region {

  private final int startX;
  private final int startY;
  private final int width;
  private final int height;

  public BoxRegion(int startX, int startY, int width, int height) {
    this.startX = startX;
    this.startY = startY;
    this.width = width;
    this.height = height;
  }

  @Override
  public boolean contains(int x, int y) {
    return x >= startX && x < startX + width && y >= startY && y < startY + height;
  }
}
