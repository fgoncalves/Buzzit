package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.presentation.events.NewTargetWordEvent;
import com.buzzit.buzzit.presentation.presenters.TargetWordPresenter;
import com.buzzit.buzzit.presentation.views.TargetWordView;
import de.greenrobot.event.EventBus;
import javax.inject.Inject;

public class TargetWordPresenterImpl implements TargetWordPresenter {
  private final TargetWordView view;
  private final EventBus bus;

  @Inject public TargetWordPresenterImpl(TargetWordView view, EventBus bus) {
    this.view = view;
    this.bus = bus;
  }

  @Override public void onAttachWindow() {
    bus.register(this);
  }

  @Override public void onDetachWindow() {
    bus.unregister(this);
  }

  public void onEvent(NewTargetWordEvent event) {
    view.displayNewTargetWord(event.getTargetWord());
  }
}
