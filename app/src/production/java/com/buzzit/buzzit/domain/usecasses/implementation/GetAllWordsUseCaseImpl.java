package com.buzzit.buzzit.domain.usecasses.implementation;

import com.buzzit.buzzit.data.models.Word;
import com.buzzit.buzzit.data.services.GetAllWordsService;
import com.buzzit.buzzit.domain.usecases.GetAllWordsUseCase;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;

/**
 * The behavior of this implementation is the same as {@link GetAllWordsService#get()}
 * <p/>
 * Created by fred on 26.08.15.
 */
public class GetAllWordsUseCaseImpl implements GetAllWordsUseCase {
  private final GetAllWordsService service;

  @Inject public GetAllWordsUseCaseImpl(GetAllWordsService service) {
    this.service = service;
  }

  @Override public Observable<List<Word>> get() {
    return service.get();
  }
}
