package com.tambapps.gmage;

import com.tambapps.gmage.bi.BufferedImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;

public class GmageEncoder {

  public static void encode(Gmage gmage, CompressFormat format, Object object)
      throws IOException {
    if (object instanceof Path) {
      encode(gmage, format, (Path) object);
    } else if (object instanceof File) {
      encode(gmage, format, (File) object);
    } else if (object instanceof OutputStream) {
      encode(gmage, format, (OutputStream) object);
    }
    throw new IllegalArgumentException("Cannot encode gmage in " + object);
  }

  public static void encode(Gmage gmage, CompressFormat format, Path path) throws IOException {
    encode(gmage, format, path.toFile());
  }

  public static void encode(Gmage gmage, CompressFormat format, File file) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(file)) {
      encode(gmage, format, fos);
    }
  }

  public static void encode(Gmage gmage, CompressFormat format, OutputStream outputStream) throws IOException {
    BufferedImage image = BufferedImageUtils.toBufferedImage(gmage);
    ImageIO.write(image, format.getFormatName(), outputStream);
  }

}
