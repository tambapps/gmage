package com.tambapps.gmage;

import com.tambapps.gmage.transformer.PixelTransformer;

import java.util.LinkedList;

/**
 * Undo/Redo Gmage
 * This Gmage listens to all operations that modifying it to keep track
 * of any change, allowing to undo/redo an operation
 */
public class URGmage extends Gmage {

  private final LinkedList<Gmage> history;
  private final int windowSize;
  // TODO handle currentIndex
  URGmage(Gmage gmage, int windowSize) {
    super(gmage.width, gmage.height, gmage.pixels);
    this.windowSize = windowSize;
    if (windowSize <= 0) {
      throw new IllegalArgumentException("Window size should be greater than 0");
    }
    this.history = new LinkedList<>();
  }

  @Override
  public void apply(PixelTransformer transformer) {
    pushHistory();
    super.apply(transformer);
  }

  private void pushHistory() {
    history.add(super.copy());
    if (history.size() > windowSize) {
      this.history.removeFirst();
    }
  }

  public URGmage copy() {
    return new URGmage(this, windowSize);
  }

  public void clearHistory() {
    history.clear();
  }

  // TODO handle putAt
  // TODO for each operation that modifies a gmage, handle history
}
