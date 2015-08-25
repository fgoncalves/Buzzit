package com.buzzit.buzzit.data.services;

import com.buzzit.buzzit.data.models.Word;
import java.util.List;
import rx.Observable;

/**
 * This service describes the interface to create words between the domain use cases and the data
 * layer.
 * <p/>
 * Created by fred on 25.08.15.
 */
public interface CreateWordsService {
  /**
   * Create the given words in the database. If the list is empty then no events will be emitted by
   * the observable.
   *
   * @param words The list of words to create without any id assigned
   * @return The same words, but with an id assigned to them
   */
  Observable<List<Word>> create(List<Word> words);
}
