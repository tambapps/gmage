package com.tambapps.gmage;

import android.graphics.Bitmap;
import com.tambapps.gmage.bitmap.BitmapUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class GmageEncoder {

  public static boolean encode(Gmage gmage, CompressFormat format, Object object)
      throws IOException {
    if (object instanceof Path) {
      return encode(gmage, format, (Path) object);
    } else if (object instanceof File) {
      return encode(gmage, format, (File) object);
    } else if (object instanceof OutputStream) {
      return encode(gmage, format, (OutputStream) object);
    }
    throw new IllegalArgumentException("Cannot encode gmage in " + object);
  }

  public static boolean encode(Gmage gmage, CompressFormat format, Path path) throws IOException {
    return encode(gmage, format, path.toFile());
  }

  public static boolean encode(Gmage gmage, CompressFormat format, File file) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(file)) {
      return encode(gmage, format, fos);
    }
  }

  public static boolean encode(Gmage gmage, CompressFormat format, OutputStream outputStream) {
    Bitmap bitmap = BitmapUtils.toBitmap(gmage);
    return bitmap.compress(format.getBitmapFormat(), 100, outputStream);
  }

}
