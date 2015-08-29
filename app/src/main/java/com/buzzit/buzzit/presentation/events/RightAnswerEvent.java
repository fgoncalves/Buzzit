package com.buzzit.buzzit.presentation.events;

import android.support.annotation.IdRes;

/**
 * An event that carries the view id of the player that was right.
 * <p/>
 * Created by fred on 29.08.15.
 */
public class RightAnswerEvent {
  @IdRes private final int viewId;

  public RightAnswerEvent(@IdRes int viewId) {
    this.viewId = viewId;
  }

  public int getViewId() {
    return viewId;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RightAnswerEvent that = (RightAnswerEvent) o;

    return viewId == that.viewId;
  }

  @Override public int hashCode() {
    return viewId;
  }
}
