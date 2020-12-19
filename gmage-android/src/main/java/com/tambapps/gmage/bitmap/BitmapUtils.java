package com.tambapps.gmage.bitmap;

import android.graphics.Bitmap;
import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;

public final class BitmapUtils {

  private BitmapUtils() {}

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
}
