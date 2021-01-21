package com.tambapps.gmage.region;

/**
 * Represents a circle region from origin (centerX, centerY) and the radius
 */
public class CircleRegion extends Region {

  private final int centerX;
  private final int centerY;
  private final float radius2;

  /**
   * Constructs a circle region from origin (centerX, centerY) and the radius
   * @param centerX the centerX
   * @param centerY the centerY
   * @param radius the radius
   */
  public CircleRegion(int centerX, int centerY, float radius) {
    this.centerX = centerX;
    this.centerY = centerY;
    this.radius2 = radius * radius;
  }

  @Override
  public boolean contains(int x, int y) {
    float distance2 = (x - centerX) * (x - centerX) + (y - centerY) * (y - centerY);
    return distance2 <= radius2;
  }
}
