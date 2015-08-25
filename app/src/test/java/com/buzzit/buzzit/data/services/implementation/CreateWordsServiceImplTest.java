package com.buzzit.buzzit.data.services.implementation;

import com.buzzit.buzzit.data.db.dao.WordsDAOManager;
import com.buzzit.buzzit.data.models.Word;
import com.j256.ormlite.dao.Dao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateWordsServiceImplTest {
  @Mock WordsDAOManager wordsDAOManager;

  private CreateWordsServiceImpl createWordsService;
  private Dao wordsDao;
  private TestSubscriber<List<Word>> testSubscriber;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    wordsDao = mock(Dao.class);
    when(wordsDAOManager.getDAO()).thenReturn(wordsDao);
    createWordsService = new CreateWordsServiceImpl(wordsDAOManager);

    testSubscriber = new TestSubscriber<List<Word>>();
  }

  @Test public void should_notify_subscriber_of_error() throws SQLException {
    SQLException expectedException = new SQLException();
    when(wordsDao.create(any(Word.class))).thenThrow(expectedException);

    List<Word> words = new ArrayList<>();
    words.add(new Word());
    createWordsService.create(words).subscribe(testSubscriber);

    testSubscriber.assertError(expectedException);
  }

  @Test public void should_emit_no_events() {
    createWordsService.create(new ArrayList<Word>()).subscribe(testSubscriber);

    testSubscriber.assertNoValues();
    testSubscriber.assertCompleted();
  }

  @Test public void should_emit_saved_words() throws SQLException {
    List<Word> words = new ArrayList<>();
    words.add(new Word());
    words.add(new Word());
    words.add(new Word());
    createWordsService.create(words).subscribe(testSubscriber);

    testSubscriber.assertCompleted();
    testSubscriber.assertValue(words);
  }
}