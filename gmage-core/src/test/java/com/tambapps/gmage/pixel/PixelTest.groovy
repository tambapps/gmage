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
        assertEquals(0xff000000, Pixel.toARGB(255, 0, 0, 0))
    }

    void testWhitePixel() {
        Pixel pixel = Pixel.of(0xffffff)
        assertEquals(255, pixel.getAlpha())
        assertEquals(255, pixel.getRed())
        assertEquals(255, pixel.getGreen())
        assertEquals(255, pixel.getBlue())
        assertEquals(0xffffff, pixel.getRGB())
        assertEquals(0xffffffff, pixel.getARGB())
        assertEquals(0xffffffff, pixel.getARGB(), Pixel.toARGB(255, 255, 255, 255))
    }

    void testRedPixel() {
        Pixel pixel = Pixel.of(0xff0000)
        assertEquals(255, pixel.getAlpha())
        assertEquals(255, pixel.getRed())
        assertEquals(0, pixel.getGreen())
        assertEquals(0, pixel.getBlue())
        assertEquals(0xff0000, pixel.getRGB())
        assertEquals(0xffff0000, pixel.getARGB())
        assertEquals(0xffff0000, Pixel.toARGB(255, 255, 0, 0))
    }

    void testGreenPixel() {
        Pixel pixel = Pixel.of(0x00ff00)
        assertEquals(255, pixel.getAlpha())
        assertEquals(0, pixel.getRed())
        assertEquals(255, pixel.getGreen())
        assertEquals(0, pixel.getBlue())
        assertEquals(0x00ff00, pixel.getRGB())
        assertEquals(0xff00ff00, pixel.getARGB())
        assertEquals(0xff00ff00, Pixel.toARGB(255, 0, 255, 0))
    }

    void testBluePixel() {
        Pixel pixel = Pixel.of(0x0000ff)
        assertEquals(255, pixel.getAlpha())
        assertEquals(0, pixel.getRed())
        assertEquals(0, pixel.getGreen())
        assertEquals(255, pixel.getBlue())
        assertEquals(0x0000ff, pixel.getRGB())
        assertEquals(0xff0000ff, pixel.getARGB())
        assertEquals(0xff0000ff, Pixel.toARGB(255, 0, 0, 255))
    }

    void testSetters() {
        Pixel pixel = Pixel.black()
        pixel.setRed(255)
        assertEquals(Pixel.of(0xff0000), pixel)
        pixel.setGreen(255)
        assertEquals(Pixel.of(0xffff00), pixel)
        pixel.setBlue(255)
        assertEquals(Pixel.of(0xffffff), pixel)
        pixel.setAlpha(0)
        assertEquals(Pixel.of(0xffffff, 0), pixel)
        pixel.setRGB(0x123456)
        assertEquals(Pixel.of(0x123456, 0), pixel)
        pixel.setARGB(0x12345678)
        assertEquals(Pixel.of(0x345678, 0x12), pixel)
    }


    void testValuesOutOfBounds() {
        def pixel = Pixel.black()
        pixel.red = 12345
        assertEquals(255, pixel.red)
        pixel.green = 12345
        assertEquals(255, pixel.green)
        pixel.blue = 12345
        assertEquals(255, pixel.blue)
    }
}
