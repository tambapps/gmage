package com.tambapps.gmage.blur;

import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;

// thanks https://tech-algorithm.com/articles/boxfiltering/
public class BoxBlur implements Blur {

  public static final Kernel UNFILTERED_KERNEL = new Kernel(0f, 0f, 0f, 0f, 1f, 0f, 0f ,0f, 0f);
  public static final Kernel SMOOTHING_KERNEL = new Kernel(1f, 1f, 1f, 1f, 2f, 1f, 1f, 1f, 1f);
  public static final Kernel SHARPENING_KERNEL = new Kernel(-1f, -1f, -1f, -1f, 9f, -1f, -1f, -1f, -1f);
  public static final Kernel RAISED_KERNEL = new Kernel(0f, 0f, -2f, 0f, 2f, 0f, 1f, 0f, 0f);
  public static final Kernel MOTION_BLUR_KERNEL = new Kernel(0f, 0f, -2f, 0f, 2f, 0f, 1f, 0f, 0f);
  public static final Kernel EDGE_DETECTION_KERNEL = new Kernel(-1f, -1f, -1f, -1f, 8f, -1f, -1f, -1f, -1f);

  public static class Kernel {
    private final float[] floats;
    public Kernel(float f0, float f1, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
      this.floats = new float[] {f0, f1, f2, f3, f4, f5, f6, f7, f8};
      System.arraycopy(floats, 0, this.floats, 0, floats.length);
    }
    private float getDenominator() {
      float denominator = 0.0f;
      for (int i = 0; i < floats.length; i++) {
        denominator += floats[i];
      }
      return denominator == 0.0f ? 1.0f : denominator;
    }

    private float get(int i) {
      return floats[i];
    }
    private int size() {
      return floats.length;
    }
  }
  private final Kernel kernel;

  public BoxBlur(Kernel kernel) {
    this.kernel = kernel;
  }

  @Override
  public Gmage apply(Gmage gmage) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    Color[] pixels = new Color[width*height];
    float denominator = kernel.getDenominator();
    float red, green, blue, alpha;
    int indexOffset;
    Color color;
    int[] indices = {
        -(width + 1),  -width,     -(width - 1),
        -1,                0,           +1,
        width - 1,      width,      width + 1
    };

    // this algorithm doesn't initialize borders, so we have to do it ourself
    for (int x = 0; x < width; x++) {
      // fill first row
      pixels[x] = gmage.getAt(x);
      // fill last row
      pixels[(height-1) * width + x] = gmage.getAt((height-1) * width + x);
    }

    for (int y = 0; y < height; y++) {
      // fill first column
      pixels[y * width] = gmage.getAt(y * width);
      // fill last column
      pixels[y * width + width - 1] = gmage.getAt(y * width + width - 1);
    }

    for (int y = 1; y < height-1; y++) {
      for (int x = 1; x < width-1; x++) {
        alpha = 0f;
        red = 0f;
        green = 0f;
        blue = 0f ;
        indexOffset = (y*width)+x ;
        for (int k = 0; k < kernel.size() ; k++) {
          color = gmage.getAt(indexOffset+indices[k]);
          float kernelValue = kernel.get(k);
          alpha+= color.getAlpha() * kernelValue;
          red+= color.getRed() * kernelValue;
          green+= color.getGreen() * kernelValue;
          blue+= color.getBlue() * kernelValue;
        }
        pixels[indexOffset] =
            new Color(bounded(alpha / denominator), bounded(red / denominator), bounded(green / denominator), bounded(blue / denominator)) ;
      }
    }
    return new Gmage(width, height, pixels);
  }

  private int bounded(float value) {
    if (value > 0xff) {
      return  0xff;
    } else if (value < 0) {
      return 0;
    } else {
      return (int) value;
    }
  }

}
