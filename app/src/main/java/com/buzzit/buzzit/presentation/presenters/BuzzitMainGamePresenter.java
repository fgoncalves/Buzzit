package com.buzzit.buzzit.presentation.presenters;

/**
 * Presenter for the main view of the game.
 * <p/>
 * Created by fred on 26.08.15.
 */
public interface BuzzitMainGamePresenter {

  void onCreate();

  void onGreenPlayerButtonClicked();

  void onYellowPlayerButtonClicked();

  void onBluePlayerButtonClicked();

  void onRedPlayerButtonClicked();
}
