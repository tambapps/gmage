package com.tambapps.gmage;

import com.tambapps.gmage.pixel.Pixel;
import com.tambapps.gmage.transformer.PixelTransformer;
import lombok.Getter;

import java.util.List;

public class Gmage {

  @Getter
  final int width;
  @Getter
  final int height;
  final Pixel[] pixels;

  Gmage(int width, int height, Pixel[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[pixels.length];
    if (pixels.length != width * height) {
      throw new IllegalArgumentException("pixels should have a size of width * height");
    }
    System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
  }

  Gmage(int width, int height) {
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

  public void putAt(List<Number> xy, Pixel value) {
    pixels[checkedIndex(xy.get(0).intValue(), xy.get(1).intValue())].set(value);
  }

  public void putAt(List<Number> xy, Number value) {
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

  public void apply(PixelTransformer transformer) {
    for (int i = 0; i < pixels.length; i++) {
      transformer.apply(this.pixels[i]);
    }
  }

  public void leftShift(PixelTransformer transformer) {
    apply(transformer);
  }

  public Gmage toUndoRedo() {
    return toUndoRedo(10);
  }

  public URGmage toUndoRedo(int windowSize) {
    return new URGmage(this, windowSize);
  }
}
