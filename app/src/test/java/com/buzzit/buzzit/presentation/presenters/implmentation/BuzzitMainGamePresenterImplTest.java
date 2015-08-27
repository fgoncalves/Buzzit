package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.domain.usecases.GetAllWordsUseCase;
import com.buzzit.buzzit.domain.usecases.PopulateWordsStorageUseCase;
import com.buzzit.buzzit.domain.usecases.RemoveWordUseCase;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import com.buzzit.buzzit.testutils.ImmediateToImmediateSchedulerTransformer;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuzzitMainGamePresenterImplTest {
  @Mock PopulateWordsStorageUseCase populateWordsStorageUseCase;
  @Mock GetAllWordsUseCase getAllWordsUseCase;
  @Mock RemoveWordUseCase removeWordUseCase;
  @Mock BuzzitMainGameView view;

  private BuzzitMainGamePresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(populateWordsStorageUseCase.populate()).thenReturn(
        Observable.just((List<Word>) new ArrayList<Word>()));

    presenter = new BuzzitMainGamePresenterImpl(populateWordsStorageUseCase, getAllWordsUseCase,
        removeWordUseCase, new ImmediateToImmediateSchedulerTransformer(), view);
  }

  @Test public void should_populate_words_on_onCreate() {
    presenter.onCreate();

    verify(populateWordsStorageUseCase).populate();
  }

  @Test public void should_tell_view_to_diaplsy_loading_indicator() {
    presenter.onCreate();

    verify(view).displayLoading();
  }

  @Test public void should_tell_view_to_hide_loading_indicator() {
    presenter.onCreate();

    verify(view).hideLoading();
  }

  @Test public void should_tell_view_to_hide_loading_indicator_when_an_error_happens_populating() {
    when(populateWordsStorageUseCase.populate()).thenReturn(
        Observable.<List<Word>>error(new RuntimeException()));
    presenter.onCreate();

    verify(view).hideLoading();
  }
}