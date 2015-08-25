package com.buzzit.buzzit.data.services.implementation;

import com.buzzit.buzzit.data.db.dao.WordsDAOManager;
import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.data.services.GetAllWords;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

public class GetAllWordsImpl implements GetAllWords {
  private final WordsDAOManager wordsDAOManager;

  public GetAllWordsImpl(WordsDAOManager wordsDAOManager) {
    this.wordsDAOManager = wordsDAOManager;
  }

  @Override public Observable<List<Word>> get() {
    return Observable.create(new Observable.OnSubscribe<List<Word>>() {
      @Override public void call(Subscriber<? super List<Word>> subscriber) {
        Dao<Word, String> dao = wordsDAOManager.getDAO();
        try {
          subscriber.onNext(dao.queryForAll());
          subscriber.onCompleted();
        } catch (SQLException e) {
          subscriber.onError(e);
        } finally {
          wordsDAOManager.release();
        }
      }
    });
  }
}
