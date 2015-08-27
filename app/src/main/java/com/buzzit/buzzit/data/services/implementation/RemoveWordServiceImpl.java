package com.buzzit.buzzit.data.services.implementation;

import com.buzzit.buzzit.data.db.dao.WordsDAOManager;
import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.data.services.RemoveWordService;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class RemoveWordServiceImpl implements RemoveWordService {
  private final WordsDAOManager wordsDAOManager;

  @Inject public RemoveWordServiceImpl(WordsDAOManager wordsDAOManager) {
    this.wordsDAOManager = wordsDAOManager;
  }

  @Override public Observable<Word> remove(final Word word) {
    return Observable.create(new Observable.OnSubscribe<Word>() {
      @Override public void call(Subscriber<? super Word> subscriber) {
        try {
          Dao<Word, String> dao = wordsDAOManager.getDAO();
          if (dao.delete(word) != 0) subscriber.onNext(word);
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
