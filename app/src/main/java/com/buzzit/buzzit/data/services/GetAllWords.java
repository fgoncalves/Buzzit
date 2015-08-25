package com.buzzit.buzzit.data.services;

import com.buzzit.buzzit.data.models.Word;
import java.util.List;
import rx.Observable;

/**
 * Service to get all the available words.
 * <p/>
 * Created by fred on 25.08.15.
 */
public interface GetAllWords {

  /**
   * Get all the available words.
   *
   * @return The list of all available words
   */
  Observable<List<Word>> get();

}
