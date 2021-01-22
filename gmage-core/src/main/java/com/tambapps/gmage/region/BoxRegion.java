package com.tambapps.gmage.region;

/**
 * Represents a box region from the left top corner (startX, startY) to the right bottom corner
 * (startX + width, startY + height)
 */
public class BoxRegion extends Region {

  private final int startX;
  private final int startY;
  private final int width;
  private final int height;

  /**
   * Constructs a box region from the left top corner (startX, startY) to the right bottom corner
   * (startX + width, startY + height)
   *
   * @param startX the startX
   * @param startY the startY
   * @param width  the width
   * @param height the height
   */
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
