package com.buzzit.buzzit.domain.usecasses.implementation;

import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.data.services.RemoveWordService;
import com.buzzit.buzzit.domain.usecases.RemoveWordUseCase;
import javax.inject.Inject;
import rx.Observable;

/**
 * This implementation has the same behavior as {@link RemoveWordService#remove(Word)}
 * <p/>
 * Created by fred on 26.08.15.
 */
public class RemoveWordUseCaseImpl implements RemoveWordUseCase {
  private final RemoveWordService service;

  @Inject public RemoveWordUseCaseImpl(RemoveWordService service) {
    this.service = service;
  }

  @Override public Observable<Word> remove(Word word) {
    return service.remove(word);
  }
}
