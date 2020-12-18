package com.tambapps.gmage;

import com.tambapps.gmage.pixel.Color;
import com.tambapps.gmage.transformer.ColorTransformer;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Gmage {

  @Getter
  final int width;
  @Getter
  final int height;
  final Color[] pixels;

  Gmage(int width, int height, Color[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = new Color[pixels.length];
    if (pixels.length != width * height) {
      throw new IllegalArgumentException("pixels should have a size of width * height");
    }
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = Color.copy(pixels[i]);
    }
  }

  Gmage(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new Color[width * height];
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = Color.BLACK;
    }
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

  // groovy methods
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

  public URGmage toUndoRedo(int windowSize) {
    return new URGmage(this, windowSize);
  }
}
