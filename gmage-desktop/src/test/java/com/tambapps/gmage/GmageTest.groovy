package com.tambapps.gmage

import com.tambapps.gmage.pixel.Pixel

import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class GmageTest extends GroovyTestCase {

  void testParseStream() {
    Gmage gmage = GmageDecoder.decode(GmageTest.class.getResource("/ronflex.jpg"))
    assertNotNull(gmage)
    Pixel pixel = gmage[0,0]
    assertNotNull(pixel)
    assertEquals("alpha test", 0xff, pixel.alpha)
    assertEquals("red test", 0xff, pixel.red)
    assertEquals("green test", 0xff, pixel.green)
    assertEquals("blue test", 0xff, pixel.blue)
  }

  void testRedImage() {
    Gmage gmage = filledImage(0xffff0000 as int)
    gmage.forEachPixel { Pixel pixel ->
      assertEquals(pixel.getAlpha(), 0xff)
      assertEquals(pixel.getRed(), 0xff)
      assertEquals(pixel.getGreen(), 0)
      assertEquals(pixel.getBlue(), 0)
      // TODO these two last asserts don't work
      assertEquals(pixel.getRGB(), 0xffffff)
      assertEquals(pixel.getARGB(), 0xffffffff)
    }
  }

  // TODO use argument
  private Gmage filledImage(int argb) {
    int width = 64
    int height = 64
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    Graphics2D g = (Graphics2D) image.getGraphics()
    g.setColor(Color.red)
    g.fillRect(0, 0, width, height)
    return GmageDecoder.decode(image)
  }
}
