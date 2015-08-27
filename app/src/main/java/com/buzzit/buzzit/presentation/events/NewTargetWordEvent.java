package com.buzzit.buzzit.presentation.events;

/**
 * Event thrown when a new target word should be displayed.
 * <p/>
 * Created by fred on 27.08.15.
 */
public class NewTargetWordEvent {
  private final String targetWord;

  public NewTargetWordEvent(String targetWord) {
    this.targetWord = targetWord;
  }

  public String getTargetWord() {
    return targetWord;
  }
}
