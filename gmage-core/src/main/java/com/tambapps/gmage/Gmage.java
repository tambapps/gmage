package com.tambapps.gmage;

import com.tambapps.gmage.pixel.Pixel;
import com.tambapps.gmage.transformer.PixelTransformer;
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
  final Pixel[] pixels;

  Gmage(int width, int height, Pixel[] pixels) {
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[pixels.length];
    if (pixels.length != width * height) {
      throw new IllegalArgumentException("pixels should have a size of width * height");
    }
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = Pixel.copy(pixels[i]);
    }
  }

  Gmage(int width, int height) {
    this.width = width;
    this.height = height;
    this.pixels = new Pixel[width * height];
    for (int i = 0; i < pixels.length; i++) {
      this.pixels[i] = Pixel.black();
    }
  }

  public Pixel getAt(Number x, Number y) {
    return pixels[checkedIndex(x.intValue(), y.intValue())];
  }

  public void putAt(List<Number> xy, Pixel value) {
    pixels[checkedIndex(xy.get(0).intValue(), xy.get(1).intValue())].set(value);
  }

  public void putAt(List<Number> xy, Number value) {
    pixels[checkedIndex(xy.get(0).intValue(), xy.get(1).intValue())].setArgb(value);
  }

  public Stream<Pixel> pixels() {
    return Arrays.stream(pixels);
  }

  public Gmage copy() {
    return new Gmage(this.width, this.height, this.pixels);
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

  // groovy methods
  public void forEachPixel(Consumer<Pixel> consumer) {
    for (int i = 0; i < pixels.length; i++) {
      consumer.accept(pixels[i]);
    }
  }

  // groovy operator
  public void leftShift(PixelTransformer transformer) {
    apply(transformer);
  }

  // TODO make or function
  public Gmage and(Number number) {
    Gmage gmage = new Gmage(width, height, pixels);
    int filter = number.intValue();
    gmage.apply((p) -> p.setArgb(p.getArgb() & filter));
    return gmage;
  }

  public Gmage toUndoRedo() {
    return toUndoRedo(10);
  }

  public URGmage toUndoRedo(int windowSize) {
    return new URGmage(this, windowSize);
  }
}
