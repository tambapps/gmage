package com.tambapps.gmage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.tambapps.gmage.exception.GmageDecodingException;
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
    } else if (object instanceof Bitmap) {
      return decode((Bitmap) object);
    } else if (object instanceof String) {
      return decode((String) object);
    } else if (object instanceof URL) {
      return decode((URL) object);
    } else if (object instanceof byte[]) {
      return decode((byte[]) object);
    }
    throw new IllegalArgumentException("Cannot decode " + object);
  }

  public static Gmage decode(Bitmap image) {
    // result of BitmapFactory.decodeSomething() is null if an error occurred
    if (image == null) {
      throw new GmageDecodingException("Couldn't decode image");
    }
    int width = image.getWidth();
    int height = image.getHeight();
    Color[] colors = new Color[width * height];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int argb = image.getPixel(x, y);
        int alpha = argb & 0xff000000;
        int rgb = argb & 0xffffff;
        colors[Gmage.getIndex(x, y, width)] = new Color(rgb, alpha);
      }
    }
    return new Gmage(width, height, colors);
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
    throw new IllegalArgumentException("Couldn't decode \"" + string + "\"");
  }

  public static Gmage decode(InputStream inputStream) {
    return decode(BitmapFactory.decodeStream(inputStream));
  }

  public static Gmage decode(byte[] bytes) {
    return decode(new ByteArrayInputStream(bytes));
  }

  public static Gmage decode(File file) {
    return decode(BitmapFactory.decodeFile(file.getPath()));
  }

  public static Gmage decode(Path path) {
    return decode(path.toFile());
  }

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
