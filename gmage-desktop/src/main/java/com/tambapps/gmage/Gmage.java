package com.tambapps.gmage;

import com.tambapps.gmage.bi.BufferedImageUtils;
import com.tambapps.gmage.color.Color;
import lombok.AllArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
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
    } else if (name.endsWith(".gif")) {
      format = CompressFormat.GIF;
    }  else if (name.endsWith(".bmp")) {
      format = CompressFormat.BMP;
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

  public void show() {
    JFrame frame = new JFrame("Gmage");
    frame.setSize(getWidth(), getHeight());
    frame.getContentPane().add(new GmageComponent(BufferedImageUtils.fromGmage(this)));

    frame.setVisible(true);
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

  @AllArgsConstructor
  private static class GmageComponent extends JPanel {

    private final BufferedImage image;

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      //g.drawImage(img, 0, 0, null);
      g.drawImage(image, 0, 0, this);
    }
  }
}
