package com.tambapps.gmage

import static org.junit.jupiter.api.Assertions.assertNotNull

import org.junit.jupiter.api.Test

class GmageTest {

    @Test
    void test() {
        Gmage gmage = new Gmage(2, 2)
        assertNotNull(gmage)
    }
}
