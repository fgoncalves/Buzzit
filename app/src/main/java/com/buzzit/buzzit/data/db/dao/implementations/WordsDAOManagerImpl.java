package com.buzzit.buzzit.data.db.dao.implementations;

import android.content.Context;
import com.buzzit.buzzit.data.db.DatabaseHelper;
import com.buzzit.buzzit.data.db.dao.WordsDAOManager;
import com.buzzit.buzzit.data.models.Word;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import javax.inject.Inject;

public class WordsDAOManagerImpl implements WordsDAOManager {
  private final Context context;

  @Inject public WordsDAOManagerImpl(Context context) {
    this.context = context;
  }

  @Override public Dao<Word, String> getDAO() {
    return OpenHelperManager.getHelper(context, DatabaseHelper.class).getWordDao();
  }

  @Override public void release() {
    OpenHelperManager.releaseHelper();
  }
}
