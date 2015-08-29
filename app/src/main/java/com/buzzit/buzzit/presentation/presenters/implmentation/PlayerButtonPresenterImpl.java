package com.buzzit.buzzit.presentation.presenters.implmentation;

import android.support.annotation.IdRes;
import com.buzzit.buzzit.presentation.events.GameEndEvent;
import com.buzzit.buzzit.presentation.events.RightAnswerEvent;
import com.buzzit.buzzit.presentation.presenters.PlayerButtonPresenter;
import com.buzzit.buzzit.presentation.views.PlayerButtonView;
import de.greenrobot.event.EventBus;
import javax.inject.Inject;

public class PlayerButtonPresenterImpl implements PlayerButtonPresenter {
  private final EventBus bus;
  private final PlayerButtonView view;
  @IdRes private int viewId;

  @Inject public PlayerButtonPresenterImpl(EventBus bus, PlayerButtonView view) {
    this.bus = bus;
    this.view = view;
  }

  @Override public void onAttachWindow() {
    bus.register(this);
  }

  @Override public void onDetachWindow() {
    bus.unregister(this);
  }

  @Override public void onFinishingInflate(@IdRes int viewId) {
    this.viewId = viewId;
    view.resetCounter();
  }

  public void onEvent(RightAnswerEvent event) {
    if (event.getViewId() != viewId) return;

    view.incrementPlayerScore();
  }

  public void onEvent(GameEndEvent event) {
    view.resetCounter();
  }
}
