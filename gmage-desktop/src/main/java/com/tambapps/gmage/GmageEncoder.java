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

  public static boolean encode(Gmage gmage, CompressFormat format, OutputStream outputStream) throws IOException {
    BufferedImage image = format.supportsAlpha() ? BufferedImageUtils.toBufferedImage(gmage, BufferedImage.TYPE_INT_RGB) : BufferedImageUtils.toBufferedImage(gmage);
    return ImageIO.write(image, format.getFormatName(), outputStream);
  }

}
