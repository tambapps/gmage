package com.tambapps.gmage;

import android.graphics.Bitmap;
import com.tambapps.gmage.bitmap.BitmapUtils;

import java.io.OutputStream;

public class GmageEncoder {

  public static boolean encode(Gmage gmage, OutputStream outputStream, CompressFormat format) {
    Bitmap bitmap = BitmapUtils.toBitmap(gmage);
    return bitmap.compress(format.getBitmapFormat(), 100, outputStream);
  }

}
