package com.buzzit.buzzit.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.buzzit.buzzit.R;
import com.buzzit.buzzit.data.models.Word;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Open helper for the main local storage of the app. This helper will populate the local storage
 * on create with data from the file words.json in the raw folder of the app.
 * <p/>
 * Created by fred on 25.08.15.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
  private static final String DATABASE_NAME = "buzzit.db";
  private static final int DATABASE_VERSION = 1;

  private final Context context;
  private Dao<Word, String> wordsDAO = null;

  public DatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
    this.context = context;
  }

  @Override public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
    try {
      TableUtils.createTable(connectionSource, Word.class);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion,
      int newVersion) {
    try {
      TableUtils.dropTable(connectionSource, Word.class, true);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    onCreate(database, connectionSource);
  }

  @Override public void close() {
    super.close();
    wordsDAO = null;
  }

  /**
   * Read the words.json file and populate the words table with the given values
   */
  private void populateDatabase() {
    //InputStream inputStream = context.getResources().openRawResource(R.raw.words);
    //List<Word> words =
    //    new Gson().fromJson(new InputStreamReader(inputStream), new TypeToken<ArrayList<Word>>() {
    //    }.getType());
    //for (Word word : words)
    //  getWordDao().create(word);
  }

  public Dao<Word, String> getWordDao() {
    if (wordsDAO == null) wordsDAO = getRuntimeExceptionDao(Word.class);
    return wordsDAO;
  }
}
