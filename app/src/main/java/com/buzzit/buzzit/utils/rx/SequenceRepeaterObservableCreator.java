package com.buzzit.buzzit.utils.rx;

import rx.Observable;
import rx.functions.Func1;

/**
 * Interface that will create an observable which emits the items of a sequence repeatedly.
 * Isolating this makes unit testing possible.
 * <p/>
 * Created by fred on 27.08.15.
 */
public interface SequenceRepeaterObservableCreator {
  /**
   * Repeat the given sequence until a certain condition is satisfied. Each element is emitted with
   * the given delay in seconds.
   *
   * @param iterable The sequence to repeat
   * @param stopPredicate The predicate that when evaluated to true will stop the sequence  @return
   * The repeating observable
   */
  <T> Observable<T> repeatSequenceUntil(Iterable<? extends T> iterable, int seconds,
      final Func1<? super T, Boolean> stopPredicate);
}
