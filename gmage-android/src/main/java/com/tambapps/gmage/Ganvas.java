package com.tambapps.gmage;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.graphics.text.MeasuredText;
import com.tambapps.gmage.bitmap.BitmapUtils;
import com.tambapps.gmage.color.Color;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.function.BiConsumer;

public class Ganvas extends AbstractGmage {

  @Getter
  private final Bitmap bitmap;
  private final Canvas canvas;

  public static Ganvas from(Object o) throws IOException, UnsupportedOperationException {
    return new Ganvas(BitmapUtils.decodeFrom(o));
  }

  public Ganvas(Bitmap bitmap) {
    this.bitmap = bitmap;
    this.canvas = new Canvas(bitmap);
  }

  public void draw(Paint paint) {
    canvas.drawBitmap(bitmap, 0f, 0f, paint);
  }

  private void draw(Paint paint, BiConsumer<Canvas, Paint> drawer) {
    drawer.accept(canvas, paint);
  }

  @Override
  public int getWidth() {
    return bitmap.getWidth();
  }

  @Override
  public Color getAt(int i) {
    int x = i / getWidth();
    int y = i % getWidth();
    return getAt(x, y);
  }

  @Override
  public Color getAt(int x, int y) {
    return BitmapUtils.toColor(bitmap.getPixel(x, y));
  }

  public Bitmap toBitmap() {
    return BitmapUtils.fromGmage(this);
  }

  @Override
  public Ganvas copy() {
    return new Ganvas(bitmap);
  }

  @Override
  public void putAt(int oneDIndex, Color value) {
    int x = oneDIndex / getWidth();
    int y = oneDIndex % getWidth();
    putColor(x, y, value);
  }

  @Override
  public void putColor(int x, int y, Color value) {
    bitmap.setPixel(x, y, BitmapUtils.toPixel(value));
  }

  @Override
  public Ganvas newInstance(int width, int height) {
    return new Ganvas(Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888));
  }

  @Override
  public Ganvas newInstance(int width, int height, Color[] pixels) {
    Gmage gmage = new Gmage(width, height);
    return new Ganvas(BitmapUtils.fromGmage(gmage));
  }

  @Override
  public int getHeight() {
    return bitmap.getHeight();
  }

  public void rightShift(OutputStream os) throws IOException {
    encode(os, CompressFormat.PNG);
  }

  public void rightShift(File file) throws IOException {
    encode(file, CompressFormat.PNG);
  }

  public void encode(OutputStream os, CompressFormat format) throws IOException {
    bitmap.compress(format.getBitmapFormat(), 100, os);
  }

  public void encode(File file, CompressFormat format) throws IOException {
    try (FileOutputStream fos = new FileOutputStream(file)) {
      bitmap.compress(format.getBitmapFormat(), 100, fos);
    }
  }
  
  // draw methods

  public void drawPicture(Picture picture) {
    canvas.drawPicture(picture);
  }

  public void drawPicture(Picture picture, RectF dst) {
    canvas.drawPicture(picture, dst);
  }

  public void drawPicture(Picture picture, Rect dst) {
    canvas.drawPicture(picture, dst);
  }

  public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint) {
    canvas.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
  }

  public void drawArc(float left, float top, float right, float bottom, float startAngle, float sweepAngle, boolean useCenter, Paint paint) {
    canvas.drawArc(left, top, right, bottom, startAngle, sweepAngle, useCenter, paint);
  }

  public void drawARGB(int a, int r, int g, int b) {
    canvas.drawARGB(a, r, g, b);
  }

  public void drawBitmap(Bitmap bitmap, float left, float top, Paint paint) {
    canvas.drawBitmap(bitmap, left, top, paint);
  }

  public void drawBitmap(Bitmap bitmap, Rect src, RectF dst, Paint paint) {
    canvas.drawBitmap(bitmap, src, dst, paint);
  }

  public void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint) {
    canvas.drawBitmap(bitmap, src, dst, paint);
  }

  public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint) {
    canvas.drawBitmap(bitmap, matrix, paint);
  }

  public void drawBitmapMesh(Bitmap bitmap, int meshWidth, int meshHeight, float[] verts, int vertOffset, int[] colors, int colorOffset, Paint paint) {
    canvas.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, vertOffset, colors, colorOffset, paint);
  }

  public void drawCircle(float cx, float cy, float radius, Paint paint) {
    canvas.drawCircle(cx, cy, radius, paint);
  }

  public void drawLine(float startX, float startY, float stopX, float stopY, Paint paint) {
    canvas.drawLine(startX, startY, stopX, stopY, paint);
  }

  public void drawLines(float[] pts, int offset, int count, Paint paint) {
    canvas.drawLines(pts, offset, count, paint);
  }

  public void drawLines(float[] pts, Paint paint) {
    canvas.drawLines(pts, paint);
  }

  public void drawOval(RectF oval, Paint paint) {
    canvas.drawOval(oval, paint);
  }

  public void drawOval(float left, float top, float right, float bottom, Paint paint) {
    canvas.drawOval(left, top, right, bottom, paint);
  }

  public void drawPath(Path path, Paint paint) {
    canvas.drawPath(path, paint);
  }

  public void drawPoint(float x, float y, Paint paint) {
    canvas.drawPoint(x, y, paint);
  }

  public void drawPoints(float[] pts, int offset, int count, Paint paint) {
    canvas.drawPoints(pts, offset, count, paint);
  }

  public void drawPoints(float[] pts, Paint paint) {
    canvas.drawPoints(pts, paint);
  }

  public void drawRect(RectF rect, Paint paint) {
    canvas.drawRect(rect, paint);
  }

  public void drawRect(Rect r, Paint paint) {
    canvas.drawRect(r, paint);
  }

  public void drawRect(float left, float top, float right, float bottom, Paint paint) {
    canvas.drawRect(left, top, right, bottom, paint);
  }

  public void drawRGB(int r, int g, int b) {
    canvas.drawRGB(r, g, b);
  }

  public void drawRoundRect(RectF rect, float rx, float ry, Paint paint) {
    canvas.drawRoundRect(rect, rx, ry, paint);
  }

  public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, Paint paint) {
    canvas.drawRoundRect(left, top, right, bottom, rx, ry, paint);
  }

  public void drawDoubleRoundRect(RectF outer, float outerRx, float outerRy, RectF inner, float innerRx, float innerRy, Paint paint) {
    canvas.drawDoubleRoundRect(outer, outerRx, outerRy, inner, innerRx, innerRy, paint);
  }

  public void drawDoubleRoundRect(RectF outer, float[] outerRadii, RectF inner, float[] innerRadii, Paint paint) {
    canvas.drawDoubleRoundRect(outer, outerRadii, inner, innerRadii, paint);
  }

  public void drawText(char[] text, int index, int count, float x, float y, Paint paint) {
    canvas.drawText(text, index, count, x, y, paint);
  }

  public void drawText(String text, float x, float y, Paint paint) {
    canvas.drawText(text, x, y, paint);
  }

  public void drawText(String text, int start, int end, float x, float y, Paint paint) {
    canvas.drawText(text, start, end, x, y, paint);
  }

  public void drawText(CharSequence text, int start, int end, float x, float y, Paint paint) {
    canvas.drawText(text, start, end, x, y, paint);
  }

  public void drawTextOnPath(char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint) {
    canvas.drawTextOnPath(text, index, count, path, hOffset, vOffset, paint);
  }

  public void drawTextOnPath(String text, Path path, float hOffset, float vOffset, Paint paint) {
    canvas.drawTextOnPath(text, path, hOffset, vOffset, paint);
  }

  public void drawTextRun(char[] text, int index, int count, int contextIndex, int contextCount, float x, float y, boolean isRtl, Paint paint) {
    canvas.drawTextRun(text, index, count, contextIndex, contextCount, x, y, isRtl, paint);
  }

  public void drawTextRun(CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint paint) {
    canvas.drawTextRun(text, start, end, contextStart, contextEnd, x, y, isRtl, paint);
  }

  public void drawTextRun(MeasuredText text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, Paint paint) {
    canvas.drawTextRun(text, start, end, contextStart, contextEnd, x, y, isRtl, paint);
  }

  public void drawVertices(Canvas.VertexMode mode, int vertexCount, float[] verts, int vertOffset, float[] texs, int texOffset, int[] colors, int colorOffset, short[] indices, int indexOffset, int indexCount, Paint paint) {
    canvas.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors, colorOffset, indices, indexOffset, indexCount, paint);
  }

  public void drawRenderNode(RenderNode renderNode) {
    canvas.drawRenderNode(renderNode);
  }
}
