package com.buzzit.buzzit.testutils;

import com.buzzit.buzzit.utils.rx.SchedulerTransformer;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Observe on and subscribe on the immediate scheduler.
 * <p/>
 * Created by fred on 27.08.15.
 */
public class ImmediateToImmediateSchedulerTransformer implements SchedulerTransformer {
  @Override public <T> Observable.Transformer<T, T> applySchedulers() {
    return new Observable.Transformer<T, T>() {
      @Override public Observable<T> call(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.immediate()).observeOn(Schedulers.immediate());
      }
    };
  }
}
