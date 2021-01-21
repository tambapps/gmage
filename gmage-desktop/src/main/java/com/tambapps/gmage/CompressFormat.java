package com.tambapps.gmage;

/**
 * Enum representing supported image format by the Java SDK BufferedImage
 */
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

  /**
   * The name of the format (suffix of file names)
   *
   * @return the name of the format
   */
  public String getFormatName() {
    return formatName;
  }

  /**
   * Whether this format supports alpha channel or not
   *
   * @return whether this format supports alpha channel or not
   */
  public boolean supportsAlpha() {
    return supportAlpha;
  }
}
