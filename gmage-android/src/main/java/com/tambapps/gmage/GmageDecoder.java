package com.tambapps.gmage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tambapps.gmage.bitmap.BitmapUtils;
import com.tambapps.gmage.exception.GmageDecodingException;

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
 * Class providing functions to decode a Gmage from many kinds of objects
 */
public final class GmageDecoder {

  private GmageDecoder() {
    throw new RuntimeException("You can't touch this");
  }

  /**
   * Tries to decode a Bitmap from an Object
   *
   * @param object the object to decode
   * @return the decoded Gmage
   * @throws UnsupportedOperationException in case the object could not be converted
   * @throws GmageDecodingException        in case an error occurred during the decoding
   */
  public static Gmage decode(Object object)
      throws UnsupportedOperationException, GmageDecodingException {
    if (object instanceof InputStream) {
      return decode((InputStream) object);
    } else if (object instanceof File) {
      return decode((File) object);
    } else if (object instanceof Path) {
      return decode((Path) object);
    } else if (object instanceof Bitmap) {
      return decode((Bitmap) object);
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
   * Decode Gmage from a Bitmap
   *
   * @param bitmap the bitmap to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(Bitmap bitmap) throws GmageDecodingException {
    // result of BitmapFactory.decodeSomething() is null if an error occurred
    if (bitmap == null) {
      throw new GmageDecodingException("Couldn't decode image");
    }
    return BitmapUtils.toGmage(bitmap);
  }

  /**
   * Decode Gmage from a string. The string can either be a file path, an URL or a Base64 string
   *
   * @param string the string to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(String string) throws GmageDecodingException {
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
    throw new GmageDecodingException("Couldn't decode \"" + string + "\"");
  }

  /**
   * Decode Gmage from an InputStream
   *
   * @param inputStream the input stream to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(InputStream inputStream) {
    return decode(BitmapFactory.decodeStream(inputStream));
  }

  /**
   * Decode Gmage from bytes
   *
   * @param objectBytes the bytes to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(Byte[] objectBytes) {
    byte[] bytes = new byte[objectBytes.length];
    for (int i = 0; i < objectBytes.length; i++) {
      bytes[i] = objectBytes[i];
    }
    return decode(bytes);
  }

  /**
   * Decode Gmage from bytes
   *
   * @param bytes the bytes to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(byte[] bytes) {
    return decode(new ByteArrayInputStream(bytes));
  }

  /**
   * Decode Gmage from a File
   *
   * @param file the file to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(File file) {
    return decode(BitmapFactory.decodeFile(file.getPath()));
  }

  /**
   * Decode Gmage from a Path
   *
   * @param path the path to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(Path path) {
    return decode(path.toFile());
  }

  /**
   * Decode Gmage from an URL
   *
   * @param url the url to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(URL url) {
    try {
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setDoInput(true);
      connection.connect();
      try {
        try (InputStream stream = connection.getInputStream()) {
          return decode(BitmapFactory.decodeStream(stream));
        }
      } finally {
        connection.disconnect();
      }
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode URL " + url, e);
    }
  }
}
