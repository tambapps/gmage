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

public final class GmageDecoder {

  private GmageDecoder() {
    throw new RuntimeException("You can't touch this");
  }

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
    } else if (object instanceof byte[]) {
      return decode((byte[]) object);
    }
    throw new IllegalArgumentException("Cannot decode gmage from " + object);
  }

  public static Gmage decode(BufferedImage image) {
    return BufferedImageUtils.toGmage(image);
  }

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
    throw new IllegalArgumentException("Cannot decode gmage from " + string);
  }

  public static Gmage decode(InputStream inputStream) {
    try {
      return decode(ImageIO.read(inputStream));
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode gmage", e);
    }
  }

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

  public static Gmage decode(Path path) {
    return decode(path.toFile());
  }

  public static Gmage decode(URL url) {
    try {
      return decode(ImageIO.read(url));
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode gmage", e);
    }
  }
}
