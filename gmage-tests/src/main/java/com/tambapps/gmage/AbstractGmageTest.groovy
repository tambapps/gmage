package com.tambapps.gmage

import com.tambapps.gmage.color.Color
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

abstract class AbstractGmageTest {

  @Test
  void testRedImage() {
    AbstractGmage gmage = filledImage(0xff0000)
    gmage.forEachPixel { Color pixel ->
      assertEquals(255, pixel.getAlpha())
      assertEquals(255, pixel.getRed())
      assertEquals(0, pixel.getGreen())
      assertEquals(0, pixel.getBlue())
      assertEquals(0xff0000, pixel.getRgb())
      assertEquals(0xffff0000, pixel.getArgb())
    }
  }

  @Test
  void testGreenImage() {
    AbstractGmage gmage = filledImage(0x00ff00)
    gmage.forEachPixel { Color pixel ->
      assertEquals(255, pixel.getAlpha())
      assertEquals(0, pixel.getRed())
      assertEquals(255, pixel.getGreen())
      assertEquals(0, pixel.getBlue())
      assertEquals(0x00ff00, pixel.getRgb())
      assertEquals(0xff00ff00, pixel.getArgb())
    }
  }

  @Test
  void testBlueImage() {
    AbstractGmage gmage = filledImage(0x0000ff)
    gmage.forEachPixel { Color pixel ->
      assertEquals(255, pixel.getAlpha())
      assertEquals(0, pixel.getRed())
      assertEquals(0, pixel.getGreen())
      assertEquals(255, pixel.getBlue())
      assertEquals(0x0000ff, pixel.getRgb())
      assertEquals(0xff0000ff, pixel.getArgb())
    }
  }

  protected abstract AbstractGmage filledImage(int rgb);

}
