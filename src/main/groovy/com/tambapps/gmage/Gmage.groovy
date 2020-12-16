package com.tambapps.gmage

import com.tambapps.gmage.pixel.Pixel

class Gmage {

  final int width
  final int height
  private final Pixel[] pixels

  Gmage(int width, int height, Pixel[] pixels) {
    this.width = width
    this.height = height
    this.pixels = new Pixel[pixels.size()]
    if (pixels.size() != width * height) {
      throw new IllegalArgumentException('pixels should have a size of width * height')
    }
    System.arraycopy(pixels, 0, this.pixels, 0, pixels.size())
  }

  Gmage(int width, int height) {
    this.width = width
    this.height = height
    this.pixels = new Pixel[width * height]
    for (i in pixels.indices) {
      this.pixels[i] = new Pixel()
    }
  }

  Pixel getAt(Number x, Number y) {
    return pixels[checkedIndex(x.intValue(), y.intValue())]
  }

  void putAt(List<Number> xy, Pixel value) {
    pixels[checkedIndex(xy[0].intValue(), xy[1].intValue())].set(value)
  }

  void putAt(List<Number> xy, Number value) {
    pixels[checkedIndex(xy[0].intValue(), xy[1].intValue())].set(value)
  }

  private int getIndex(int x, int y) {
    return y * width + x
  }

  private int checkedIndex(int x, int y) {
    if (x < 0 || x >= width || y < 0 || y >= height) {
      throw new IllegalArgumentException("Indexes are not within bounds")
    }
    return getIndex(x, y)
  }
}
