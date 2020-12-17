package com.tambapps.gmage;

import com.tambapps.gmage.exception.GmageDecodingException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
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

  public static Gmage parse(Object object) {
    if (object instanceof InputStream) {
      return parse((InputStream) object);
    } else if (object instanceof File) {
      return parse((File) object);
    } else if (object instanceof Path) {
      return parse((Path) object);
    } else if (object instanceof BufferedImage) {
      return parse((BufferedImage) object);
    } else if (object instanceof String) {
      return parse((String) object);
    } else if (object instanceof URL) {
      return parse((URL) object);
    } else if (object instanceof byte[]) {
      return parse((byte[]) object);
    }
    throw new IllegalArgumentException("Cannot decode gmage from " + object);
  }

  public static Gmage parse(BufferedImage image) {
    Raster raster = image.getData();
    int nbChannels = raster.getNumDataElements();
    switch (nbChannels) {
      case 1:
        return parseGrayImage(raster);
      case 4:
        return parseRGBAImage(raster);
      default:
        return parseRGBImage(raster);
    }
  }

  private static Gmage parseGrayImage(Raster raster) {
    Gmage gmage = new Gmage(raster.getWidth(), raster.getHeight());
    int[] pixel = new int[1];
    for (int y = 0; y < gmage.getHeight(); y++) {
      for (int x = 0; x < gmage.getWidth(); x++) {
        raster.getPixel(x, y, pixel);
        gmage.getAt(x, y).setRGB(pixel[0], pixel[0], pixel[0]);
      }
    }
    return gmage;
  }

  private static Gmage parseRGBImage(Raster raster) {
    Gmage gmage = new Gmage(raster.getWidth(), raster.getHeight());
    int[] pixel = new int[3];
    for (int y = 0; y < gmage.getHeight(); y++) {
      for (int x = 0; x < gmage.getWidth(); x++) {
        raster.getPixel(x, y, pixel);
        gmage.getAt(x, y).setRGB(pixel[0], pixel[1], pixel[2]);
      }
    }
    return gmage;
  }

  private static Gmage parseRGBAImage(Raster raster) {
    Gmage gmage = new Gmage(raster.getWidth(), raster.getHeight());
    int[] pixel = new int[4];
    for (int y = 0; y < gmage.getHeight(); y++) {
      for (int x = 0; x < gmage.getWidth(); x++) {
        raster.getPixel(x, y, pixel);
        gmage.getAt(x, y).setRGBA(pixel[0], pixel[1], pixel[2], pixel[3]);
      }
    }
    return gmage;
  }

  public static Gmage parse(String string) {
    File file = new File(string);
    if (file.exists()) {
      return parse(file);
    }
    try {
      return parse(new URL(string));
    } catch (MalformedURLException ignored) {
    }
    try {
      byte[] bytes = Base64.getDecoder().decode(string);
      return parse(bytes);
    } catch (IllegalArgumentException ignored) {
    }
    throw new IllegalArgumentException("Cannot decode gmage from " + string);
  }

  public static Gmage parse(InputStream inputStream) {
    try {
      return parse(ImageIO.read(inputStream));
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode gmage", e);
    }
  }

  public static Gmage parse(byte[] bytes) {
    return parse(new ByteArrayInputStream(bytes));
  }

  public static Gmage parse(File file) {
    try {
      return parse(ImageIO.read(file));
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode gmage", e);
    }
  }

  public static Gmage parse(Path path) {
    return parse(path.toFile());
  }

  public static Gmage parse(URL url) {
    try {
      return parse(ImageIO.read(url));
    } catch (IOException e) {
      throw new GmageDecodingException("Couldn't decode gmage", e);
    }
  }
}
