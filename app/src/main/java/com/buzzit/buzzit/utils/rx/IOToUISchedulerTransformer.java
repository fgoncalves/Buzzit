package com.buzzit.buzzit.utils.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A transformer that will subscribe on the io scheduler and observe on the main thread.
 * <p/>
 * Created by fred on 26.08.15.
 */
public class IOToUISchedulerTransformer implements SchedulerTransformer {
  @Override public <T> Observable.Transformer<T, T> applySchedulers() {
    return new Observable.Transformer<T, T>() {
      @Override public Observable<T> call(Observable<T> observable) {
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }
}
