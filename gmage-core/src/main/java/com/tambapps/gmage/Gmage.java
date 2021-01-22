package com.tambapps.gmage;

import com.tambapps.gmage.blur.Blur;
import com.tambapps.gmage.blur.PixelationBlur;
import com.tambapps.gmage.color.Color;
import com.tambapps.gmage.region.Region;
import com.tambapps.gmage.scaling.Scaling;
import com.tambapps.gmage.transformer.ColorTransformer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Represent an image
 * (0, 0) is the top left corner. That means
 * X-axis goes from left to right
 * Y-axis goes from top to bottom
 */
@EqualsAndHashCode
public class Gmage {

  @Getter final int width;
  @Getter final int height;
  final Color[] pixels;

  /**
   * Constructs a gmage with the given dimensions. The supplied pixels array must have length of
   * width * height. It represents the pixels in 1-D of this gmage
   *
   * @param width  the width of the gmage
   * @param height the height of the gmage
   * @param pixels the pixels of the gmage
   */
  public Gmage(int width, int height, Color[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = new Color[pixels.length];
    if (pixels.length != width * height) {
      throw new IllegalArgumentException("pixels should have a size of width * height");
    }
    System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
  }

  /**
   * Constructs a gmage with the given dimensions. All pixels are initialized black
   *
   * @param width  the width of the gmage
   * @param height the height of the gmage
   */
  public Gmage(int width, int height) {
    this(width, height, Color.BLACK);
  }

  /**
   * Constructs a gmage with the given dimensions. All pixels are initialized with the provided
   * default color
   *
   * @param width        the width of the gmage
   * @param height       the height of the gmage
   * @param defaultColor the color of all pixels
   */
  public Gmage(int width, int height, Color defaultColor) {
    this.width = width;
    this.height = height;
    this.pixels = new Color[width * height];
    Arrays.fill(pixels, defaultColor);
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
  public Color getAt(int i) {
    return pixels[checkedIndex(i)];
  }

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
  public Color getAt(int x, int y) {
    return pixels[checkedIndex(x, y)];
  }

  /**
   * Modify the pixel at coordinates (xy[0], xy[1]). A list was used for xy coordinates to make this
   * method groovy friendly => gmage[x,y] = 0xffffff
   *
   * @param xy    a list of two elements: x and y coordinates
   * @param value the color to put at this coordinate
   */
  public void putAt(List<Number> xy, Color value) {
    pixels[checkedIndex(xy.get(0).intValue(), xy.get(1).intValue())] = value;
  }

  /**
   * Modify the pixel at coordinates (xy[0], xy[1]). A list was used for xy coordinates to make this
   * method groovy friendly => gmage[x,y] = 0xffffff
   *
   * @param xy    a list of two elements: x and y coordinates
   * @param value the ARGB color to put at this coordinate
   */
  public void putAt(List<Number> xy, Number value) {
    putAt(xy, new Color(value));
  }

  /**
   * Modify the pixel at coordinates (xy[0], xy[1]). A list was used for xy coordinates to make this
   * method groovy friendly => gmage[x,y] = 0xffffff
   *
   * @param xy    a list of two elements: x and y coordinates
   * @param value the RGB color to put at this coordinate
   */
  public void putAt(List<Number> xy, Integer value) {
    putAt(xy, new Color(value));
  }

  /**
   * Returns a stream of all pixels
   *
   * @return a stream of all pixels
   */
  public Stream<Color> pixels() {
    return Arrays.stream(pixels);
  }

  /**
   * Returns a copy of this gmage
   *
   * @return a copy of this gmage
   */
  public Gmage copy() {
    return new Gmage(this.width, this.height, this.pixels);
  }

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
  public void putAt(int oneDIndex, Color value) {
    pixels[checkedIndex(oneDIndex)] = value;
  }

  /**
   * Modify the pixel at coordinates (x, y)
   *
   * @param x     the x coordinate
   * @param y     the y coordinate
   * @param value the color to put at this coordinate
   */
  public void putColor(int x, int y, Color value) {
    pixels[checkedIndex(x, y)] = value;
  }

  /**
   * copy this gmage into the one passed in parameter. Both gmage must have the same size
   * to be able to perform this operation
   *
   * @param gmage the target gmage to copy into
   */
  public void set(Gmage gmage) {
    if (width != gmage.width || height != gmage.height) {
      throw new IllegalArgumentException("Cannot set from gmage with different size");
    }
    System.arraycopy(gmage.pixels, 0, pixels, 0, pixels.length);
  }

  /**
   * Apply the color transformer in all pixels of this gmage
   *
   * @param transformer the color transformer
   */
  public void apply(ColorTransformer transformer) {
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = transformer.apply(this.pixels[i]);
    }
  }

  /**
   * Apply the color transformer in pixels belonging to the provided region
   *
   * @param transformer the color transformer
   * @param region      the region in which to apply the color transformer
   */
  public void apply(ColorTransformer transformer, Region region) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (region.contains(x, y)) {
          this.pixels[getIndex(x, y)] = transformer.apply(getAt(x, y));
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
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (!region.contains(x, y)) {
          this.pixels[getIndex(x, y)] = transformer.apply(getAt(x, y));
        }
      }
    }
  }

  /**
   * Returns a new gmage scaled by the provided factor, both width and height. Bilinear interpolation
   * is used for scaling
   *
   * @param factor the factor
   * @return a scaled gmage
   */
  public Gmage scaledBy(Number factor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, factor, factor);
  }

  /**
   * Returns a new gmage scaled by the provided factor, both width and height. Bilinear interpolation
   * is used for scaling
   *
   * @param factor the factor
   * @return a scaled gmage
   */
  public Gmage scaledBy(float factor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, factor, factor);
  }


  /**
   * Returns a new gmage scaled by the provided factor (both width and height), with the given algorithm
   *
   * @param scaling the scaling algorithm
   * @param factor  the factor
   * @return a scaled gmage
   */
  public Gmage scaledBy(Scaling scaling, Float factor) {
    return scaledBy(scaling, factor.floatValue());
  }

  /**
   * Returns a new gmage scaled by the provided factor (both width and height), with the given algorithm
   *
   * @param scaling the scaling algorithm
   * @param factor  the factor
   * @return a scaled gmage
   */
  public Gmage scaledBy(Scaling scaling, float factor) {
    return scaledBy(scaling, factor, factor);
  }

  /**
   * Returns a new gmage scaled by the provided factors. Bilinear interpolation
   * is used for scaling
   *
   * @param widthFactor  the width factor
   * @param heightFactor the height factor
   * @return a scaled gmage
   */
  public Gmage scaledBy(float widthFactor, float heightFactor) {
    return scaled(Scaling.BILINEAR_INTERPOLATION, (int) (widthFactor * width),
        (int) (heightFactor * height));
  }

  /**
   * Returns a new gmage scaled by the provided factors. Bilinear interpolation
   * is used for scaling
   *
   * @param widthFactor  the width factor
   * @param heightFactor the height factor
   * @return a scaled gmage
   */
  public Gmage scaledBy(Number widthFactor, Number heightFactor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, widthFactor.floatValue(),
        heightFactor.floatValue());
  }

  /**
   * Returns a new gmage scaled by the provided factors, with the given algorithm
   *
   * @param scaling      the scaling algorithm
   * @param widthFactor  the width factor
   * @param heightFactor the height factor
   * @return a scaled gmage
   */
  public Gmage scaledBy(Scaling scaling, Number widthFactor, Number heightFactor) {
    return scaledBy(scaling, widthFactor.floatValue(), heightFactor.floatValue());
  }

  /**
   * Returns a new gmage scaled by the provided factors, with the given algorithm
   *
   * @param scaling      the scaling algorithm
   * @param widthFactor  the width factor
   * @param heightFactor the height factor
   * @return a scaled gmage
   */
  public Gmage scaledBy(Scaling scaling, float widthFactor, float heightFactor) {
    return scaled(scaling, (int) (widthFactor * width), (int) (heightFactor * height));
  }

  /**
   * Returns a new gmage scaled to the given dimension using bilinear interpolation
   *
   * @param newWidth  the width of the returned gmage
   * @param newHeight the height of the returned gmage
   * @return a scaled gmage
   */
  public Gmage scaled(Number newWidth, Number newHeight) {
    return scaled(Scaling.BILINEAR_INTERPOLATION, newWidth.intValue(), newHeight.intValue());
  }

  /**
   * Returns a new gmage scaled to the given dimension using bilinear interpolation
   *
   * @param newWidth  the width of the returned gmage
   * @param newHeight the height of the returned gmage
   * @return a scaled gmage
   */
  public Gmage scaled(int newWidth, int newHeight) {
    return Scaling.BILINEAR_INTERPOLATION.scale(this, newWidth, newHeight);
  }

  /**
   * Returns a new gmage scaled to the given dimension, with the given algorithm
   *
   * @param scaling   the scaling algorithm
   * @param newWidth  the width of the returned gmage
   * @param newHeight the height of the returned gmage
   * @return a scaled gmage
   */
  public Gmage scaled(Scaling scaling, Number newWidth, Number newHeight) {
    return scaled(scaling, newWidth.intValue(), newHeight.intValue());
  }

  /**
   * Returns a new gmage scaled to the given dimension, with the given algorithm
   *
   * @param scaling   the scaling algorithm
   * @param newWidth  the width of the returned gmage
   * @param newHeight the height of the returned gmage
   * @return a scaled gmage
   */
  public Gmage scaled(Scaling scaling, int newWidth, int newHeight) {
    return scaling.scale(this, newWidth, newHeight);
  }

  /**
   * Returns a new gmage resulting of the rotation to 90 degrees clockwise of this gmage
   *
   * @return a 90 clockwise rotated gmage
   */
  public Gmage rotated90ClockWise() {
    Gmage gmage = new Gmage(height, width);
    for (int y = 0; y < gmage.height; y++) {
      for (int x = 0; x < gmage.width; x++) {
        gmage.putAt(gmage.getIndex(x, y), getAt(y, gmage.width - 1 - x));
      }
    }
    return gmage;
  }

  /**
   * Returns a new gmage resulting of the rotation to 90 degrees counter clockwise of this gmage
   *
   * @return a 90 counter clockwise rotated gmage
   */
  public Gmage rotated90CounterClockWise() {
    Gmage gmage = new Gmage(height, width);
    for (int y = 0; y < gmage.height; y++) {
      for (int x = 0; x < gmage.width; x++) {
        gmage.putAt(gmage.getIndex(x, y), getAt(gmage.height - 1 - y, x));
      }
    }
    return gmage;
  }

  /**
   * Returns a new gmage resulting of the rotation to 180 degrees of this gmage
   *
   * @return a 180 rotated gmage
   */
  public Gmage rotated180() {
    Gmage gmage = new Gmage(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        gmage.putAt(gmage.getIndex(x, y), getAt(width - 1 - x, height - 1 - y));
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
    pixels[getIndex(x1, y1)] = getAt(x2, y2);
    pixels[getIndex(x2, y2)] = color;
  }

  /**
   * Swap all pixel along the Y axis at the horizontal center of the gmage.
   */
  public void mirrorX() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width / 2; x++) {
        swap(x, y, width - 1 - x, y);
      }
    }
  }

  /**
   * Swap all pixel along the X axis at the vertical center of the gmage.
   */
  public void mirrorY() {
    for (int y = 0; y < height / 2; y++) {
      for (int x = 0; x < width; x++) {
        swap(x, y, x, height - 1 - y);
      }
    }
  }

  /**
   * Returns a new gmage resulting of the mirrorX of this one
   *
   * @return a mirrored X gmage
   */
  public Gmage mirroredX() {
    Gmage gmage = new Gmage(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        gmage.putAt(gmage.getIndex(x, y), getAt(width - 1 - x, y));
      }
    }
    return gmage;
  }

  /**
   * Returns a new gmage resulting of the mirrorY of this one
   *
   * @return a mirrored Y gmage
   */
  public Gmage mirroredY() {
    Gmage gmage = new Gmage(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        gmage.putAt(gmage.getIndex(x, y), getAt(x, height - 1 - y));
      }
    }
    return gmage;
  }

  static int getIndex(int x, int y, int width) {
    return y * width + x;
  }

  private int getIndex(int x, int y) {
    return y * width + x;
  }

  private int checkedIndex(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException(String
          .format("Indexes (%d, %d) are not within bounds (width=%d, height=%d)", x, y, width,
              height));
    }
    return getIndex(x, y);
  }

  private int checkedIndex(int i) {
    if (i < 0 || i >= pixels.length) {
      throw new IllegalArgumentException(
          String.format("Index %d is not within bounds (length=%d)", i, pixels.length));
    }
    return i;
  }

  public Gmage padded(Number padLeft, Number padTop, Number padRight, Number padBottom) {
    return padded(padLeft.intValue(), padTop.intValue(), padRight.intValue(), padBottom.intValue(),
        Color.BLACK);
  }

  public Gmage padded(Number padLeft, Number padTop, Number padRight, Number padBottom,
      Color backgroundColor) {
    return padded(padLeft.intValue(), padTop.intValue(), padRight.intValue(), padBottom.intValue(),
        backgroundColor);
  }

  public Gmage padded(int padLeft, int padTop, int padRight, int padBottom) {
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

  public Gmage padded(int padLeft, int padTop, int padRight, int padBottom, Color backgroundColor) {
    Gmage gmage =
        new Gmage(width + padLeft + padRight, height + padTop + padBottom, backgroundColor);
    copyInto(gmage, padLeft, padTop);
    return gmage;
  }

  public void copyInto(Gmage gmage, Number startX, Number startY) {
    copyInto(gmage, startX.intValue(), startY.intValue());
  }

  public void copyInto(Gmage gmage, int startX, int startY) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        gmage.pixels[gmage.getIndex(x + startX, y + startY)] = getAt(x, y);
      }
    }
  }

  public Gmage blurred(Blur blur) {
    return blur.apply(this);
  }

  public void forEachPixel(Consumer<Color> consumer) {
    for (int i = 0; i < pixels.length; i++) {
      consumer.accept(pixels[i]);
    }
  }

  public void forEachPixel(Consumer<Color> consumer, Region region) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
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

  public Gmage and(Number number) {
    return and(new Color(number));
  }

  public Gmage and(Integer number) {
    return and(new Color(number));
  }

  public Gmage and(Color color) {
    Gmage gmage = copy();
    for (int i = 0; i < pixels.length; i++) {
      gmage.pixels[i] = pixels[i].and(color);
    }
    return gmage;
  }

  public Gmage or(Number number) {
    return or(new Color(number));
  }

  public Gmage or(Integer number) {
    return or(new Color(number));
  }

  public Gmage or(Color color) {
    Gmage gmage = copy();
    for (int i = 0; i < pixels.length; i++) {
      gmage.pixels[i] = pixels[i].or(color);
    }
    return gmage;
  }

  public Gmage negative() {
    Gmage gmage = copy();
    gmage.apply(Color::negative);
    return gmage;
  }

  public Gmage toUndoRedo() {
    return toUndoRedo(10);
  }

  public UndoRedoGmage toUndoRedo(int windowSize) {
    return new UndoRedoGmage(this, windowSize);
  }

  @Override
  public String toString() {
    return String.format("Gmage (%d * %d)", width, height);
  }
}
