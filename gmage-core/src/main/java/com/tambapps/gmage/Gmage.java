package com.tambapps.gmage;

import com.tambapps.gmage.blur.Blur;
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

  @Getter
  final int width;
  @Getter
  final int height;
  final Color[] pixels;

  public Gmage(int width, int height, Color[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = new Color[pixels.length];
    if (pixels.length != width * height) {
      throw new IllegalArgumentException("pixels should have a size of width * height");
    }
    System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
  }

  public Gmage(int width, int height) {
    this(width, height, Color.BLACK);
  }

  public Gmage(int width, int height, Color defaultColor) {
    this.width = width;
    this.height = height;
    this.pixels = new Color[width * height];
    Arrays.fill(pixels, defaultColor);
  }

  public Color getAt(Number i) {
    return getAt(i.intValue());
  }

  public Color getAt(int i) {
    return pixels[checkedIndex(i)];
  }

  public Color getAt(Number x, Number y) {
    return getAt(x.intValue(), y.intValue());
  }

  public Color getAt(int x, int y) {
    return pixels[checkedIndex(x, y)];
  }

  public void putAt(List<Number> xy, Color value) {
    pixels[checkedIndex(xy.get(0).intValue(), xy.get(1).intValue())] = value;
  }
  public Stream<Color> pixels() {
    return Arrays.stream(pixels);
  }

  public Gmage copy() {
    return new Gmage(this.width, this.height, this.pixels);
  }

  public void putAt(Integer oneDIndex, Number value) {
    putAt(oneDIndex.intValue(), new Color(value));
  }

  public void putAt(Integer oneDIndex, Integer value) {
    putAt(oneDIndex.intValue(), new Color(value));
  }

  public void putAt(Integer oneDIndex, Color value) {
    putAt(oneDIndex.intValue(), value);
  }

  public void putAt(int oneDIndex, Number value) {
    putAt(oneDIndex, new Color(value));
  }

  public void putAt(int oneDIndex, Integer value) {
    putAt(oneDIndex, new Color(value));
  }

  public void putAt(int oneDIndex, Color value) {
    pixels[checkedIndex(oneDIndex)] = value;
  }

  public void putPixel(int x, int y, Color value) {
    pixels[checkedIndex(x, y)] = value;
  }

  // used when there is 2 arguments
  public void putAt(List<Number> xy, Number value) {
    putAt(xy, new Color(value));
  }

  public void putAt(List<Number> xy, Integer value) {
    putAt(xy, new Color(value));
  }

  public void set(Gmage gmage) {
    if (width != gmage.width || height != gmage.height) {
      throw new IllegalArgumentException("Cannot set from gmage with different size");
    }
    System.arraycopy(gmage.pixels, 0, pixels, 0, pixels.length);
  }

  public void apply(ColorTransformer transformer) {
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = transformer.apply(this.pixels[i]);
    }
  }

  public void apply(ColorTransformer transformer, Region region) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (region.contains(x, y)) {
          this.pixels[getIndex(x, y)] = transformer.apply(getAt(x, y));
        }
      }
    }
  }

  public void applyOutside(ColorTransformer transformer, Region region) {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        if (!region.contains(x, y)) {
          this.pixels[getIndex(x, y)] = transformer.apply(getAt(x, y));
        }
      }
    }
  }

  public Gmage scaledBy(Number factor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, factor, factor);
  }

  public Gmage scaledBy(float factor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, factor, factor);
  }

  public Gmage scaledBy(Scaling scaling, Float factor) {
    return scaledBy(scaling, factor.floatValue());
  }

  public Gmage scaledBy(Scaling scaling, float factor) {
    return scaledBy(scaling, factor, factor);
  }

  public Gmage scaledBy(float widthFactor, float heightFactor) {
    return scaled(Scaling.BILINEAR_INTERPOLATION, (int) (widthFactor * width), (int) (heightFactor * height));
  }

  public Gmage scaledBy(Number widthFactor, Number heightFactor) {
    return scaledBy(Scaling.BILINEAR_INTERPOLATION, widthFactor.floatValue(), heightFactor.floatValue());
  }

  public Gmage scaledBy(Scaling scaling, Number widthFactor, Number heightFactor) {
    return scaledBy(scaling, widthFactor.floatValue(), heightFactor.floatValue());
  }

  public Gmage scaledBy(Scaling scaling, float widthFactor, float heightFactor) {
    return scaled(scaling, (int) (widthFactor * width), (int) (heightFactor * height));
  }

  public Gmage scaled(Number newWidth, Number newHeight) {
    return scaled(Scaling.BILINEAR_INTERPOLATION, newWidth.intValue(), newHeight.intValue());
  }

  public Gmage scaled(int newWidth, int newHeight) {
    return Scaling.BILINEAR_INTERPOLATION.scale(this, newWidth, newHeight);
  }

  public Gmage scaled(Scaling scaling, Number newWidth, Number newHeight) {
    return scaled(scaling, newWidth.intValue(), newHeight.intValue());
  }

  public Gmage scaled(Scaling scaling, int newWidth, int newHeight) {
    return scaling.scale(this, newWidth, newHeight);
  }

  public Gmage rotated90ClockWise() {
    Gmage gmage = new Gmage(height, width);
    for (int y = 0; y < gmage.height; y++) {
      for (int x = 0; x < gmage.width; x++) {
        gmage.putAt(gmage.getIndex(x,y), getAt(y, gmage.width - 1 - x));
      }
    }
    return gmage;
  }

  public Gmage rotated90CounterClockWise() {
    Gmage gmage = new Gmage(height, width);
    for (int y = 0; y < gmage.height; y++) {
      for (int x = 0; x < gmage.width; x++) {
        gmage.putAt(gmage.getIndex(x,y), getAt(gmage.height - 1 - y, x));
      }
    }
    return gmage;
  }

  public Gmage rotated180() {
    Gmage gmage = new Gmage(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        gmage.putAt(gmage.getIndex(x,y), getAt(width - 1 - x, height - 1 - y));
      }
    }
    return gmage;
  }

  public void swap(int x1, int y1, int x2, int y2) {
    Color color = getAt(x1, y1);
    pixels[getIndex(x1, y1)] = getAt(x2, y2);
    pixels[getIndex(x2, y2)] = color;
  }

  public void mirrorX() {
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width / 2; x++) {
        swap(x, y, width - 1 - x, y);
      }
    }
  }

  public void mirrorY() {
    for (int y = 0; y < height / 2; y++) {
      for (int x = 0; x < width; x++) {
        swap(x, y, x, height - 1 - y);
      }
    }
  }

  public Gmage mirroredX() {
    Gmage gmage = new Gmage(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        gmage.putAt(gmage.getIndex(x,y), getAt(width - 1 - x, y));
      }
    }
    return gmage;
  }

  public Gmage mirroredY() {
    Gmage gmage = new Gmage(width, height);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        gmage.putAt(gmage.getIndex(x,y), getAt(x, height - 1 - y));
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
      throw new IllegalArgumentException(String.format("Indexes (%d, %d) are not within bounds (width=%d, height=%d)", x, y, width, height));
    }
    return getIndex(x, y);
  }

  private int checkedIndex(int i) {
    if (i < 0 || i >= pixels.length) {
      throw new IllegalArgumentException(String.format("Index %d is not within bounds (length=%d)", i, pixels.length));
    }
    return i;
  }

  public Gmage padded(Number padLeft, Number padTop, Number padRight, Number padBottom) {
    return padded(padLeft.intValue(), padTop.intValue(), padRight.intValue(), padBottom.intValue(), Color.BLACK);
  }

  public Gmage padded(Number padLeft, Number padTop, Number padRight, Number padBottom, Color backgroundColor) {
    return padded(padLeft.intValue(), padTop.intValue(), padRight.intValue(), padBottom.intValue(), backgroundColor);
  }

  public Gmage padded(int padLeft, int padTop, int padRight, int padBottom) {
    return padded(padLeft, padTop, padRight, padBottom, Color.BLACK);
  }

  public Gmage padded(int padLeft, int padTop, int padRight, int padBottom, Color backgroundColor) {
    Gmage gmage = new Gmage(width + padLeft + padRight, height + padTop + padBottom, backgroundColor);
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
