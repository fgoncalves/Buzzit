package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.R;
import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.domain.usecases.GetAllWordsUseCase;
import com.buzzit.buzzit.domain.usecases.PopulateWordsStorageUseCase;
import com.buzzit.buzzit.domain.usecases.RemoveWordUseCase;
import com.buzzit.buzzit.presentation.events.NewTargetWordEvent;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import com.buzzit.buzzit.testutils.ImmediateToImmediateSchedulerTransformer;
import com.buzzit.buzzit.utils.rx.SequenceRepeaterObservableCreator;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.functions.Func1;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BuzzitMainGamePresenterImplTest {
  @Mock PopulateWordsStorageUseCase populateWordsStorageUseCase;
  @Mock GetAllWordsUseCase getAllWordsUseCase;
  @Mock RemoveWordUseCase removeWordUseCase;
  @Mock BuzzitMainGameView view;
  @Mock EventBus bus;
  @Mock SequenceRepeaterObservableCreator sequenceRepeaterObservableCreator;

  private BuzzitMainGamePresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    when(populateWordsStorageUseCase.populate()).thenReturn(
        Observable.just((List<Word>) new ArrayList<Word>()));
    when(sequenceRepeaterObservableCreator.repeatSequenceUntil(anyCollection(), anyInt(),
        any(Func1.class))).thenReturn(Observable.just(new Word()));
    when(getAllWordsUseCase.get()).thenReturn(Observable.just((List<Word>) new ArrayList<Word>()));

    presenter = new BuzzitMainGamePresenterImpl(populateWordsStorageUseCase, getAllWordsUseCase,
        removeWordUseCase, new ImmediateToImmediateSchedulerTransformer(),
        sequenceRepeaterObservableCreator, bus, view);
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

  @Test public void should_show_generic_error() {
    when(populateWordsStorageUseCase.populate()).thenReturn(
        Observable.<List<Word>>error(new RuntimeException()));
    presenter.onCreate();

    verify(view).showGenericError();
  }

  @Test public void should_remove_target_word() {
    Word targetWord = new Word();
    targetWord.setId("fooid");
    targetWord.setTextEng("it's amazing");
    targetWord.setTextSpa("issa amassing");
    List<Word> words = new ArrayList<>();
    words.add(targetWord);
    when(populateWordsStorageUseCase.populate()).thenReturn(Observable.just(words));
    presenter.onCreate();

    verify(removeWordUseCase).remove(targetWord);
  }

  @Test public void should_tell_view_to_show_optional_word() {
    List<Word> words = new ArrayList<>();
    Word word = new Word();
    word.setId("fooid");
    word.setTextEng("it's amazing");
    word.setTextSpa("issa amassing");
    words.add(word);

    word = new Word();
    word.setId("foo2");
    word.setTextEng("Call a pizza");
    word.setTextSpa("Call a pissa");
    words.add(word);

    when(populateWordsStorageUseCase.populate()).thenReturn(Observable.just(words));
    when(removeWordUseCase.remove(any(Word.class))).thenReturn(Observable.just(word));

    presenter.onCreate();

    verify(view).showOptionalWord(anyString());
  }

  @Test public void should_tell_view_to_start_fading_animation_for_optional_word() {
    List<Word> words = new ArrayList<>();
    Word word = new Word();
    word.setId("fooid");
    word.setTextEng("it's amazing");
    word.setTextSpa("issa amassing");
    words.add(word);

    word = new Word();
    word.setId("foo2");
    word.setTextEng("Call a pizza");
    word.setTextSpa("Call a pissa");
    words.add(word);

    when(populateWordsStorageUseCase.populate()).thenReturn(Observable.just(words));
    when(removeWordUseCase.remove(any(Word.class))).thenReturn(Observable.just(word));

    presenter.onCreate();

    verify(view).startOptionalWordAnimation();
  }

  @Test public void should_tell_view_to_stop_fading_animation_for_optional_word() {
    List<Word> words = new ArrayList<>();
    Word word = new Word();
    word.setId("fooid");
    word.setTextEng("it's amazing");
    word.setTextSpa("issa amassing");
    words.add(word);

    when(populateWordsStorageUseCase.populate()).thenReturn(Observable.just(words));
    when(removeWordUseCase.remove(any(Word.class))).thenReturn(Observable.just(word));

    presenter.onCreate();
    presenter.onGreenPlayerButtonClicked();

    verify(view).stopOptionalWordAnimation();
  }

  @Test public void should_start_new_round_on_button_clicked_and_words_match() {
    Word targetWord = new Word();
    targetWord.setId("fooid");
    targetWord.setTextEng("it's amazing");
    targetWord.setTextSpa("issa amassing");
    List<Word> words = new ArrayList<>();
    words.add(targetWord);
    when(populateWordsStorageUseCase.populate()).thenReturn(Observable.just(words));
    when(removeWordUseCase.remove(any(Word.class))).thenReturn(Observable.just(targetWord));
    presenter.onCreate();
    presenter.onGreenPlayerButtonClicked();

    verify(getAllWordsUseCase).get();
  }



  @Test public void should_tell_view_to_blink_accordingly_to_the_winner() {
    List<Word> words = new ArrayList<>();
    Word word = new Word();
    word.setId("fooid");
    word.setTextEng("it's amazing");
    word.setTextSpa("issa amassing");
    words.add(word);

    when(populateWordsStorageUseCase.populate()).thenReturn(Observable.just(words));
    when(removeWordUseCase.remove(any(Word.class))).thenReturn(Observable.just(word));

    presenter.onCreate();
    presenter.onGreenPlayerButtonClicked();

    verify(view).blink(R.color.green);
  }
}