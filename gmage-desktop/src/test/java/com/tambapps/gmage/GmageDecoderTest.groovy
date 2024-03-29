package com.tambapps.gmage

import org.junit.jupiter.api.Test

import java.awt.*
import java.awt.image.BufferedImage

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertNotNull

class GmageDecoderTest extends AbstractGmageTest {

  @Test
  void testParseStream() {
    AbstractGmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    assertNotNull(gmage)
    com.tambapps.gmage.color.Color pixel = gmage[0, 0]
    assertNotNull(pixel)
    assertEquals(0xff, pixel.alpha, "alpha test")
    assertEquals(0xff, pixel.red, "red test")
    assertEquals(0xff, pixel.green, "green test")
    assertEquals(0xff, pixel.blue, "blue test")
  }

  @Override
  protected AbstractGmage filledImage(int rgb) {
    int width = 64
    int height = 64
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    Graphics2D g = (Graphics2D) image.getGraphics()
    g.setColor(new Color(rgb))
    g.fillRect(0, 0, width, height)
    return GmageDecoder.decode(image)
  }
}
