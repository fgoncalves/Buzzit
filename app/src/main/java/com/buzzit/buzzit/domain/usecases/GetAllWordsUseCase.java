package com.buzzit.buzzit.domain.usecases;

import com.buzzit.buzzit.data.models.Word;
import java.util.List;
import rx.Observable;

/**
 * Use case to get all the words currently available for the game. This is the use case to use if
 * one wants to get the words that can still be played.
 * <p/>
 * Created by fred on 26.08.15.
 */
public interface GetAllWordsUseCase {

  /**
   * Get all the words available to the game at any given moment. The words retrieved are the words
   * still available to play.
   *
   * @return The words that can still be played.
   */
  Observable<List<Word>> get();
}
