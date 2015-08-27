package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.domain.usecases.GetAllWordsUseCase;
import com.buzzit.buzzit.domain.usecases.PopulateWordsStorageUseCase;
import com.buzzit.buzzit.domain.usecases.RemoveWordUseCase;
import com.buzzit.buzzit.presentation.events.NewTargetWordEvent;
import com.buzzit.buzzit.presentation.presenters.BuzzitMainGamePresenter;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import com.buzzit.buzzit.utils.rx.SchedulerTransformer;
import com.buzzit.buzzit.utils.rx.modules.RxSchedulersModule;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Subscriber;
import timber.log.Timber;

public class BuzzitMainGamePresenterImpl implements BuzzitMainGamePresenter {
  private static final Random RANDOM = new Random();
  private final PopulateWordsStorageUseCase populateWordsStorageUseCase;
  private final GetAllWordsUseCase getAllWordsUseCase;
  private final RemoveWordUseCase removeWordUseCase;
  private final SchedulerTransformer schedulerTransformer;
  private final EventBus bus;

  private final BuzzitMainGameView view;
  private List<Word> currentAvailableWords;
  private GameState currentGameState = GameState.UNKNOWN;
  private Word targetWord;

  @Inject
  public BuzzitMainGamePresenterImpl(PopulateWordsStorageUseCase populateWordsStorageUseCase,
      GetAllWordsUseCase getAllWordsUseCase, RemoveWordUseCase removeWordUseCase,
      @Named(RxSchedulersModule.IO_TO_UI_SCHEDULER_TRANSFORMER_INJECTION_NAME)
      SchedulerTransformer schedulerTransformer, EventBus bus, BuzzitMainGameView view) {
    this.populateWordsStorageUseCase = populateWordsStorageUseCase;
    this.getAllWordsUseCase = getAllWordsUseCase;
    this.removeWordUseCase = removeWordUseCase;
    this.schedulerTransformer = schedulerTransformer;
    this.bus = bus;
    this.view = view;
  }

  @Override public void onCreate() {
    view.displayLoading();
    populateWordsStorageUseCase.populate()
        .compose(schedulerTransformer.<List<Word>>applySchedulers())
        .subscribe(new AllWordsSubscriber());
  }

  /**
   * Check the current state and jump to the next one.
   */
  private void toNextState() {
    switch (currentGameState) {
      case UNKNOWN:
        currentGameState = GameState.START_GAME;
        onGameStart();
        break;
    }
  }

  /**
   * Called when the game begins
   */
  private void onGameStart() {
    chooseTargetWord();
  }

  private void chooseTargetWord() {
    targetWord = currentAvailableWords.get(RANDOM.nextInt(currentAvailableWords.size()));
    String targetTransslation;
    if (RANDOM.nextDouble() < 0.5) {
      targetTransslation = targetWord.getTextEng();
    } else {
      targetTransslation = targetWord.getTextSpa();
    }
    bus.post(new NewTargetWordEvent(targetTransslation));
  }

  private enum GameState {
    UNKNOWN,
    START_GAME,
    START_ROUND,
    END_ROUND,
    END_GAME
  }

  /**
   * This subscriber will choose another target word and change the game state accordingly.
   */
  private class AllWordsSubscriber extends Subscriber<List<Word>> {
    @Override public void onCompleted() {
      toNextState();
      view.hideLoading();
    }

    @Override public void onError(Throwable e) {
      view.hideLoading();
      Timber.d(e, "Failed to get all words still available");
    }

    @Override public void onNext(List<Word> words) {
      currentAvailableWords = new ArrayList<>(words);
    }
  }
}
