package com.tambapps.gmage;

/**
 * Undo/Redo Gmage
 * This Gmage listens to all operations that modifying it to keep track
 * of any change, allowing to undo/redo an operation
 */
public class URGmage extends Gmage {

  private final Gmage[] history;
  URGmage(Gmage gmage, int windowSize) {
    super(gmage.width, gmage.height, gmage.pixels);
    if (windowSize <= 0) {
      throw new IllegalArgumentException("Window size should be greater than 0");
    }
    this.history = new Gmage[windowSize];
  }

  // TODO for each operation that modifies a gmage, handle history
}
