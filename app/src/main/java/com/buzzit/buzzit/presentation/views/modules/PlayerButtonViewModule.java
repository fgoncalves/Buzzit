package com.buzzit.buzzit.presentation.views.modules;

import com.buzzit.buzzit.AppModule;
import com.buzzit.buzzit.presentation.presenters.PlayerButtonPresenter;
import com.buzzit.buzzit.presentation.presenters.implmentation.PlayerButtonPresenterImpl;
import com.buzzit.buzzit.presentation.views.PlayerButtonView;
import com.buzzit.buzzit.presentation.views.implementation.PlayerButtonViewImpl;
import dagger.Module;
import dagger.Provides;

/**
 * Dagger module for the player button view
 * <p/>
 * Created by fred on 29.08.15.
 */
@Module(injects = { PlayerButtonViewImpl.class }, addsTo = AppModule.class)
public class PlayerButtonViewModule {
  private final PlayerButtonView view;

  public PlayerButtonViewModule(PlayerButtonView view) {
    this.view = view;
  }

  @Provides public PlayerButtonView providesPlayerButtonView() {
    return view;
  }

  @Provides public PlayerButtonPresenter providesPlayerButtonPresenter(
      PlayerButtonPresenterImpl playerButtonPresenter) {
    return playerButtonPresenter;
  }
}
