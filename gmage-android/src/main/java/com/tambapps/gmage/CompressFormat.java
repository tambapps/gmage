package com.tambapps.gmage;

import android.graphics.Bitmap;

/**
 * Enum representing supported image format by the Android SDK Bitmap
 */
public enum CompressFormat {
  JPG(Bitmap.CompressFormat.JPEG), PNG(Bitmap.CompressFormat.PNG), WEBP(Bitmap.CompressFormat.WEBP);

  private final Bitmap.CompressFormat format;
  CompressFormat(Bitmap.CompressFormat format) {
    this.format = format;
  }

  /**
   * The corresponding Bitmap compress format
   * @return the corresponding Bitmap compress format
   */
  public Bitmap.CompressFormat getBitmapFormat() {
    return format;
  }
}
