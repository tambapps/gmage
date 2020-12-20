package com.tambapps.gmage.blur

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
class BlurTest {

  // does nothing
  @Test
  void testBoxFilterOriginal() {
    testBoxBlur([0f, 0f, 0f, 0f, 1f, 0f, 0f ,0f, 0f], "Original")
  }

  @Test
  void testBoxFilterSmoothing() {
    testBoxBlur([1f, 1f, 1f, 1f, 2f, 1f, 1f, 1f, 1f], "Smoothing")
  }

  @Test
  void testBoxFilterSharpening() {
    testBoxBlur([-1f, -1f, -1f, -1f, 9f, -1f, -1f, -1f, -1f], "Sharpening")
  }

  @Test
  void testBoxFilterRaised() {
    testBoxBlur([0f, 0f, -2f, 0f, 2f, 0f, 1f, 0f, 0f], "Raised")
  }

  @Test
  void testBoxFilterMotionBlur() {
    testBoxBlur([0f, 0f, -2f, 0f, 2f, 0f, 1f, 0f, 0f], "Motion-Blur")
  }

  @Test
  void testBoxFilterEdgeDetection() {
    testBoxBlur([-1f, -1f, -1f, -1f, 8f, -1f, -1f, -1f, -1f], "Edge_Detection")
  }

  private static void testBoxBlur(List<Float> kernel, String blurName) {
    Gmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def blur = new BoxBlur(kernel as float[])
    def blurred = gmage.blurred(blur)
    GmageEncoder.encode(blurred, CompressFormat.PNG, new File("test_box_blur_${blurName}.png"))
    assertEquals(gmage.width, blurred.width)
    assertEquals(gmage.height, blurred.height)
  }

}
