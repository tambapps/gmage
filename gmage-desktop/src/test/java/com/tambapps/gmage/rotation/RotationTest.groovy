package com.tambapps.gmage.rotation

import com.tambapps.gmage.CompressFormat
import com.tambapps.gmage.Gmage
import com.tambapps.gmage.GmageDecoder
import com.tambapps.gmage.GmageDecoderTest
import com.tambapps.gmage.GmageEncoder
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertTrue

// testby looking at outputs
@Disabled
class RotationTest {

  @Test
  void testRotation90ClockWise() {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def rotated = gmage.rotated90ClockWise()
    assertTrue(GmageEncoder.encode(rotated, CompressFormat.JPG,
        new File("test_rotated90_clockwise.png")))
  }

  @Test
  void testRotation90CounterClockWise() {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def rotated = gmage.rotated90CounterClockWise()
    assertTrue(GmageEncoder.encode(rotated, CompressFormat.JPG,
        new File("test_rotated90_counter_clockwise.png")))
  }

  @Test
  void testRotation180() {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def rotated = gmage.rotated180()
    assertTrue(GmageEncoder.encode(rotated, CompressFormat.JPG,
        new File("test_rotated180.png")))
  }

}
