package com.tambapps.gmage;

import android.graphics.Bitmap;

public enum CompressFormat {
  JPG(Bitmap.CompressFormat.JPEG), PNG(Bitmap.CompressFormat.PNG), WEBP(Bitmap.CompressFormat.WEBP);

  private final Bitmap.CompressFormat format;
  CompressFormat(Bitmap.CompressFormat format) {
    this.format = format;
  }

  public Bitmap.CompressFormat getBitmapFormat() {
    return format;
  }
}
