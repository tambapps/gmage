package com.tambapps.gmage

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import org.junit.jupiter.api.Test

class GmageDecoderTest {

  @Test
  void testParseStream() {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    assertNotNull(gmage)
    com.tambapps.gmage.pixel.Color pixel = gmage[0,0]
    assertNotNull(pixel)
    assertEquals(0xff, pixel.alpha, "alpha test")
    assertEquals(0xff, pixel.red, "red test")
    assertEquals(0xff, pixel.green, "green test")
    assertEquals(0xff, pixel.blue, "blue test")
  }

  @Test
  void testRedImage() {
    Gmage gmage = filledImage(0xff0000)
    gmage.forEachPixel { com.tambapps.gmage.pixel.Color pixel ->
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
    Gmage gmage = filledImage(0x00ff00)
    gmage.forEachPixel { com.tambapps.gmage.pixel.Color pixel ->
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
    Gmage gmage = filledImage(0x0000ff)
    gmage.forEachPixel { com.tambapps.gmage.pixel.Color pixel ->
      assertEquals(255, pixel.getAlpha())
      assertEquals(0, pixel.getRed())
      assertEquals(0, pixel.getGreen())
      assertEquals(255, pixel.getBlue())
      assertEquals(0x0000ff, pixel.getRgb())
      assertEquals(0xff0000ff, pixel.getArgb())
    }
  }

  private static Gmage filledImage(int rgb) {
    int width = 64
    int height = 64
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    Graphics2D g = (Graphics2D) image.getGraphics()
    g.setColor(new Color(rgb))
    g.fillRect(0, 0, width, height)
    return GmageDecoder.decode(image)
  }
}
