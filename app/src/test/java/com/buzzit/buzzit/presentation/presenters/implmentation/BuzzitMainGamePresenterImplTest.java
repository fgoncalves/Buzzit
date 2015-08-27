package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.domain.usecases.GetAllWordsUseCase;
import com.buzzit.buzzit.domain.usecases.PopulateWordsStorageUseCase;
import com.buzzit.buzzit.domain.usecases.RemoveWordUseCase;
import com.buzzit.buzzit.presentation.events.NewTargetWordEvent;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import com.buzzit.buzzit.testutils.ImmediateToImmediateSchedulerTransformer;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuzzitMainGamePresenterImplTest {
  @Mock PopulateWordsStorageUseCase populateWordsStorageUseCase;
  @Mock GetAllWordsUseCase getAllWordsUseCase;
  @Mock RemoveWordUseCase removeWordUseCase;
  @Mock BuzzitMainGameView view;
  @Mock EventBus bus;

  private BuzzitMainGamePresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(populateWordsStorageUseCase.populate()).thenReturn(
        Observable.just((List<Word>) new ArrayList<Word>()));

    presenter = new BuzzitMainGamePresenterImpl(populateWordsStorageUseCase, getAllWordsUseCase,
        removeWordUseCase, new ImmediateToImmediateSchedulerTransformer(), bus, view);
  }

  @Test public void should_populate_words_on_onCreate() {
    presenter.onCreate();

    verify(populateWordsStorageUseCase).populate();
  }

  @Test public void should_tell_view_to_display_loading_indicator() {
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

  @Test public void should_start_game_with_new_target_word() {
    String expected = "foo";
    Word targetWord = new Word();
    targetWord.setTextEng(expected);
    targetWord.setTextSpa(expected);
    List<Word> words = new ArrayList<>();
    words.add(targetWord);
    when(populateWordsStorageUseCase.populate()).thenReturn(Observable.just(words));
    presenter.onCreate();

    ArgumentCaptor<NewTargetWordEvent> captor = ArgumentCaptor.forClass(NewTargetWordEvent.class);

    verify(bus).post(captor.capture());
    assertThat(captor.getValue().getTargetWord()).isEqualTo(expected);
  }
}