package com.tambapps.gmage.scaling

import com.tambapps.gmage.CompressFormat
import com.tambapps.gmage.Gmage
import com.tambapps.gmage.GmageDecoder
import com.tambapps.gmage.GmageDecoderTest
import com.tambapps.gmage.GmageEncoder
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

// test by seeing the output file
@Disabled
class ScalingTest {

  @Test
  void testNearestNeighbourBigger() {
    testScalingBigger(Scaling.NEAREST_NEIGHBOR)
  }

  @Test
  void testNearestNeighbourSmaller() {
    testScalingSmaller(Scaling.NEAREST_NEIGHBOR)
  }

  @Test
  void testBilinearInterpolationBigger() {
    testScalingBigger(Scaling.BILINEAR_INTERPOLATION)
  }

  @Test
  void testBilinearInterpolationSmaller() {
    testScalingSmaller(Scaling.BILINEAR_INTERPOLATION)
  }

  private static void testScalingBigger(Scaling scaling) {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def scaled = gmage.scaleBy(scaling, 1.5, 2)
    GmageEncoder.encode(scaled, CompressFormat.PNG, new File("test_${scaling.name().toLowerCase()}_bigger.png"))
    assertEquals((gmage.width * 1.5f) as int, scaled.width)
    assertEquals((gmage.height * 2f) as int, scaled.height)
  }

  private static void testScalingSmaller(Scaling scaling) {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def scaled = gmage.scaleBy(scaling, 0.75, 0.5)
    GmageEncoder.encode(scaled,
        CompressFormat.PNG, new File("test_${scaling.name().toLowerCase()}_smaller.png"))
    assertEquals((gmage.width * 0.75f) as int, scaled.width)
    assertEquals((gmage.height * 0.5f) as int, scaled.height)
  }
}
