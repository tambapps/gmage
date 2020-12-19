package com.tambapps.gmage

import com.tambapps.gmage.color.Color

import static org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertThrows

class GmageTest {

    @Test
    void testRedImage() {
        Gmage gmage = filledImage(0xff0000)
        gmage.forEachPixel { Color pixel ->
            assertEquals(255, pixel.getAlpha())
            assertEquals(255, pixel.getRed())
            assertEquals(0, pixel.getGreen())
            assertEquals(0, pixel.getBlue())
            assertEquals(0xff0000, pixel.getRgb())
            assertEquals(0xffff0000, pixel.getArgb())
        }
        assertEquals(gmage, gmage.copy())
    }

    @Test
    void testGreenImage() {
        Gmage gmage = filledImage(0x00ff00)
        gmage.forEachPixel { Color pixel ->
            assertEquals(255, pixel.getAlpha())
            assertEquals(0, pixel.getRed())
            assertEquals(255, pixel.getGreen())
            assertEquals(0, pixel.getBlue())
            assertEquals(0x00ff00, pixel.getRgb())
            assertEquals(0xff00ff00, pixel.getArgb())
        }
        assertEquals(gmage, gmage.copy())
    }

    @Test
    void testBlueImage() {
        Gmage gmage = filledImage(0x0000ff)
        gmage.forEachPixel { Color pixel ->
            assertEquals(255, pixel.getAlpha())
            assertEquals(0, pixel.getRed())
            assertEquals(0, pixel.getGreen())
            assertEquals(255, pixel.getBlue())
            assertEquals(0x0000ff, pixel.getRgb())
            assertEquals(0xff0000ff, pixel.getArgb())
        }
        assertEquals(gmage, gmage.copy())
    }

    @Test
    void testPut() {
        Gmage gmage = new Gmage(8, 8)
        gmage[0] = Color.WHITE
        assertEquals(gmage[0], Color.WHITE)
        assertEquals(gmage[0], gmage[0, 0])

        Gmage gmage1 = new Gmage(gmage.width, gmage.height)
        gmage.set(gmage1)
        assertEquals(gmage, gmage1)

        assertThrows(IllegalArgumentException) {
            gmage.set(new Gmage(gmage.width, gmage.height + 1))
        }
        assertThrows(IllegalArgumentException) {
            gmage.set(new Gmage(gmage.width + 1, gmage.height))
        }
    }

    @Test
    void testPixelStream() {
        Color color = new Color(0xff00ff48)
        Gmage gmage = new Gmage(4, 6, color)
        gmage.pixels().forEach {
            assertEquals(color, it)
        }
    }

    @Test
    void testPadded() {
        Gmage gmage = new Gmage(10, 10)
        Gmage paddedGmage = gmage.padded(1, 2, 3, 4, Color.WHITE)
        assertEquals(gmage.width + 1 + 3, paddedGmage.width)
        assertEquals(gmage.height + 2 + 4, paddedGmage.height)

        for (y in 0..<paddedGmage.height) {
            for (x in 0..<paddedGmage.width) {
                def pixel = paddedGmage[x, y]
                if (x < 1 || x >= paddedGmage.width - 3) {
                    assertEquals(pixel, Color.WHITE, "should be white (x=$x)")
                } else if (y < 2 || y >= paddedGmage.height - 4) {
                    assertEquals(pixel, Color.WHITE, "should be white (y=$y)")
                } else {
                    assertEquals(pixel, Color.BLACK)
                }
            }
        }
    }

    @Test
    void testColorTransformer() {
        Gmage gmage = new Gmage(10, 10)
        gmage << {
            Color.WHITE
        }
        gmage.forEachPixel { Color pixel ->
            assertEquals(Color.WHITE, pixel)
        }

        gmage.apply {
            new Color(0xffff00)
        }
        gmage.forEachPixel { Color pixel ->
            assertEquals(new Color(0xffff00), pixel)
        }
    }

    @Test
    void testAnd() {
        Gmage gmage = new Gmage(10, 10, new Color(0x00ffff))
        Color green = new Color(0x00ff00)
        for (result in [gmage & new Color(0xffff00), gmage & 0xffff00, gmage & 0xffffff00])
            result.forEachPixel { Color pixel ->
            assertEquals(green, pixel)
        }
    }

    @Test
    void testOr() {
        Gmage gmage = new Gmage(10, 10, new Color(0x0f00f0))
        Color purple = new Color(0xff00ff)
        for (result in [gmage | new Color(0xf0000f), gmage | 0xf0000f, gmage | 0xfff0000f])
            result.forEachPixel { Color pixel ->
                assertEquals(purple, pixel)
            }
    }

    private static Gmage filledImage(int rgb) {
        int width = 64
        int height = 64
        return new Gmage(width, height, new Color(rgb))
    }
}
