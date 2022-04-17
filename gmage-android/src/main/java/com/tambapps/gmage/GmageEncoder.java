package com.tambapps.gmage;

import android.graphics.Bitmap;
import com.tambapps.gmage.bitmap.BitmapUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Class providing functions to encode a Gmage
 */
public class GmageEncoder {

  /**
   * tries to encode a Gmage in the given object if supported
   *
   * @param gmage  the gmage to encode
   * @param format the format in which to encode
   * @param object the object in which to encode to
   * @return whether the encoding succeeded or not
   * @throws IOException                   in case of I/O error
   * @throws UnsupportedOperationException in case the object is not supported
   */
  public static boolean encode(AbstractGmage gmage, CompressFormat format, Object object)
      throws IOException {
    if (object instanceof Path) {
      return encode(gmage, format, (Path) object);
    } else if (object instanceof File) {
      return encode(gmage, format, (File) object);
    } else if (object instanceof OutputStream) {
      return encode(gmage, format, (OutputStream) object);
    }
    throw new UnsupportedOperationException("Cannot encode gmage in " + object);
  }

  /**
   * encode a Gmage in the given path
   *
   * @param gmage  the gmage to encode
   * @param format the format in which to encode
   * @param path   the path in which to encode to
   * @return whether the encoding succeeded or not
   * @throws IOException in case of I/O error
   */
  public static boolean encode(AbstractGmage gmage, CompressFormat format, Path path) throws IOException {
    return encode(gmage, format, path.toFile());
  }

  /**
   * encode a Gmage in the file
   *
   * @param gmage  the gmage to encode
   * @param format the format in which to encode
   * @param file   the file in which to encode to
   * @return whether the encoding succeeded or not
   * @throws IOException in case of I/O error
   */
  public static boolean encode(AbstractGmage gmage, CompressFormat format, File file) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(file)) {
      return encode(gmage, format, fos);
    }
  }

  /**
   * encode a Gmage in the output stream
   *
   * @param gmage        the gmage to encode
   * @param format       the format in which to encode
   * @param outputStream the output stream in which to encode to
   * @return whether the encoding succeeded or not
   */
  public static boolean encode(AbstractGmage gmage, CompressFormat format, OutputStream outputStream) {
    Bitmap bitmap = BitmapUtils.fromGmage(gmage);
    return bitmap.compress(format.getBitmapFormat(), 100, outputStream);
  }

}
