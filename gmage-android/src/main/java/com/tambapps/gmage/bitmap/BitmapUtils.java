package com.tambapps.gmage.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tambapps.gmage.AbstractGmage;
import com.tambapps.gmage.Gmage;
import com.tambapps.gmage.color.Color;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Base64;

/**
 * Class containing useful functions to convert Bitmap from/to Gmage
 */
public final class BitmapUtils {

  private BitmapUtils() {
  }

  /**
   * Tries to decode a Bitmap from an Object
   *
   * @param object the object to decode
   * @return the decoded Bitmap
   * @throws UnsupportedOperationException in case the object could not be converted
   * @throws IOException        in case an error occurred during the decoding
   */
  public static Bitmap decodeFrom(Object object) throws IOException {
    if (object instanceof InputStream) {
      return decode((InputStream) object);
    } else if (object instanceof File) {
      return decode((File) object);
    } else if (object instanceof Path) {
      return decode((Path) object);
    } else if (object instanceof Bitmap) {
      return (Bitmap) object;
    } else if (object instanceof String) {
      return decode((String) object);
    } else if (object instanceof URL) {
      return decode((URL) object);
    } else if (object instanceof Byte[]) {
      return decode((Byte[]) object);
    } else if (object instanceof byte[]) {
      return decode((byte[]) object);
    }
    throw new UnsupportedOperationException("Cannot decode " + object);
  }

  /**
   * Decode Bitmap from a string. The string can either be a file path, an URL or a Base64 string
   *
   * @param string the string to decode
   * @return the decoded Bitmap
   * @throws IOException in case an error occurred during the decoding
   */
  public static Bitmap decode(String string) throws IOException {
    File file = new File(string);
    if (file.exists()) {
      return decode(file);
    }
    try {
      return decode(new URL(string));
    } catch (MalformedURLException ignored) {
    }
    try {
      byte[] bytes = Base64.getDecoder().decode(string);
      return decode(bytes);
    } catch (IllegalArgumentException ignored) {
    }
    throw new IOException("Couldn't decode \"" + string + "\"");
  }

  /**
   * Decode Bitmap from an InputStream
   *
   * @param inputStream the input stream to decode
   * @return the decoded Bitmap
   */
  public static Bitmap decode(InputStream inputStream) {
    return BitmapFactory.decodeStream(inputStream);
  }

  /**
   * Decode Bitmap from bytes
   *
   * @param objectBytes the bytes to decode
   * @return the decoded Bitmap
   */
  public static Bitmap decode(Byte[] objectBytes) {
    byte[] bytes = new byte[objectBytes.length];
    for (int i = 0; i < objectBytes.length; i++) {
      bytes[i] = objectBytes[i];
    }
    return decode(bytes);
  }

  /**
   * Decode Bitmap from bytes
   *
   * @param bytes the bytes to decode
   * @return the decoded Bitmap
   */
  public static Bitmap decode(byte[] bytes) {
    return decode(new ByteArrayInputStream(bytes));
  }

  /**
   * Decode Bitmap from a File
   *
   * @param file the file to decode
   * @return the decoded Bitmap
   */
  public static Bitmap decode(File file) {
    return BitmapFactory.decodeFile(file.getPath());
  }

  /**
   * Decode Bitmap from a Path
   *
   * @param path the path to decode
   * @return the decoded Bitmap
   */
  public static Bitmap decode(Path path) {
    return decode(path.toFile());
  }

  /**
   * Decode Bitmap from an URL
   *
   * @param url the url to decode
   * @return the decoded Bitmap
   * @throws IOException in case an error occurred during the decoding
   */
  public static Bitmap decode(URL url) throws IOException {
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setDoInput(true);
    connection.connect();
    try {
      try (InputStream stream = connection.getInputStream()) {
        return BitmapFactory.decodeStream(stream);
      }
    } finally {
      connection.disconnect();
    }
  }

  /**
   * Converts a Gmage to a Bitmap
   *
   * @param gmage the Gmage to convert
   * @return the converted Bitmap
   */
  public static Bitmap fromGmage(AbstractGmage gmage) {
    int width = gmage.getWidth();
    int height = gmage.getHeight();
    Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        Color color = gmage.getAt(x, y);
        bmp.setPixel(x, y, toPixel(color));
      }
    }
    return bmp;
  }

  /**
   * Converts a Bitmap to a Gmage
   *
   * @param bitmap the Bitmap to convert
   * @return the converted Gmage
   */
  public static Gmage toGmage(Bitmap bitmap) {
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();
    Color[] colors = new Color[width * height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        colors[y * width + x] = toColor(bitmap.getPixel(x, y));
      }
    }
    return new Gmage(width, height, colors);
  }

  public static Color toColor(int argb) {
    int alpha = argb >>> 24;
    int rgb = argb & 0xffffff;
    return new Color(rgb, alpha);
  }

  public static int toPixel(Color color) {
    return (int) color.getArgb();
  }
}
