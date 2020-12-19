package com.tambapps.gmage;


public enum CompressFormat {
  JPG("jpg", false), PNG("png", true), GIF("gif",
      true),
  BMP("bmp", true);

  private final String formatName;
  private final boolean supportAlpha;
  CompressFormat(String formatName, boolean supportAlpha) {
    this.formatName = formatName;
    this.supportAlpha = supportAlpha;
  }

  public String getFormatName() {
    return formatName;
  }

  public boolean supportsAlpha() {
    return supportAlpha;
  }
}
