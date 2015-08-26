package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.domain.usecases.GetAllWordsUseCase;
import com.buzzit.buzzit.domain.usecases.PopulateWordsStorageUseCase;
import com.buzzit.buzzit.domain.usecases.RemoveWordUseCase;
import com.buzzit.buzzit.presentation.presenters.BuzzitMainGamePresenter;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import java.util.ArrayList;
import java.util.List;
import rx.Subscriber;

public class BuzzitMainGamePresenterImpl implements BuzzitMainGamePresenter {
  private final PopulateWordsStorageUseCase populateWordsStorageUseCase;
  private final GetAllWordsUseCase getAllWordsUseCase;
  private final RemoveWordUseCase removeWordUseCase;
  private final BuzzitMainGameView view;

  private List<Word> currentAvailableWords;

  public BuzzitMainGamePresenterImpl(PopulateWordsStorageUseCase populateWordsStorageUseCase,
      GetAllWordsUseCase getAllWordsUseCase, RemoveWordUseCase removeWordUseCase,
      BuzzitMainGameView view) {
    this.populateWordsStorageUseCase = populateWordsStorageUseCase;
    this.getAllWordsUseCase = getAllWordsUseCase;
    this.removeWordUseCase = removeWordUseCase;
    this.view = view;
  }

  @Override public void onCreate() {
    view.displayLoading();
    populateWordsStorageUseCase.populate().subscribe(new Subscriber<List<Word>>() {
      @Override public void onCompleted() {
        view.hideLoading();
      }

      @Override public void onError(Throwable e) {
        view.hideLoading();
      }

      @Override public void onNext(List<Word> words) {
        currentAvailableWords = new ArrayList<>(words);
      }
    });
  }
}
