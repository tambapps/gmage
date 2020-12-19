package com.tambapps.gmage;

import com.tambapps.gmage.color.Color;
import com.tambapps.gmage.transformer.ColorTransformer;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

// TODO add scaled and padded functions
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

  public Color getAt(Number x, Number y) {
    return pixels[checkedIndex(x.intValue(), y.intValue())];
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

  public void setAt(Integer oneDIndex, Number value) {
    setAt(oneDIndex.intValue(), new Color(value));
  }

  public void setAt(Integer oneDIndex, Integer value) {
    setAt(oneDIndex.intValue(), new Color(value));
  }

  public void setAt(Integer oneDIndex, Color value) {
    setAt(oneDIndex.intValue(), value);
  }

  public void setAt(int oneDIndex, Number value) {
    setAt(oneDIndex, new Color(value));
  }

  public void setAt(int oneDIndex, Integer value) {
    setAt(oneDIndex, new Color(value));
  }

  public void setAt(int oneDIndex, Color value) {
    if (oneDIndex < 0 || oneDIndex >= pixels.length) {
      throw new IllegalArgumentException(String.format("Index %d is not within bounds (length=%d)", oneDIndex, pixels.length));
    }
    pixels[oneDIndex] = value;
  }

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
    for (int i = 0; i < pixels.length; i++) {
      pixels[i] = gmage.pixels[i];
    }
  }

  public void apply(ColorTransformer transformer) {
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = transformer.apply(this.pixels[i]);
    }
  }

  static int getIndex(int x, int y, int width) {
    return y * width + x;
  }

  private int getIndex(int x, int y) {
    return y * width + x;
  }

  private int checkedIndex(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("Indexes are not within bounds");
    }
    return getIndex(x, y);
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
    copyInto(gmage, padLeft, padBottom);
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

  public void forEachPixel(Consumer<Color> consumer) {
    for (int i = 0; i < pixels.length; i++) {
      consumer.accept(pixels[i]);
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
      pixels[i] = pixels[i].and(color);
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
      pixels[i] = pixels[i].or(color);
    }
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
