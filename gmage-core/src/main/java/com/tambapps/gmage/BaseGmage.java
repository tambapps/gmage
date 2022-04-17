package com.tambapps.gmage;

import com.tambapps.gmage.color.Color;
import lombok.Getter;

import java.util.Arrays;
import java.util.stream.Stream;

public class BaseGmage extends AbstractGmage {

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
  public BaseGmage(int width, int height, Color[] pixels) {
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
  public BaseGmage(int width, int height) {
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
  public BaseGmage(int width, int height, Color defaultColor) {
    this.width = width;
    this.height = height;
    this.pixels = new Color[width * height];
    Arrays.fill(pixels, defaultColor);
  }


  /**
   * Returns a stream of all pixels
   *
   * @return a stream of all pixels
   */
  public Stream<Color> pixels() {
    return Arrays.stream(pixels);
  }

  @Override
  public Color getAt(int i) {
    return pixels[checkedIndex(i)];
  }

  @Override
  public Color getAt(int x, int y) {
    return getAt(getIndex(x, y));
  }

  @Override
  public BaseGmage copy() {
    return new BaseGmage(width, height, pixels);
  }

  @Override
  public BaseGmage newInstance(int width, int height) {
    return new BaseGmage(width, height);
  }

  @Override
  public BaseGmage newInstance(int width, int height, Color[] pixels) {
    return new BaseGmage(width, height, pixels);
  }

  @Override
  public void putAt(int oneDIndex, Color value) {
    pixels[checkedIndex(oneDIndex)] = value;
  }

  @Override
  public void putColor(int x, int y, Color value) {
    putAt(getIndex(x, y), value);
  }

}
