package com.tambapps.gmage;

import com.tambapps.gmage.color.Color;
import com.tambapps.gmage.region.Region;
import com.tambapps.gmage.transformer.ColorTransformer;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Undo/Redo Gmage
 * This Gmage listens to all operations modifying itself to keep track
 * of any change, allowing to undo/redo an operation
 */
@EqualsAndHashCode(callSuper = true)
public class UndoRedoGmage extends Gmage {

  private final LinkedList<Gmage> history;
  private final int windowSize;
  private int currentIndex = 0;
  private boolean storeHistory;

  UndoRedoGmage(Gmage gmage, int windowSize) {
    this(gmage, windowSize, new LinkedList<>());
  }

  private UndoRedoGmage(Gmage gmage, int windowSize, LinkedList<Gmage> history) {
    super(gmage.width, gmage.height, gmage.pixels);
    this.windowSize = windowSize;
    if (windowSize <= 0) {
      throw new IllegalArgumentException("Window size should be greater than 0");
    }
    this.history = history;
    storeHistory = true;
  }

  @Override
  public void apply(ColorTransformer transformer) {
    pushHistory();
    super.apply(transformer);
  }

  @Override
  public void apply(ColorTransformer transformer, Region region) {
    pushHistory();
    super.apply(transformer, region);
  }

  @Override
  public void applyOutside(ColorTransformer transformer, Region region) {
    pushHistory();
    super.applyOutside(transformer, region);
  }

  private void pushHistory() {
    if (!storeHistory) {
      return;
    }
    history.add(super.copy());
    if (history.size() > windowSize) {
      this.history.removeFirst();
    }
    currentIndex = history.size() - 1;
  }

  /**
   * Redo the last undone operation if any
   * @return whether it did redo or there weren't any operation to redo
   */
  public boolean redo() {
    if (history.isEmpty() || currentIndex >= history.size() - 1) {
      return false;
    }
    this.set(history.get(++currentIndex));
    return true;
  }

  /**
   * Undo the last done operation if any
   * @return whether it did undo or not
   */
  public boolean undo() {
    if (history.isEmpty() || currentIndex <= 0) {
      return false;
    }
    this.set(history.get(--currentIndex));
    return true;
  }

  /**
   * Allow to perform multiple modifications to this UndoRedoGmage and get only one change containing all
   * the actions performed in the history
   * Note that nested calls of this functions are not handled
   *
   * @param gmageConsumer the consumer in which the action should be performed
   */
  public void runOperation(Consumer<Gmage> gmageConsumer) {
    storeHistory = false;
    gmageConsumer.accept(this);
    storeHistory = true;
  }

  public UndoRedoGmage copy() {
    return new UndoRedoGmage(this, windowSize, new LinkedList<>(history));
  }

  public UndoRedoGmage copy(int windowSize) {
    return new UndoRedoGmage(this, windowSize);
  }

  @Override
  public void putAt(List<Number> xy, Color value) {
    pushHistory();
    super.putAt(xy, value);
  }

  @Override
  public void putAt(int oneDIndex, Color value) {
    pushHistory();
    super.putAt(oneDIndex, value);
  }

  @Override
  public void putColor(int x, int y, Color value) {
    pushHistory();
    super.putColor(x, y, value);
  }

  @Override
  public void mirrorX() {
    pushHistory();
    super.mirrorX();
  }

  @Override
  public void mirrorY() {
    pushHistory();
    super.mirrorY();
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

  /**
   * Clear the history
   */
  public void clearHistory() {
    history.clear();
  }

  /**
   * Returns the history of this gmage
   * @return the history of this gmage
   */
  public List<Gmage> getHistory() {
    return Collections.unmodifiableList(history);
  }
}
