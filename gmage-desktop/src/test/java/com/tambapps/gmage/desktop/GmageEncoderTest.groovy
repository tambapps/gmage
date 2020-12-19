package com.tambapps.gmage.desktop

import com.tambapps.gmage.CompressFormat
import com.tambapps.gmage.Gmage
import com.tambapps.gmage.GmageDecoder
import com.tambapps.gmage.GmageEncoder
import com.tambapps.gmage.color.Color
import com.tambapps.gmage.scaling.Scaling
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import java.nio.file.Files

import static org.junit.jupiter.api.Assertions.assertEquals
import static org.junit.jupiter.api.Assertions.assertTrue

class GmageEncoderTest {

  // not very precise :(
  private static final int PRECISION_THRESHOLD = 60

  @Test
  void testJpgEncoding() {
    Gmage gmage = GmageDecoder.decode(GmageEncoderTest.class.getResource("/ronflex.jpg"))
    File f = Files.createTempFile("image", ".jpg").toFile()
    f.deleteOnExit()
    assertTrue(GmageEncoder.encode(gmage, CompressFormat.JPG, f))
    Gmage gmage1 = GmageDecoder.decode(f)
    assertEquals(gmage.width, gmage1.width)
    assertEquals(gmage.height, gmage1.height)

    // apparently jpg encoding is not exact, some precision is lost in colors
    //assertEquals(gmage, gmage1)
    for (i in 0..<gmage.width * gmage.height) {
      assertAlmostEquals(gmage[i], gmage1[i])
    }
  }

  @Test
  void testGifEncoding() {
    Gmage gmage = GmageDecoder.decode(GmageEncoderTest.class.getResource("/ronflex.jpg"))
    File f = Files.createTempFile("image", ".gif").toFile()
    f.deleteOnExit()
    assertTrue(GmageEncoder.encode(gmage, CompressFormat.GIF, f))
    Gmage gmage1 = GmageDecoder.decode(f)
    assertEquals(gmage.width, gmage1.width)
    assertEquals(gmage.height, gmage1.height)
    // same here, precision lost
    for (i in 0..<gmage.width * gmage.height) {
      assertAlmostEquals(gmage[i], gmage1[i])
    }
  }

  // 100% accurate
  @Test
  void testBmpEncoding() {
    Gmage gmage = GmageDecoder.decode(GmageEncoderTest.class.getResource("/ronflex.jpg"))
    File f = Files.createTempFile("image", ".bmp").toFile()
    f.deleteOnExit()
    assertTrue(GmageEncoder.encode(gmage, CompressFormat.BMP, f))
    Gmage gmage1 = GmageDecoder.decode(f)
    assertEquals(gmage.width, gmage1.width)
    assertEquals(gmage.height, gmage1.height)
    for (i in 0..<gmage.width * gmage.height) {
      assertEquals(gmage[i], gmage1[i])
    }
    assertEquals(gmage, gmage1)
  }

  // 100% accurate
  @Test
  void testPngEncoding() {
    Gmage gmage = GmageDecoder.decode(GmageEncoderTest.class.getResource("/ronflex.jpg"))
    File f = Files.createTempFile("image", ".png").toFile()
    f.deleteOnExit()
    assertTrue(GmageEncoder.encode(gmage, CompressFormat.PNG, f))
    Gmage gmage1 = GmageDecoder.decode(f)
    assertEquals(gmage.width, gmage1.width)
    assertEquals(gmage.height, gmage1.height)
    for (i in 0..<gmage.width * gmage.height) {
      assertEquals(gmage[i], gmage1[i])
    }
    assertEquals(gmage, gmage1)
  }

  // test by seeing the output file
  @Test
  @Disabled
  void testScalingBigger() {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def scaled = gmage.scaleBy(Scaling.NEAREST_NEIGHBOR, 1.5, 2)
    GmageEncoder.encode(scaled, CompressFormat.PNG, new File("test.png"))
    assertEquals((gmage.width * 1.5f) as int, scaled.width)
    assertEquals((gmage.height * 2f) as int, scaled.height)
  }

  @Test
  @Disabled
  void testScalingSmaller() {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def scaled = gmage.scaleBy(Scaling.NEAREST_NEIGHBOR, 0.75, 0.5)
    GmageEncoder.encode(scaled,
        CompressFormat.PNG, new File("test.png"))
    assertEquals((gmage.width * 0.75f) as int, scaled.width)
    assertEquals((gmage.height * 0.5f) as int, scaled.height)
  }

  private static void assertAlmostEquals(Color c1, Color c2) {
    assertTrue(Math.abs(c1.alpha - c2.alpha) < PRECISION_THRESHOLD, "$c1 != $c2 (on alpha)")
    assertTrue(Math.abs(c1.red - c2.red) < PRECISION_THRESHOLD, "$c1 != $c2 (on red)")
    assertTrue(Math.abs(c1.green - c2.green) < PRECISION_THRESHOLD, "$c1 != $c2 (on green)")
    assertTrue(Math.abs(c1.blue - c2.blue) < PRECISION_THRESHOLD, "$c1 != $c2 (on blue)")
  }
}