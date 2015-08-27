package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.presentation.events.NewTargetWordEvent;
import com.buzzit.buzzit.presentation.views.TargetWordView;
import de.greenrobot.event.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class TargetWordPresenterImplTest {
  @Mock TargetWordView view;
  @Mock EventBus bus;

  private TargetWordPresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter = new TargetWordPresenterImpl(view, bus);
  }

  @Test public void should_register_for_events_on_attach_window() {
    presenter.onAttachWindow();

    verify(bus).register(presenter);
  }

  @Test public void should_unregister_from_events_on_detach_window() {
    presenter.onDetachWindow();

    verify(bus).unregister(presenter);
  }

  @Test public void should_tell_view_to_display_a_new_target_word() {
    String expected = "foo";
    NewTargetWordEvent event = new NewTargetWordEvent(expected);
    presenter.onEvent(event);

    verify(view).displayNewTargetWord(expected);
  }
}