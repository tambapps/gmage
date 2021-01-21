package com.tambapps.gmage.bitmap;

import android.graphics.Bitmap;
import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;

/**
 * Class containing useful functions to convert Bitmap from/to Gmage
 */
public final class BitmapUtils {

  private BitmapUtils() {}

  /**
   * Converts a Gmage to a Bitmap
   * @param gmage the Gmage to convert
   * @return the converted Bitmap
   */
  public static Bitmap toBitmap(Gmage gmage) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color pixel = gmage.getAt(x, y);
        bmp.setPixel(x, y, (int) pixel.getArgb());
      }
    }
    return bmp;
  }

  /**
   * Converts a Bitmap to a Gmage
   * @param bitmap the Bitmap to convert
   * @return the converted Gmage
   */
  public static Gmage toGmage(Bitmap bitmap) {
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    Color[] colors = new Color[width * height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int argb = bitmap.getPixel(x, y);
        int alpha = argb >>> 24;
        int rgb = argb & 0xffffff;
        colors[y * width + x] = new Color(rgb, alpha);
      }
    }
    return new Gmage(width, height, colors);
  }
}
