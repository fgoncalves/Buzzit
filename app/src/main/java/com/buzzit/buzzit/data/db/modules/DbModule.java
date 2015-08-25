package com.buzzit.buzzit.data.db.modules;

import com.buzzit.buzzit.data.db.dao.WordsDAOManager;
import com.buzzit.buzzit.data.db.dao.implementations.WordsDAOManagerImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Module to satisfy dependencies related with database.
 * <p/>
 * Created by fred on 25.08.15.
 */
@Module(complete = false, library = true) public class DbModule {

  @Provides public WordsDAOManager providesWordsDAOManager(WordsDAOManagerImpl wordsDAOManager) {
    return wordsDAOManager;
  }
}
