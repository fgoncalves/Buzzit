package com.buzzit.buzzit.utils.rx;

import rx.Observable;

/**
 * Following the post from Dan Lew - http://blog.danlew.net/2015/03/02/dont-break-the-chain/ - This
 * interface defines the idea of transformer. This is useful especially for schedulers.
 * <p/>
 * Created by fred on 26.08.15.
 */
public interface SchedulerTransformer {
  /**
   * Apply the schedulers to the observables. Basically set up the subscribe and observe on
   * methods.
   *
   * @param <T> The type of the observer returned
   * @return A transformer for the schedulers of the current chain of observables
   */
  <T> Observable.Transformer<T, T> applySchedulers();
}
