package com.tambapps.gmage;

import android.graphics.Bitmap;
import com.tambapps.gmage.bitmap.BitmapUtils;
import com.tambapps.gmage.color.Color;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public class Gmage extends BaseGmage {

  public static Gmage from(Object o) {
    return GmageDecoder.decodeFrom(o);
  }

  public Gmage(int width, int height, Color[] pixels) {
    super(width, height, pixels);
  }

  public Gmage(int width, int height) {
    super(width, height);
  }

  public Gmage(int width, int height, Color defaultColor) {
    super(width, height, defaultColor);
  }

  public void rightShift(OutputStream os) throws IOException {
    encode(os, CompressFormat.PNG);
  }

  public void rightShift(File file) throws IOException {
    CompressFormat format;
    // to ignore case
    String name = file.getName().toLowerCase(Locale.ENGLISH);
    if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
      format = CompressFormat.JPG;
    } else if (name.endsWith(".webp")) {
      format = CompressFormat.WEBP;
    } else {
      format = CompressFormat.PNG;
    }
    encode(file, format);
  }

  public void encode(OutputStream os, CompressFormat format) throws IOException {
    GmageEncoder.encode(this, format, os);
  }

  public void encode(File file, CompressFormat format) throws IOException {
    GmageEncoder.encode(this, format, file);
  }

  public Bitmap toBitmap() {
    return BitmapUtils.fromGmage(this);
  }

  @Override
  public Gmage copy() {
    return new Gmage(width, height, pixels);
  }

  @Override
  public Gmage newInstance(int width, int height) {
    return new Gmage(width, height);
  }

  @Override
  public Gmage newInstance(int width, int height, Color[] pixels) {
    return new Gmage(width, height, pixels);
  }
}
