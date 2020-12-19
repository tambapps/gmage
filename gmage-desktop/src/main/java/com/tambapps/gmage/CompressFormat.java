package com.tambapps.gmage;


public enum CompressFormat {
  JPG("JPEG"), PNG("PNG"), WBMP("WBMP"), GIF("GIF"),
  BMP("BMP");

  private final String formatName;
  CompressFormat(String formatName) {
    this.formatName = formatName;
  }

  public String getFormatName() {
    return formatName;
  }

}
