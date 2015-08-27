package com.buzzit.buzzit.presentation.views;

import android.support.annotation.NonNull;

/**
 * Custom view for the target word in the middle of the screen
 * <p/>
 * Created by fred on 27.08.15.
 */
public interface TargetWordView {
  void displayNewTargetWord(@NonNull String expected);
}
