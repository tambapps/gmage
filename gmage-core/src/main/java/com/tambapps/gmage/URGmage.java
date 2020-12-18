package com.tambapps.gmage;

import com.tambapps.gmage.pixel.Color;
import com.tambapps.gmage.transformer.ColorTransformer;

import java.util.LinkedList;
import java.util.List;

/**
 * Undo/Redo Gmage
 * This Gmage listens to all operations that modifying it to keep track
 * of any change, allowing to undo/redo an operation
 */
public class URGmage extends Gmage {

  private final LinkedList<Gmage> history;
  private final int windowSize;
  private int currentIndex = 0;
  URGmage(Gmage gmage, int windowSize) {
    super(gmage.width, gmage.height, gmage.pixels);
    this.windowSize = windowSize;
    if (windowSize <= 0) {
      throw new IllegalArgumentException("Window size should be greater than 0");
    }
    this.history = new LinkedList<>();
  }

  @Override
  public void apply(ColorTransformer transformer) {
    pushHistory();
    super.apply(transformer);
  }

  private void pushHistory() {
    history.add(super.copy());
    if (history.size() > windowSize) {
      this.history.removeFirst();
    }
    currentIndex = history.size() - 1;
  }

  public boolean redo() {
    if (history.isEmpty() || currentIndex >= history.size() - 1) {
      return false;
    }
    this.set(history.get(++currentIndex));
    return true;
  }

  public boolean undo() {
    if (history.isEmpty() || currentIndex <= 0) {
      return false;
    }
    this.set(history.get(--currentIndex));
    return true;
  }

  public URGmage copy() {
    return new URGmage(this, windowSize);
  }

  @Override
  public void putAt(List<Number> xy, Color value) {
    pushHistory();
    super.putAt(xy, value);
  }

  @Override
  public void set(Gmage gmage) {
    pushHistory();
    try {
      super.set(gmage);
    } catch (IllegalArgumentException e) {
      history.removeLast();
      throw e;
    }
  }

  public void clearHistory() {
    history.clear();
  }

  // TODO handle putAt
  // TODO for each operation that modifies a gmage, handle history
}
