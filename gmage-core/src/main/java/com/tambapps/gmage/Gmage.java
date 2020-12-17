package com.tambapps.gmage;

import com.tambapps.gmage.pixel.Pixel;
import com.tambapps.gmage.transformer.PixelTransformer;

import java.util.List;

public class Gmage {

  final int width;
  final int height;
  private final Pixel[] pixels;

  public Gmage(int width, int height, Pixel[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[pixels.length];
    if (pixels.length != width * height) {
      throw new IllegalArgumentException("pixels should have a size of width * height");
    }
    System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
  }

  public Gmage(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[width * height];
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = new Pixel();
    }
  }

  public Pixel getAt(Number x, Number y) {
    return pixels[checkedIndex(x.intValue(), y.intValue())];
  }

  void putAt(List<Number> xy, Pixel value) {
    pixels[checkedIndex(xy.get(0).intValue(), xy.get(1).intValue())].set(value);
  }

  void putAt(List<Number> xy, Number value) {
    pixels[checkedIndex(xy.get(0).intValue(), xy.get(1).intValue())].set(value);
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

  void apply(PixelTransformer transformer) {
    for (int i = 0; i < pixels.length; i++) {
      transformer.apply(this.pixels[i]);
    }
  }

  void leftShift(PixelTransformer transformer) {
    apply(transformer);
  }
}
