package com.tambapps.gmage;

import com.tambapps.gmage.blur.Blur;
import com.tambapps.gmage.blur.PixelationBlur;
import com.tambapps.gmage.color.Color;
import com.tambapps.gmage.region.Region;
import com.tambapps.gmage.scaling.Scaling;
import com.tambapps.gmage.transformer.ColorTransformer;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.function.Consumer;

/**
 * Represent an image
 * (0, 0) is the top left corner. That means
 * X-axis goes from left to right
 * Y-axis goes from top to bottom
 */
@EqualsAndHashCode
public abstract class AbstractGmage {

  public abstract int getHeight();
  public abstract int getWidth();
  
  public int getSize() {
    return getWidth() * getHeight();
  }
  /**
   * Get the i-th pixels (1-D)
   *
   * @param i the index
   * @return the i-th pixel
   */
  public Color getAt(Number i) {
    return getAt(i.intValue());
  }

  /**
   * Get the i-th pixels (1-D)
   *
   * @param i the index
   * @return the i-th pixel
   */
  public abstract Color getAt(int i);

  /**
   * Get the pixel at coordinates (x, y)
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return the pixel at coordinates (x, y)
   */
  public Color getAt(Number x, Number y) {
    return getAt(x.intValue(), y.intValue());
  }

  /**
   * Get the pixel at coordinates (x, y)
   *
   * @param x the x coordinate
   * @param y the y coordinate
   * @return the pixel at coordinates (x, y)
   */
  public abstract Color getAt(int x, int y);

  /**
   * Modify the pixel at coordinates (xy[0], xy[1]). A list was used for xy coordinates to make this
   * method groovy friendly e.g: gmage[x,y] = 0xffffff
   *
   * @param xy    a list of two elements: x and y coordinates
   * @param value the color to put at this coordinate
   */
  public void putAt(List<Number> xy, Color value) {
    putColor(xy.get(0).intValue(), xy.get(1).intValue(), value);
  }

  /**
   * Modify the pixel at coordinates (xy[0], xy[1]). A list was used for xy coordinates to make this
   * method groovy friendly e.g: gmage[x,y] = 0xffffff
   *
   * @param xy    a list of two elements: x and y coordinates
   * @param value the ARGB color to put at this coordinate
   */
  public void putAt(List<Number> xy, Number value) {
    putAt(xy, new Color(value));
  }

  /**
   * Modify the pixel at coordinates (xy[0], xy[1]). A list was used for xy coordinates to make this
   * method groovy friendly e.g: gmage[x,y] = 0xffffff
   *
   * @param xy    a list of two elements: x and y coordinates
   * @param value the RGB color to put at this coordinate
   */
  public void putAt(List<Number> xy, Integer value) {
    putAt(xy, new Color(value));
  }

  /**
   * Returns a copy of this gmage
   *
   * @return a copy of this gmage
   */
  public abstract <T extends AbstractGmage> T copy();

  /**
   * Modify the i-th pixel (1-D)
   *
   * @param oneDIndex the 1-D index
   * @param value     the color to put
   */
  public void putAt(Integer oneDIndex, Color value) {
    putAt(oneDIndex.intValue(), value);
  }

  /**
   * Modify the i-th pixel (1-D)
   *
   * @param oneDIndex the 1-D index
   * @param value     the ARGB color to put
   */
  public void putAt(Integer oneDIndex, Number value) {
    putAt(oneDIndex.intValue(), new Color(value));
  }

  /**
   * Modify the i-th pixel (1-D)
   *
   * @param oneDIndex the 1-D index
   * @param value     the RGB color to put
   */
  public void putAt(Integer oneDIndex, Integer value) {
    putAt(oneDIndex.intValue(), new Color(value));
  }

  /**
   * Modify the i-th pixel (1-D)
   *
   * @param oneDIndex the 1-D index
   * @param value     the ARGB color to put
   */
  public void putAt(int oneDIndex, Number value) {
    putAt(oneDIndex, new Color(value));
  }

  /**
   * Modify the i-th pixel (1-D)
   *
   * @param oneDIndex the 1-D index
   * @param value     the RGB color to put
   */
  public void putAt(int oneDIndex, Integer value) {
    putAt(oneDIndex, new Color(value));
  }

  /**
   * Modify the i-th pixel (1-D)
   *
   * @param oneDIndex the 1-D index
   * @param value     the color to put
   */
  public abstract void putAt(int oneDIndex, Color value);

  /**
   * Modify the pixel at coordinates (x, y)
   *
   * @param x     the x coordinate
   * @param y     the y coordinate
   * @param value the color to put at this coordinate
   */
  public abstract void putColor(int x, int y, Color value);

  /**
   * copy this gmage into the one passed in parameter. Both gmage must have the same size
   * to be able to perform this operation
   *
   * @param gmage the target gmage to copy into
   */
  public void set(AbstractGmage gmage) {
    if (getWidth() != gmage.getWidth() || getHeight() != gmage.getHeight()) {
      throw new IllegalArgumentException("Cannot set from gmage with different size");
    }
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        putColor(x, y, gmage.getAt(x, y));
      }
    }
  }

  /**
   * Apply the color transformer in all pixels of this gmage
   *
   * @param transformer the color transformer
   */
  public void apply(ColorTransformer transformer) {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        putColor(x, y, transformer.apply(getAt(x, y)));
      }
    }
  }

  /**
   * Apply the color transformer in pixels belonging to the provided region
   *
   * @param transformer the color transformer
   * @param region      the region in which to apply the color transformer
   */
  public void apply(ColorTransformer transformer, Region region) {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        if (region.contains(x, y)) {
          putColor(x, y, transformer.apply(getAt(x, y)));
        }
      }
    }
  }

  /**
   * Apply the color transformer in pixels outside to the provided region
   *
   * @param transformer the color transformer
   * @param region      the region in which not to apply the color transformer
   */
  public void applyOutside(ColorTransformer transformer, Region region) {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        if (!region.contains(x, y)) {
          putColor(x, y, transformer.apply(getAt(x, y)));
        }
      }
    }
  }

  /**
   * Returns a new gmage scaled by the provided factor, both getWidth() and getHeight(). Bilinear interpolation
   * is used for scaling
   *
   * @param factor the factor
   * @return a scaled gmage
   */
  public AbstractGmage scaledBy(Number factor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, factor, factor);
  }

  /**
   * Returns a new gmage scaled by the provided factor, both getWidth() and getHeight(). Bilinear interpolation
   * is used for scaling
   *
   * @param factor the factor
   * @return a scaled gmage
   */
  public AbstractGmage scaledBy(float factor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, factor, factor);
  }


  /**
   * Returns a new gmage scaled by the provided factor (both getWidth() and getHeight()), with the given algorithm
   *
   * @param scaling the scaling algorithm
   * @param factor  the factor
   * @return a scaled gmage
   */
  public AbstractGmage scaledBy(Scaling scaling, Float factor) {
    return scaledBy(scaling, factor.floatValue());
  }

  /**
   * Returns a new gmage scaled by the provided factor (both getWidth() and getHeight()), with the given algorithm
   *
   * @param scaling the scaling algorithm
   * @param factor  the factor
   * @return a scaled gmage
   */
  public AbstractGmage scaledBy(Scaling scaling, float factor) {
    return scaledBy(scaling, factor, factor);
  }

  /**
   * Returns a new gmage scaled by the provided factors. Bilinear interpolation
   * is used for scaling
   *
   * @param widthFactor  the getWidth() factor
   * @param heightFactor the getHeight() factor
   * @return a scaled gmage
   */
  public AbstractGmage scaledBy(float widthFactor, float heightFactor) {
    return scaled(Scaling.BILINEAR_INTERPOLATION, (int) (widthFactor * getWidth()),
        (int) (heightFactor * getHeight()));
  }

  /**
   * Returns a new gmage scaled by the provided factors. Bilinear interpolation
   * is used for scaling
   *
   * @param widthFactor  the getWidth() factor
   * @param heightFactor the getHeight() factor
   * @return a scaled gmage
   */
  public AbstractGmage scaledBy(Number widthFactor, Number heightFactor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, widthFactor.floatValue(),
        heightFactor.floatValue());
  }

  /**
   * Returns a new gmage scaled by the provided factors, with the given algorithm
   *
   * @param scaling      the scaling algorithm
   * @param widthFactor  the getWidth() factor
   * @param heightFactor the getHeight() factor
   * @return a scaled gmage
   */
  public AbstractGmage scaledBy(Scaling scaling, Number widthFactor, Number heightFactor) {
    return scaledBy(scaling, widthFactor.floatValue(), heightFactor.floatValue());
  }

  /**
   * Returns a new gmage scaled by the provided factors, with the given algorithm
   *
   * @param scaling      the scaling algorithm
   * @param widthFactor  the getWidth() factor
   * @param heightFactor the getHeight() factor
   * @return a scaled gmage
   */
  public AbstractGmage scaledBy(Scaling scaling, float widthFactor, float heightFactor) {
    return scaled(scaling, (int) (widthFactor * getWidth()), (int) (heightFactor * getHeight()));
  }

  /**
   * Returns a new gmage scaled to the given dimension using bilinear interpolation
   *
   * @param newWidth  the getWidth() of the returned gmage
   * @param newHeight the getHeight() of the returned gmage
   * @return a scaled gmage
   */
  public AbstractGmage scaled(Number newWidth, Number newHeight) {
    return scaled(Scaling.BILINEAR_INTERPOLATION, newWidth.intValue(), newHeight.intValue());
  }

  /**
   * Returns a new gmage scaled to the given dimension using bilinear interpolation
   *
   * @param newWidth  the getWidth() of the returned gmage
   * @param newHeight the getHeight() of the returned gmage
   * @return a scaled gmage
   */
  public AbstractGmage scaled(int newWidth, int newHeight) {
    return Scaling.BILINEAR_INTERPOLATION.scale(this, newWidth, newHeight);
  }

  /**
   * Returns a new gmage scaled to the given dimension, with the given algorithm
   *
   * @param scaling   the scaling algorithm
   * @param newWidth  the getWidth() of the returned gmage
   * @param newHeight the getHeight() of the returned gmage
   * @return a scaled gmage
   */
  public AbstractGmage scaled(Scaling scaling, Number newWidth, Number newHeight) {
    return scaled(scaling, newWidth.intValue(), newHeight.intValue());
  }

  /**
   * Returns a new gmage scaled to the given dimension, with the given algorithm
   *
   * @param scaling   the scaling algorithm
   * @param newWidth  the getWidth() of the returned gmage
   * @param newHeight the getHeight() of the returned gmage
   * @return a scaled gmage
   */
  public AbstractGmage scaled(Scaling scaling, int newWidth, int newHeight) {
    return scaling.scale(this, newWidth, newHeight);
  }

  public abstract <T extends AbstractGmage> T newInstance(int width, int height);
  public abstract <T extends AbstractGmage> T newInstance(int width, int height, Color[] pixels);

  /**
   * Returns a new gmage resulting of the rotation to 90 degrees clockwise of this gmage
   *
   * @return a 90 clockwise rotated gmage
   */
  public <T extends AbstractGmage> T rotated90ClockWise() {
    T gmage = newInstance(getHeight(), getWidth());
    for (int y = 0; y < gmage.getHeight(); y++) {
      for (int x = 0; x < gmage.getWidth(); x++) {
        gmage.putColor(x, y, getAt(y, gmage.getWidth() - 1 - x));
      }
    }
    return gmage;
  }

  /**
   * Returns a new gmage resulting of the rotation to 90 degrees counter clockwise of this gmage
   *
   * @return a 90 counter clockwise rotated gmage
   */
  public AbstractGmage rotated90CounterClockWise() {
    AbstractGmage gmage = newInstance(getHeight(), getWidth());
    for (int y = 0; y < gmage.getHeight(); y++) {
      for (int x = 0; x < gmage.getWidth(); x++) {
        gmage.putColor(x, y, getAt(gmage.getHeight() - 1 - y, x));
      }
    }
    return gmage;
  }

  /**
   * Returns a new gmage resulting of the rotation to 180 degrees of this gmage
   *
   * @return a 180 rotated gmage
   */
  public AbstractGmage rotated180() {
    AbstractGmage gmage = newInstance(getWidth(), getHeight());
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        gmage.putColor(x, y, getAt(getWidth() - 1 - x, getHeight() - 1 - y));
      }
    }
    return gmage;
  }

  /**
   * Swaps pixels at coordinates (x1, y1) and (x2, y2)
   *
   * @param x1 the x coordinate of the first pixel to swap
   * @param y1 the y coordinate of the first pixel to swap
   * @param x2 the x coordinate of the second pixel to swap
   * @param y2 the y coordinate of the second pixel to swap
   */
  public void swap(int x1, int y1, int x2, int y2) {
    Color color = getAt(x1, y1);
    putColor(x1, y1, getAt(x2, y2));
    putColor(x2, y2, color);
  }

  /**
   * Swap all pixel along the Y axis at the horizontal center of the gmage.
   */
  public void mirrorX() {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth() / 2; x++) {
        swap(x, y, getWidth() - 1 - x, y);
      }
    }
  }

  /**
   * Swap all pixel along the X axis at the vertical center of the gmage.
   */
  public void mirrorY() {
    for (int y = 0; y < getHeight() / 2; y++) {
      for (int x = 0; x < getWidth(); x++) {
        swap(x, y, x, getHeight() - 1 - y);
      }
    }
  }

  /**
   * Returns a new gmage resulting of the mirrorX of this one
   *
   * @return a mirrored X gmage
   */
  public AbstractGmage mirroredX() {
    AbstractGmage gmage = newInstance(getWidth(), getHeight());
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        gmage.putAt(gmage.getIndex(x, y), getAt(getWidth() - 1 - x, y));
      }
    }
    return gmage;
  }

  /**
   * Returns a new gmage resulting of the mirrorY of this one
   *
   * @return a mirrored Y gmage
   */
  public AbstractGmage mirroredY() {
    AbstractGmage gmage = newInstance(getWidth(), getHeight());
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        gmage.putColor(x, y, getAt(x, getHeight() - 1 - y));
      }
    }
    return gmage;
  }

  static int getIndex(int x, int y, int width) {
    return y * width + x;
  }

  protected int getIndex(int x, int y) {
    return y * getWidth() + x;
  }

  private int checkedIndex(int x, int y) {
    if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
      throw new IllegalArgumentException(String
          .format("Indexes (%d, %d) are not within bounds (getWidth()=%d, getHeight()=%d)", x, y, getWidth(),
              getHeight()));
    }
    return getIndex(x, y);
  }

  protected int checkedIndex(int i) {
    if (i < 0 || i >= getSize()) {
      throw new IllegalArgumentException(
          String.format("Index %d is not within bounds (length=%d)", i, getSize()));
    }
    return i;
  }

  public AbstractGmage padded(Number padLeft, Number padTop, Number padRight, Number padBottom) {
    return padded(padLeft.intValue(), padTop.intValue(), padRight.intValue(), padBottom.intValue(),
        Color.BLACK);
  }

  public AbstractGmage padded(Number padLeft, Number padTop, Number padRight, Number padBottom,
      Color backgroundColor) {
    return padded(padLeft.intValue(), padTop.intValue(), padRight.intValue(), padBottom.intValue(),
        backgroundColor);
  }

  public AbstractGmage padded(int padLeft, int padTop, int padRight, int padBottom) {
    return padded(padLeft, padTop, padRight, padBottom, Color.BLACK);
  }

  public void pixelize(Number pixelSize) {
    pixelize(pixelSize.intValue());
  }

  public void pixelize(int pixelSize) {
    new PixelationBlur(pixelSize).applyOn(this);
  }

  public void pixelize(Number pixelSize, Region region) {
    pixelize(pixelSize.intValue(), region);
  }

  public void pixelize(int pixelSize, Region region) {
    new PixelationBlur(pixelSize).applyOn(this, region);
  }

  public AbstractGmage padded(int padLeft, int padTop, int padRight, int padBottom, Color backgroundColor) {
    AbstractGmage gmage =
        newInstance(getWidth() + padLeft + padRight, getHeight() + padTop + padBottom);
    gmage.fill(backgroundColor);
    copyInto(gmage, padLeft, padTop);
    return gmage;
  }

  public void fill(Color color) {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        putColor(x, y, color);
      }
    }
  }

  public void copyInto(AbstractGmage gmage, Number startX, Number startY) {
    copyInto(gmage, startX.intValue(), startY.intValue());
  }

  public void copyInto(AbstractGmage gmage, int startX, int startY) {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        gmage.putColor(x + startX, y + startY, getAt(x, y));
      }
    }
  }

  public AbstractGmage blurred(Blur blur) {
    return blur.apply(this);
  }

  public void forEachPixel(Consumer<Color> consumer) {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        consumer.accept(getAt(x, y));
      }
    }
  }

  public void forEachPixel(Consumer<Color> consumer, Region region) {
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        if (region.contains(x, y)) {
          consumer.accept(getAt(x, y));
        }
      }
    }
  }

  // groovy operator
  public void leftShift(ColorTransformer transformer) {
    apply(transformer);
  }

  public AbstractGmage and(Number number) {
    return and(new Color(number));
  }

  public AbstractGmage and(Integer number) {
    return and(new Color(number));
  }

  public AbstractGmage and(Color color) {
    AbstractGmage gmage = copy();
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        gmage.putColor(x, y, getAt(x, y).and(color));
      }
    }
    return gmage;
  }

  public AbstractGmage or(Number number) {
    return or(new Color(number));
  }

  public AbstractGmage or(Integer number) {
    return or(new Color(number));
  }

  public AbstractGmage or(Color color) {
    AbstractGmage gmage = copy();
    for (int y = 0; y < getHeight(); y++) {
      for (int x = 0; x < getWidth(); x++) {
        gmage.putColor(x, y, getAt(x, y).or(color));
      }
    }
    return gmage;
  }

  public AbstractGmage negative() {
    AbstractGmage gmage = copy();
    gmage.apply(Color::negative);
    return gmage;
  }

  @Override
  public String toString() {
    return String.format("Gmage (%d * %d)", getWidth(), getHeight());
  }
}
