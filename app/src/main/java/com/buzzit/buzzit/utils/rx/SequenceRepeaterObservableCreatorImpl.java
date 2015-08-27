package com.buzzit.buzzit.utils.rx;

import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

public class SequenceRepeaterObservableCreatorImpl implements SequenceRepeaterObservableCreator {
  @Inject public SequenceRepeaterObservableCreatorImpl() {
  }

  @Override
  public <T> Observable<T> repeatSequenceUntil(Iterable<? extends T> iterable, int seconds,
      Func1<? super T, Boolean> stopPredicate) {
    return Observable.from(iterable)
        .repeat()
        .takeUntil(stopPredicate)
        .delay(seconds, TimeUnit.SECONDS);
  }
}
