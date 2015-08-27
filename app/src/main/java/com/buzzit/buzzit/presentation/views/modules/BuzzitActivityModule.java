package com.buzzit.buzzit.presentation.views.modules;

import com.buzzit.buzzit.AppModule;
import com.buzzit.buzzit.BuzzitActivity;
import com.buzzit.buzzit.presentation.presenters.BuzzitMainGamePresenter;
import com.buzzit.buzzit.presentation.presenters.implmentation.BuzzitMainGamePresenterImpl;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module for the main activity
 * <p/>
 * Created by fred on 27.08.15.
 */
@Module(injects = { BuzzitActivity.class }, addsTo = AppModule.class)
public class BuzzitActivityModule {
  private final BuzzitMainGameView view;

  public BuzzitActivityModule(BuzzitMainGameView view) {
    this.view = view;
  }

  @Provides @Singleton public BuzzitMainGameView providesBuzzitMainGameView() {
    return view;
  }

  @Provides @Singleton public BuzzitMainGamePresenter providesBuzzitMainGamePresenter(
      BuzzitMainGamePresenterImpl presenter) {
    return presenter;
  }
}
