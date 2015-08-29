package com.buzzit.buzzit.presentation.presenters;

import android.support.annotation.IdRes;

/**
 * Presenter for the players' button. This presenter will post events to the event bus when the
 * view is clicked with the appropriate color id for the blinking background
 * <p/>
 * Created by fred on 29.08.15.
 */
public interface PlayerButtonPresenter {
  void onAttachWindow();

  void onDetachWindow();

  void onFinishingInflate(@IdRes int id);
}
