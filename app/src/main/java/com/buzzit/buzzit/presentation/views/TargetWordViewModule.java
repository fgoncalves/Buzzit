package com.buzzit.buzzit.presentation.views;

import com.buzzit.buzzit.AppModule;
import com.buzzit.buzzit.presentation.presenters.TargetWordPresenter;
import com.buzzit.buzzit.presentation.presenters.implmentation.TargetWordPresenterImpl;
import com.buzzit.buzzit.presentation.views.implementation.TargetWordViewImpl;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Dagger module for the target word view
 * <p/>
 * Created by fred on 27.08.15.
 */
@Module(injects = { TargetWordViewImpl.class }, addsTo = AppModule.class)
public class TargetWordViewModule {
  private final TargetWordView view;

  public TargetWordViewModule(TargetWordView view) {
    this.view = view;
  }

  @Provides @Singleton public TargetWordView providesTargetWordView() {
    return view;
  }

  @Provides @Singleton
  public TargetWordPresenter providesTargetWordPresenter(TargetWordPresenterImpl presenter) {
    return presenter;
  }
}
