package com.buzzit.buzzit.presentation.presenters.implmentation;

import com.buzzit.buzzit.R;
import com.buzzit.buzzit.presentation.events.GameEndEvent;
import com.buzzit.buzzit.presentation.events.RightAnswerEvent;
import com.buzzit.buzzit.presentation.views.PlayerButtonView;
import de.greenrobot.event.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class PlayerButtonPresenterImplTest {
  @Mock EventBus bus;
  @Mock PlayerButtonView view;

  private PlayerButtonPresenterImpl presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    presenter = new PlayerButtonPresenterImpl(bus, view);
  }

  @Test public void should_register_for_events_on_attach_window() {
    presenter.onAttachWindow();

    verify(bus).register(presenter);
  }

  @Test public void should_unregister_from_events_on_detach_window() {
    presenter.onDetachWindow();

    verify(bus).unregister(presenter);
  }

  @Test public void should_tell_view_to_put_the_counter_to_zero_on_finishing_inflate() {
    presenter.onFinishingInflate(-1);

    verify(view).resetCounter();
  }

  @Test public void should_increment_counter_when_player_was_right() {
    RightAnswerEvent event = new RightAnswerEvent(R.id.action_bar);
    presenter.onFinishingInflate(R.id.action_bar);
    presenter.onEvent(event);

    verify(view).incrementPlayerScore();
  }

  @Test public void should_not_increment_counter_when_player_was_not_the_winner() {
    RightAnswerEvent event = new RightAnswerEvent(R.id.collapseActionView);
    presenter.onFinishingInflate(R.id.action_bar);
    presenter.onEvent(event);

    verify(view, never()).incrementPlayerScore();
  }

  @Test public void should_tell_view_to_reset_counter_on_game_finished() {
    presenter.onEvent(new GameEndEvent());

    verify(view).resetCounter();
  }
}