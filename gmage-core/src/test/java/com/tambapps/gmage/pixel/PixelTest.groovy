package com.tambapps.gmage.pixel

class PixelTest extends GroovyTestCase {


    void testBlackPixel() {
        Pixel pixel = Pixel.of(0x000000)
        assertEquals(255, pixel.getAlpha())
        assertEquals(0, pixel.getRed())
        assertEquals(0, pixel.getGreen())
        assertEquals(0, pixel.getBlue())
        assertEquals(0x000000, pixel.getRGB())
        assertEquals(0xff000000, pixel.getARGB())
    }

    void testWhitePixel() {
        Pixel pixel = Pixel.of(0xffffff)
        assertEquals(255, pixel.getAlpha())
        assertEquals(255, pixel.getRed())
        assertEquals(255, pixel.getGreen())
        assertEquals(255, pixel.getBlue())
        assertEquals(0xffffff, pixel.getRGB())
        assertEquals(0xffffffff, pixel.getARGB())
    }

    void testRedPixel() {
        Pixel pixel = Pixel.of(0xff0000)
        assertEquals(255, pixel.getAlpha())
        assertEquals(255, pixel.getRed())
        assertEquals(0, pixel.getGreen())
        assertEquals(0, pixel.getBlue())
        assertEquals(0xff0000, pixel.getRGB())
        assertEquals(0xffff0000, pixel.getARGB())
    }

    void testGreenPixel() {
        Pixel pixel = Pixel.of(0x00ff00)
        assertEquals(255, pixel.getAlpha())
        assertEquals(0, pixel.getRed())
        assertEquals(255, pixel.getGreen())
        assertEquals(0, pixel.getBlue())
        assertEquals(0x00ff00, pixel.getRGB())
        assertEquals(0xff00ff00, pixel.getARGB())
    }

    void testBluePixel() {
        Pixel pixel = Pixel.of(0x0000ff)
        assertEquals(255, pixel.getAlpha())
        assertEquals(0, pixel.getRed())
        assertEquals(0, pixel.getGreen())
        assertEquals(255, pixel.getBlue())
        assertEquals(0x0000ff, pixel.getRGB())
        assertEquals(0xff0000ff, pixel.getARGB())
    }
}
