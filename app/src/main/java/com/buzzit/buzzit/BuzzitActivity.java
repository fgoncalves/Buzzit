package com.buzzit.buzzit;

import android.os.Bundle;
import android.view.View;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.buzzit.buzzit.presentation.presenters.BuzzitMainGamePresenter;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import com.buzzit.buzzit.presentation.views.modules.BuzzitActivityModule;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class BuzzitActivity extends BuzzitBaseActivity implements BuzzitMainGameView {
  @Bind(R.id.loadingScreen) View loadingScreen;

  @Inject BuzzitMainGamePresenter presenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_buzzit);
    ButterKnife.bind(this);
    presenter.onCreate();
  }

  @Override protected List<Object> getModules() {
    return Arrays.<Object>asList(new BuzzitActivityModule(this));
  }

  @Override public void displayLoading() {
    loadingScreen.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoading() {
    loadingScreen.setVisibility(View.GONE);
  }
}
