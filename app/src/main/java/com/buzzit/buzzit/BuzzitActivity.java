package com.buzzit.buzzit;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.buzzit.buzzit.presentation.presenters.BuzzitMainGamePresenter;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import com.buzzit.buzzit.presentation.views.modules.BuzzitActivityModule;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class BuzzitActivity extends BuzzitBaseActivity implements BuzzitMainGameView {
  @Bind(R.id.loadingScreen) View loadingScreen;
  @Bind(R.id.optionsTextView) TextView optionalWordTextView;

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

  @Override public void showGenericError() {
    Toast.makeText(this, R.string.generic_error, Toast.LENGTH_SHORT).show();
  }

  @Override public void showOptionalWord(String optionalWord) {
    optionalWordTextView.setText(optionalWord);
  }

  @Override public void startOptionalWordAnimation() {
    //TODO: Animation
  }

  @Override public void stopOptionalWordAnimation() {
    // TODO: animation
  }

  @OnClick(R.id.green_player_layout) public void onGreenPlayLayoutClick() {
    presenter.onGreenPlayerButtonClicked();
  }

  @OnClick(R.id.yellow_player_layout) public void onYellowPlayLayoutClick() {
    presenter.onYellowPlayerButtonClicked();
  }

  @OnClick(R.id.blue_player_layout) public void onBluePlayLayoutClick() {
    presenter.onBluePlayerButtonClicked();
  }

  @OnClick(R.id.red_player_layout) public void onRedPlayLayoutClick() {
    presenter.onRedPlayerButtonClicked();
  }
}
