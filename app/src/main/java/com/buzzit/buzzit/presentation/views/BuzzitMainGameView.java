package com.buzzit.buzzit.presentation.views;

import android.support.annotation.ColorRes;

/**
 * View interface for the main view of the game.
 * <p/>
 * Created by fred on 26.08.15.
 */
public interface BuzzitMainGameView {
  /**
   * Tell the view to display a loading indicator
   */
  void displayLoading();

  /**
   * Tell the view to hide the loading indicator
   */
  void hideLoading();

  /**
   * Something unrecoverable happened. Let the user know.
   */
  void showGenericError();

  /**
   * Show this text as a possible translation
   *
   * @param optionalWord A possible translation for the target word
   */
  void showOptionalWord(String optionalWord);

  void startOptionalWordAnimation();

  void stopOptionalWordAnimation();

  /**
   * Blink the background from black to the supplied color
   *
   * @param color The color to blink
   */
  void blink(@ColorRes int color);
}
