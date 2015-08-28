package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.BuildConfig;
import com.buzzit.buzzit.R;
import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.domain.usecases.GetAllWordsUseCase;
import com.buzzit.buzzit.domain.usecases.PopulateWordsStorageUseCase;
import com.buzzit.buzzit.domain.usecases.RemoveWordUseCase;
import com.buzzit.buzzit.presentation.events.NewTargetWordEvent;
import com.buzzit.buzzit.presentation.presenters.BuzzitMainGamePresenter;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import com.buzzit.buzzit.utils.rx.SchedulerTransformer;
import com.buzzit.buzzit.utils.rx.SequenceRepeaterObservableCreator;
import com.buzzit.buzzit.utils.rx.modules.RxSchedulersModule;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import timber.log.Timber;

public class BuzzitMainGamePresenterImpl implements BuzzitMainGamePresenter {
  private static final Random RANDOM = new Random();
  private static final String ENGLISH = "english";
  private static final String SPANISH = "spanish";

  private final PopulateWordsStorageUseCase populateWordsStorageUseCase;
  private final GetAllWordsUseCase getAllWordsUseCase;
  private final RemoveWordUseCase removeWordUseCase;
  private final SchedulerTransformer schedulerTransformer;
  private final SequenceRepeaterObservableCreator sequenceRepeaterObservableCreator;
  private final EventBus bus;

  private final BuzzitMainGameView view;
  private List<Word> currentAvailableWords;
  private GameState currentGameState = GameState.UNKNOWN;
  private Word targetWord;
  /**
   * The language chosen to show the target word
   */
  private String targetWordLanguage;
  private volatile String currentDisplayedWord;
  private Subscription optionalWordsSubscription;

  @Inject
  public BuzzitMainGamePresenterImpl(PopulateWordsStorageUseCase populateWordsStorageUseCase,
      GetAllWordsUseCase getAllWordsUseCase, RemoveWordUseCase removeWordUseCase,
      @Named(RxSchedulersModule.IO_TO_UI_SCHEDULER_TRANSFORMER_INJECTION_NAME)
      SchedulerTransformer schedulerTransformer,
      SequenceRepeaterObservableCreator sequenceRepeaterObservableCreator, EventBus bus,
      BuzzitMainGameView view) {
    this.populateWordsStorageUseCase = populateWordsStorageUseCase;
    this.getAllWordsUseCase = getAllWordsUseCase;
    this.removeWordUseCase = removeWordUseCase;
    this.schedulerTransformer = schedulerTransformer;
    this.sequenceRepeaterObservableCreator = sequenceRepeaterObservableCreator;
    this.bus = bus;
    this.view = view;
  }

  @Override public void onCreate() {
    view.displayLoading();
    view.createBlinkingAnimation();
    view.positionOptionalView();
    view.createOptionalTextAnimation();
    populateWordsStorageUseCase.populate()
        .compose(schedulerTransformer.<List<Word>>applySchedulers())
        .subscribe(new AllWordsSubscriber());
  }

  @Override public void onGreenPlayerButtonClicked() {
    if (isCurrentTranslationCorrect()) {
      view.blink(R.color.green);
      toNextState();
    }
  }

  @Override public void onYellowPlayerButtonClicked() {
    if (isCurrentTranslationCorrect()) {
      view.blink(R.color.yellow);
      toNextState();
    }
  }

  @Override public void onBluePlayerButtonClicked() {
    if (isCurrentTranslationCorrect()) {
      view.blink(R.color.blue);
      toNextState();
    }
  }

  @Override public void onRedPlayerButtonClicked() {
    if (isCurrentTranslationCorrect()) {
      view.blink(R.color.red);
      toNextState();
    }
  }

  private boolean isCurrentTranslationCorrect() {
    return currentDisplayedWord.equals(targetWord.getTextEng()) || currentDisplayedWord.equals(
        targetWord.getTextSpa());
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
      case START_GAME:
        currentGameState = GameState.START_ROUND;
        onRoundStart();
        break;
      case START_ROUND:
        currentGameState = GameState.END_ROUND;
        onRoundEnd();
        break;
      case END_ROUND:
        if (currentAvailableWords.isEmpty()) {
          currentGameState = GameState.END_GAME;
          toNextState();
          break;
        }
        currentGameState = GameState.START_ROUND;
        chooseTargetWord();
        onRoundStart();
        break;
      case END_GAME:
        currentGameState = GameState.UNKNOWN;
        onGameEnd();
    }
  }

  /**
   * Called when the game is over
   */
  private void onGameEnd() {
    // TODO: Cleat pending subscriptions
    view.stopOptionalWordAnimation();
  }

  /**
   * Called when the round is over
   */
  private void onRoundEnd() {
    optionalWordsSubscription.unsubscribe();
    getAllWordsUseCase.get()
        .compose(schedulerTransformer.<List<Word>>applySchedulers())
        .subscribe(new AllWordsSubscriber());
  }

  /**
   * Called when the round started
   */
  private void onRoundStart() {
    optionalWordsSubscription =
        sequenceRepeaterObservableCreator.repeatSequenceUntil(currentAvailableWords,
            BuildConfig.TIME_FOR_EACH_OPTIONAL, new Func1<Word, Boolean>() {
              @Override public Boolean call(Word word) {
                return currentGameState == GameState.END_ROUND
                    || currentGameState == GameState.END_GAME;
              }
            })
            .compose(schedulerTransformer.<Word>applySchedulers())
            .subscribe(new OptionalWordSubscriber());
  }

  /**
   * Called when the game begins
   */
  private void onGameStart() {
    chooseTargetWord();
    toNextState();
    view.startOptionalWordAnimation();
  }

  /**
   * Choose the next target word and remove it from the available ones
   */
  private void chooseTargetWord() {
    targetWord = currentAvailableWords.remove(RANDOM.nextInt(currentAvailableWords.size()));
    String targetTranslation;
    if (RANDOM.nextDouble() < 0.5) {
      targetTranslation = targetWord.getTextEng();
      targetWordLanguage = ENGLISH;
    } else {
      targetTranslation = targetWord.getTextSpa();
      targetWordLanguage = SPANISH;
    }
    bus.post(new NewTargetWordEvent(targetTranslation));
    removeWordUseCase.remove(targetWord)
        .compose(schedulerTransformer.applySchedulers())
        .subscribe();
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
      view.showGenericError();
      Timber.d(e, "Failed to get all words still available");
    }

    @Override public void onNext(List<Word> words) {
      currentAvailableWords = new ArrayList<>(words);
    }
  }

  private class OptionalWordSubscriber extends Subscriber<Word> {
    @Override public void onCompleted() {
    }

    @Override public void onError(Throwable e) {
      view.showGenericError();
      Timber.d(e, "Failed to show optional word");
    }

    @Override public void onNext(Word word) {
      if (RANDOM.nextDouble() <= 0.25 || currentAvailableWords.isEmpty()) {
        word = targetWord;
      }
      if (targetWordLanguage.equals(ENGLISH)) {
        view.showOptionalWord(word.getTextSpa());
        currentDisplayedWord = word.getTextSpa();
      } else {
        view.showOptionalWord(word.getTextEng());
        currentDisplayedWord = word.getTextEng();
      }
    }
  }
}
