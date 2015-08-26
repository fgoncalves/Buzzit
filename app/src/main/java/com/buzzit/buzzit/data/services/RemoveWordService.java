package com.buzzit.buzzit.data.services;

import com.buzzit.buzzit.data.models.Word;
import rx.Observable;

/**
 * Interface for the service that removes a single word from the data layer storage
 * <p/>
 * Created by fred on 25.08.15.
 */
public interface RemoveWordService {

  /**
   * Remove the given word from the data layer. The word will be permanently removed. If the word
   * is not found then no events are emitted.
   *
   * @param word The to be removed
   * @return An observable for the removed word
   */
  Observable<Word> remove(Word word);
}
