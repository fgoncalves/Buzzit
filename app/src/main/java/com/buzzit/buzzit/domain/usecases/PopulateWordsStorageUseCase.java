package com.buzzit.buzzit.domain.usecases;

import com.buzzit.buzzit.data.models.Word;
import java.util.List;
import rx.Observable;

/**
 * Interface to populate the words storage with some data. This should be called before the game
 * starts.<p/>
 * Created by fred on 26.08.15.
 */
public interface PopulateWordsStorageUseCase {
  /**
   * Populate the local storage with data to be used during the game. Calling this method mid game
   * has an undefined behavior.
   *
   * @return The list of words that were populated
   */
  Observable<List<Word>> populate();
}
