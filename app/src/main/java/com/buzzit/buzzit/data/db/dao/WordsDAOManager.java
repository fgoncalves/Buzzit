package com.buzzit.buzzit.data.db.dao;

import com.buzzit.buzzit.data.models.Word;
import com.j256.ormlite.dao.Dao;

/**
 * Wrap the DAO operations inside an interface for a specific model. This helps us with opening and
 * closing connections to the db.
 * </p>
 * Created by fred on 25.08.15.
 */
public interface WordsDAOManager {

  /**
   * Get the DAO associated with this object. Please don't forget to call {@link
   * WordsDAOManager#release()} when you're done with this DAo.
   *
   * @return A run time DAO created specifically for the model {@link Word}
   */
  Dao<Word, String> getDAO();

  /**
   * Call this method when you're done with the database to avoid memory leaks
   */
  void release();
}
