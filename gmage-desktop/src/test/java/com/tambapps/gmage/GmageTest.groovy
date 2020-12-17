package com.tambapps.gmage

import com.tambapps.gmage.pixel.Pixel

class GmageTest extends GroovyTestCase {

  void testParseStream() {
    Gmage gmage = GmageDecoder.decode(GmageTest.class.getResource("/ronflex.jpg"))
    assertNotNull(gmage)
    Pixel pixel = gmage[0,0]
    assertNotNull(pixel)
    assertEquals(0xff, pixel.red)
    assertEquals(0xff, pixel.green)
    assertEquals(0xff, pixel.blue)
    assertEquals(0xff, pixel.alpha)
  }
}
