package com.buzzit.buzzit.data.services.implementation;

import com.buzzit.buzzit.data.db.dao.WordsDAOManager;
import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.data.services.CreateWordsService;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;

public class CreateWordsServiceImpl implements CreateWordsService {
  private final WordsDAOManager wordsDAOManager;

  @Inject public CreateWordsServiceImpl(WordsDAOManager wordsDAOManager) {
    this.wordsDAOManager = wordsDAOManager;
  }

  @Override public Observable<List<Word>> create(final List<Word> words) {
    return Observable.create(new Observable.OnSubscribe<List<Word>>() {
      @Override public void call(Subscriber<? super List<Word>> subscriber) {
        if (words.isEmpty()) {
          subscriber.onCompleted();
          return;
        }

        Dao<Word, String> dao = wordsDAOManager.getDAO();
        try {
          for (Word word : words)
            dao.create(word);
          // emit the words already with the id set
          subscriber.onNext(words);
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
