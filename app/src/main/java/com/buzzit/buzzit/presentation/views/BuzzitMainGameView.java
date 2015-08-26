package com.buzzit.buzzit.presentation.views;

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
}
