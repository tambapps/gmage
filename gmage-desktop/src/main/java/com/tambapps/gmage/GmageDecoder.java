package com.tambapps.gmage;

import com.tambapps.gmage.bi.BufferedImageUtils;
import com.tambapps.gmage.exception.GmageDecodingException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
  public static Gmage decode(Object object) {
    if (object instanceof InputStream) {
      return decode((InputStream) object);
    } else if (object instanceof File) {
      return decode((File) object);
    } else if (object instanceof Path) {
      return decode((Path) object);
    } else if (object instanceof BufferedImage) {
      return decode((BufferedImage) object);
    } else if (object instanceof String) {
      return decode((String) object);
    } else if (object instanceof URL) {
      return decode((URL) object);
    } else if (object instanceof Byte[]) {
      return decode((Byte[]) object);
    } else if (object instanceof byte[]) {
      return decode((byte[]) object);
    }
    throw new UnsupportedOperationException("Cannot decode gmage from " + object);
  }

  /**
   * Decode Gmage from a BufferedImage
   *
   * @param image the image to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(BufferedImage image) {
    return BufferedImageUtils.toGmage(image);
  }

  /**
   * Decode Gmage from a string. The string can either be a file path, an URL or a Base64 string
   *
   * @param string the string to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(String string) {
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
    throw new GmageDecodingException("Cannot decode gmage from " + string);
  }

  /**
   * Decode Gmage from an InputStream
   *
   * @param inputStream the input stream to decode
   * @return the decoded Gmage
   * @throws GmageDecodingException in case an error occurred during the decoding
   */
  public static Gmage decode(InputStream inputStream) {
    try {
      return decode(ImageIO.read(inputStream));
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode gmage", e);
    }
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

  public static Gmage decode(File file) {
    try {
      return decode(ImageIO.read(file));
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode gmage", e);
    }
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
      return decode(ImageIO.read(url));
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode gmage", e);
    }
  }
}
