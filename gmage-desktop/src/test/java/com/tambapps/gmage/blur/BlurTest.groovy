package com.tambapps.gmage.blur

import com.tambapps.gmage.CompressFormat
import com.tambapps.gmage.AbstractGmage
import com.tambapps.gmage.GmageDecoder
import com.tambapps.gmage.GmageDecoderTest
import com.tambapps.gmage.GmageEncoder
import com.tambapps.gmage.blur.BoxBlur.Kernel
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertEquals

// test by seeing the output file
@Disabled
class BlurTest {

  // does nothing
  @Test
  void testBoxFilterOriginal() {
    testBoxBlur(BoxBlur.UNFILTERED_KERNEL, "Original")
  }

  @Test
  void testBoxFilterSmoothing() {
    testBoxBlur(BoxBlur.SMOOTHING_KERNEL, "Smoothing")
  }

  @Test
  void testBoxFilterSharpening() {
    testBoxBlur(BoxBlur.SHARPENING_KERNEL, "Sharpening")
  }

  @Test
  void testBoxFilterRaised() {
    testBoxBlur(BoxBlur.RAISED_KERNEL, "Raised")
  }

  @Test
  void testBoxFilterMotionBlur() {
    testBoxBlur(BoxBlur.MOTION_BLUR_KERNEL, "Motion-Blur")
  }

  @Test
  void testBoxFilterEdgeDetection() {
    testBoxBlur(BoxBlur.EDGE_DETECTION_KERNEL, "Edge_Detection")
  }

  @Test
  void testPixelationBlur() {
    AbstractGmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    int pixelSize = gmage.width.intdiv(10)
    def blur = new PixelationBlur(pixelSize)
    AbstractGmage gmage1 = gmage.copy()
    blur.applyOn(gmage1)
    assertEquals(gmage1, blur.apply(gmage))
    GmageEncoder.encode(gmage1, CompressFormat.PNG, new File("test_pixelation_${pixelSize}.png"))
  }

  private static void testBoxBlur(Kernel kernel, String blurName) {
    AbstractGmage gmage = GmageDecoder.decode(GmageDecoderTest.class.getResource("/ronflex.jpg"))
    def blur = new BoxBlur(kernel)
    def blurred = gmage.blurred(blur)
    GmageEncoder.encode(blurred, CompressFormat.PNG, new File("test_box_blur_${blurName}.png"))
    assertEquals(gmage.width, blurred.width)
    assertEquals(gmage.height, blurred.height)
  }

}
