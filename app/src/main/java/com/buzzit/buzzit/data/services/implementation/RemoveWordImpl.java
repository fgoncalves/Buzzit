package com.buzzit.buzzit.data.services.implementation;

import com.buzzit.buzzit.data.db.dao.WordsDAOManager;
import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.data.services.RemoveWord;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import rx.Observable;
import rx.Subscriber;

public class RemoveWordImpl implements RemoveWord {
  private final WordsDAOManager wordsDAOManager;

  public RemoveWordImpl(WordsDAOManager wordsDAOManager) {
    this.wordsDAOManager = wordsDAOManager;
  }

  @Override public Observable<Word> remove(final Word word) {
    return Observable.create(new Observable.OnSubscribe<Word>() {
      @Override public void call(Subscriber<? super Word> subscriber) {
        Dao<Word, String> dao = wordsDAOManager.getDAO();
        try {
          dao.delete(word);
          subscriber.onNext(word);
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
