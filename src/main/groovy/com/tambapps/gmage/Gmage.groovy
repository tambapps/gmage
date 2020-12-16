package com.tambapps.gmage

import com.tambapps.gmage.pixel.Pixel

class Gmage {

  final int width
  final int height
  private final Pixel[] pixels

  Gmage(int width, int height, Pixel[] pixels) {
    this.width = width
    this.height = height
    if (pixels.size() != width * height) {
      throw new IllegalArgumentException('pixels should have a size of width * height')
    }
    this.pixels = new Pixel[pixels.size()]
    System.arraycopy(pixels, 0, this.pixels, 0, pixels.size())
  }

  Gmage(int width, int height) {
    this.width = width
    this.height = height
    this.pixels = new Pixel[pixels.size()]
    for (i in pixels.indices) {
      pixels[i] = new Pixel()
    }
  }
}
