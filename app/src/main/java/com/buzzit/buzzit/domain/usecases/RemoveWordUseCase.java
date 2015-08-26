package com.buzzit.buzzit.domain.usecases;

import com.buzzit.buzzit.data.models.Word;
import rx.Observable;

/**
 * This is the use case to remove a word from the current available ones. The behavior is the same
 * as {@link com.buzzit.buzzit.data.services.RemoveWordService#remove(Word)}
 * <p/>
 * Created by fred on 26.08.15.
 */
public interface RemoveWordUseCase {

  /**
   * Remove the given word from the current available ones. The word has to have a valid id. The
   * behavior of the method is the same as {@link com.buzzit.buzzit.data.services.RemoveWordService#remove(Word)}
   *
   * @param word The word to remove with a valid id
   * @return An observable for the deleted word.
   */
  Observable<Word> remove(Word word);
}
