package com.tambapps.gmage.pixel

class PixelTest extends GroovyTestCase {


    void testBlackPixel() {
        Pixel pixel = new Pixel(0x000000)
        assertEquals(255, pixel.alpha)
        assertEquals(0, pixel.red)
        assertEquals(0, pixel.green)
        assertEquals(0, pixel.blue)
        assertEquals(0x000000, pixel.rgb)
        assertEquals(0xff000000, pixel.argb)
        assertEquals(0xff000000, Pixel.toArgb(255, 0, 0, 0))
    }

    void testWhitePixel() {
        Pixel pixel = new Pixel(0xffffff)
        assertEquals(255, pixel.alpha)
        assertEquals(255, pixel.red)
        assertEquals(255, pixel.green)
        assertEquals(255, pixel.blue)
        assertEquals(0xffffff, pixel.rgb)
        assertEquals(0xffffffff, pixel.argb)
        assertEquals(0xffffffff, pixel.argb, Pixel.toArgb(255, 255, 255, 255))
    }

    void testRedPixel() {
        Pixel pixel = new Pixel(0xff0000)
        assertEquals(255, pixel.alpha)
        assertEquals(255, pixel.red)
        assertEquals(0, pixel.green)
        assertEquals(0, pixel.blue)
        assertEquals(0xff0000, pixel.rgb)
        assertEquals(0xffff0000, pixel.argb)
        assertEquals(0xffff0000, Pixel.toArgb(255, 255, 0, 0))
    }

    void testGreenPixel() {
        Pixel pixel = new Pixel(0x00ff00)
        assertEquals(255, pixel.alpha)
        assertEquals(0, pixel.red)
        assertEquals(255, pixel.green)
        assertEquals(0, pixel.blue)
        assertEquals(0x00ff00, pixel.rgb)
        assertEquals(0xff00ff00, pixel.argb)
        assertEquals(0xff00ff00, Pixel.toArgb(255, 0, 255, 0))
    }

    void testBluePixel() {
        Pixel pixel = new Pixel(0x0000ff)
        assertEquals(255, pixel.alpha)
        assertEquals(0, pixel.red)
        assertEquals(0, pixel.green)
        assertEquals(255, pixel.blue)
        assertEquals(0x0000ff, pixel.rgb)
        assertEquals(0xff0000ff, pixel.argb)
        assertEquals(0xff0000ff, Pixel.toArgb(255, 0, 0, 255))
    }

    void testSetters() {
        // test different constructors of Pixel
        /*
        Pixel pixel = Pixel.black()
        pixel.red = 255
        assertEquals(new Pixel(0xff0000), pixel)
        pixel.green = 255
        assertEquals(new Pixel(0xffff00), pixel)
        pixel.blue = 255
        assertEquals(new Pixel(0xffffff), pixel)
        pixel.alpha = 0
        assertEquals(new Pixel(0xffffff, 0), pixel)
        pixel.rgb = 0x123456
        assertEquals(new Pixel(0x123456, 0), pixel)
        pixel.argb = 0x12345678
        assertEquals(new Pixel(0x345678, 0x12), pixel)

         */
    }

}
