package com.tambapps.gmage

import com.tambapps.gmage.pixel.Color

import static org.junit.jupiter.api.Assertions.assertNotNull

import org.junit.jupiter.api.Test

class GmageTest extends AbstractGmageTest {

    @Test
    void test() {
        Gmage gmage = new Gmage(2, 2)
        assertNotNull(gmage)
    }

    @Override
    protected Gmage filledImage(int rgb) {
        int width = 64
        int height = 64
        return new Gmage(width, height, new Color(rgb))
    }
}
