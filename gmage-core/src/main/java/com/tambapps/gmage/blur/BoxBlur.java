package com.tambapps.gmage.blur;

import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;

// thanks https://tech-algorithm.com/articles/boxfiltering/
public class BoxBlur implements Blur {

  private final float[] kernel;

  public BoxBlur(float[] kernel) {
    this.kernel = checkedKernel(kernel);
  }

  @Override
  public Gmage apply(Gmage gmage) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    Color[] pixels = new Color[width*height];
    float denominator = 0.0f;
    float red, green, blue, alpha;
    int indexOffset;
    Color color;
    int[] indices = {
        -(width + 1),  -width,     -(width - 1),
        -1,                0,           +1,
        width - 1,      width,      width + 1
    };
    for (int i = 0; i < kernel.length; i++) {
      denominator += kernel[i];
    }
    if (denominator == 0.0f) {
      denominator = 1.0f;
    }

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
        for (int k = 0; k < kernel.length ; k++) {
          color = gmage.getAt(indexOffset+indices[k]);
          alpha+= color.getAlpha() * kernel[k];
          red+= color.getRed() * kernel[k];
          green+= color.getGreen() * kernel[k];
          blue+= color.getBlue() * kernel[k];
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

  private float[] checkedKernel(float[] kernel) {
    if (kernel.length < 3) {
      throw new IllegalArgumentException("Kernel must be have at a length of 9 (3 * 3)");
    }
    return kernel;
  }
}
