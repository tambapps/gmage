package com.tambapps.gmage.color

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class ColorTest {

  @Test
  void testBlackPixel() {
    Color pixel = new Color(0x000000)
    assertEquals(255, pixel.alpha)
    assertEquals(0, pixel.red)
    assertEquals(0, pixel.green)
    assertEquals(0, pixel.blue)
    assertEquals(0x000000, pixel.rgb)
    assertEquals(0xff000000, pixel.argb)
    assertEquals(0xff000000, Color.toArgb(255, 0, 0, 0))
  }

  @Test
  void testWhitePixel() {
    Color pixel = new Color(0xffffff)
    assertEquals(255, pixel.alpha)
    assertEquals(255, pixel.red)
    assertEquals(255, pixel.green)
    assertEquals(255, pixel.blue)
    assertEquals(0xffffff, pixel.rgb)
    assertEquals(0xffffffff, pixel.argb)
    assertEquals(0xffffffff, pixel.argb, Color.toArgb(255, 255, 255, 255))
  }

  @Test
  void testRedPixel() {
    Color pixel = new Color(0xff0000)
    assertEquals(255, pixel.alpha)
    assertEquals(255, pixel.red)
    assertEquals(0, pixel.green)
    assertEquals(0, pixel.blue)
    assertEquals(0xff0000, pixel.rgb)
    assertEquals(0xffff0000, pixel.argb)
    assertEquals(0xffff0000, Color.toArgb(255, 255, 0, 0))
  }

  @Test
  void testGreenPixel() {
    Color pixel = new Color(0x00ff00)
    assertEquals(255, pixel.alpha)
    assertEquals(0, pixel.red)
    assertEquals(255, pixel.green)
    assertEquals(0, pixel.blue)
    assertEquals(0x00ff00, pixel.rgb)
    assertEquals(0xff00ff00, pixel.argb)
    assertEquals(0xff00ff00, Color.toArgb(255, 0, 255, 0))
  }

  @Test
  void testBluePixel() {
    Color pixel = new Color(0x0000ff)
    assertEquals(255, pixel.alpha)
    assertEquals(0, pixel.red)
    assertEquals(0, pixel.green)
    assertEquals(255, pixel.blue)
    assertEquals(0x0000ff, pixel.rgb)
    assertEquals(0xff0000ff, pixel.argb)
    assertEquals(0xff0000ff, Color.toArgb(255, 0, 0, 255))
  }

  @Test
  void testConstructors() {
    long value = 0xf0f0f0f0
    Color color = new Color(value)
    assertEquals(value, color.argb)
    assertEquals(color, new Color(0xf0f0f0, 0xf0))
    assertEquals(color, new Color(0xf0, 0xf0, 0xf0, 0xf0))
  }

  @Test
  void testNegative() {
    assertEquals(Color.WHITE, -Color.BLACK)
    assertEquals(Color.BLACK, -Color.WHITE)
    assertEquals(new Color(0xf0f0f0f0), -new Color(0xf00f0f0f))
  }

  @Test
  void testPlus() {
    assertEquals(Color.WHITE, Color.WHITE + Color.BLACK)
    assertEquals(Color.WHITE, Color.BLACK + Color.WHITE)
    assertEquals(Color.WHITE, Color.RED + Color.GREEN + Color.BLUE)
    assertEquals(Color.WHITE, new Color(0xf0f0f0f0) + new Color(0x0f0f0f0f))
    assertEquals(new Color(0xffff00), new Color(0xff0000) + new Color(0x00ff00))
  }

  @Test
  void testMinus() {
    assertEquals(Color.WHITE, Color.WHITE - Color.BLACK)
    assertEquals(Color.BLACK, Color.BLACK - Color.WHITE)
    assertEquals(Color.RED, Color.RED - Color.GREEN)
    assertEquals(new Color(0xff00ff00), new Color(0xffffff00) - new Color(0x00ff0000))
  }

  @Test
  void testOr() {
    assertEquals(Color.WHITE, Color.WHITE | Color.BLACK)
    assertEquals(Color.WHITE, Color.BLACK | Color.WHITE)
    assertEquals(Color.WHITE, Color.RED | Color.GREEN | Color.BLUE)
    assertEquals(Color.WHITE, new Color(0xf0f0f0f0) | new Color(0x0f0f0f0f))
    assertEquals(new Color(0xffff00), new Color(0xff0000) | new Color(0x00ff00))
  }

  @Test
  void testAnd() {
    assertEquals(Color.BLACK, Color.WHITE & Color.BLACK)
    assertEquals(Color.BLACK, Color.BLACK & Color.WHITE)
    assertEquals(Color.BLACK, Color.RED & Color.GREEN)
    assertEquals(new Color(0x00ff0000L), new Color(0xffffff00L) & new Color(0x00ff0000L))
  }

  @Test
  void testDistance() {
    assertEquals(0, Color.BLACK.distance2(Color.BLACK))
    assertEquals(0, Color.BLACK.distance(Color.BLACK))

    assertEquals(Color.MAX_DISTANCE_2, Color.BLACK.distance2(Color.WHITE))
    assertApproximatelyEquals(Math.sqrt(Color.MAX_DISTANCE_2), Color.BLACK.distance(Color.WHITE))

    assertEquals(255 * 255 * 2, Color.RED.distance2(Color.GREEN))
    assertApproximatelyEquals(Math.sqrt(255 * 255 * 2), Color.RED.distance(Color.GREEN))
    assertEquals(255 * 255 * 2, Color.RED.distance2(Color.BLUE))
    assertApproximatelyEquals(Math.sqrt(255 * 255 * 2), Color.RED.distance(Color.BLUE))
    assertEquals(255 * 255 * 2, Color.BLUE.distance2(Color.GREEN))
    assertApproximatelyEquals(Math.sqrt(255 * 255 * 2), Color.BLUE.distance(Color.GREEN))
  }

  private static void assertApproximatelyEquals(double expected, double actual) {
    assertTrue(Math.abs(expected - actual) < 0.001, "Expected $expected but got $actual")
  }
}
