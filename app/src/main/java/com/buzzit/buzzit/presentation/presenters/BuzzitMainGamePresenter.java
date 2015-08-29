package com.buzzit.buzzit.presentation.presenters;

import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;

/**
 * Presenter for the main view of the game.
 * <p/>
 * Created by fred on 26.08.15.
 */
public interface BuzzitMainGamePresenter {

  void onCreate();

  void onResume();

  void onPause();

  void onPlayerButtonClicked(@IdRes int viewID, @ColorRes int blinkingColor);
}
