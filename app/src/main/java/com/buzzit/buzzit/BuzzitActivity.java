package com.buzzit.buzzit;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.buzzit.buzzit.presentation.presenters.BuzzitMainGamePresenter;
import com.buzzit.buzzit.presentation.views.BuzzitMainGameView;
import com.buzzit.buzzit.presentation.views.implementation.PlayerButtonViewImpl;
import com.buzzit.buzzit.presentation.views.modules.BuzzitActivityModule;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

public class BuzzitActivity extends BuzzitBaseActivity implements BuzzitMainGameView {
  @Bind(R.id.loadingScreen) View loadingScreen;
  @Bind(R.id.optionsTextView) TextView optionalWordTextView;
  @Bind(R.id.blinkingPane) View blinkingPane;

  @Inject BuzzitMainGamePresenter presenter;

  private OptionalWordAnimation optionalWordAnimation;
  private BlinkingAnimation blinkingAnimation;
  private int screenWith;
  private int screenHeight;

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
    optionalWordTextView.startAnimation(optionalWordAnimation);
  }

  @Override public void stopOptionalWordAnimation() {
    optionalWordTextView.clearAnimation();
  }

  @Override public void blink(@ColorRes int color) {
    blinkingAnimation.setColor(color);
    blinkingAnimation.setRepeatCount(3);
    blinkingAnimation.setDuration(500);
    blinkingAnimation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        blinkingPane.setVisibility(View.VISIBLE);
      }

      @Override public void onAnimationEnd(Animation animation) {
        blinkingPane.setVisibility(View.GONE);
      }

      @Override public void onAnimationRepeat(Animation animation) {
      }
    });
    blinkingPane.startAnimation(blinkingAnimation);
  }

  @Override public void createBlinkingAnimation() {
    blinkingAnimation = new BlinkingAnimation();
  }

  @Override public void positionOptionalView() {
    optionalWordTextView.setX(100);
    optionalWordTextView.setY(100);
  }

  @Override public void createOptionalTextAnimation() {
    Display display = getWindowManager().getDefaultDisplay();
    Point size = new Point();
    display.getSize(size);
    screenWith = size.x;
    screenHeight = size.y;

    optionalWordAnimation = new OptionalWordAnimation();
    optionalWordAnimation.setRepeatCount(Animation.INFINITE);
    optionalWordAnimation.setDuration(BuildConfig.TIME_FOR_EACH_OPTIONAL * 1000);
  }

  @OnClick({
      R.id.green_player_layout, R.id.yellow_player_layout, R.id.blue_player_layout,
      R.id.red_player_layout
  }) public void onPlayerButtonClicked(View view) {
    PlayerButtonViewImpl playerButton = (PlayerButtonViewImpl) view;
    presenter.onPlayerButtonClicked(playerButton.getId(), playerButton.getBlinkingColor());
  }

  /**
   * This animation will fade in and fade out the optional text view and will keep it moving around
   * within certain bounds.
   */
  private class OptionalWordAnimation extends Animation {
    private int xDirection = 1;
    private int yDirection = 1;

    @Override protected void applyTransformation(float interpolatedTime, Transformation t) {
      // To make the fade in fade out effect we need to set the alpha of the text view to a value
      // between 0 and 1. We also know that the interpolated time is between 0 and 1. We want alpha
      // to be 1 when the interpolated time is 0.5 and we want alpha to be 0 when the interpolated
      // time is 0 or 1. This describes a parabola with vertex (0.5, 1) and the zeros at (0,0) and
      // (1,0), where the x-axis is the interpolated time and y-axis is the alpha.
      // We start of with an easy parabola: -x^2 = 0
      // We then add 1 to move the parabola up: -x^2 + 1 = 0
      // We subtract one to x to move it to the side: -(x-1)^2 + 1 = 0
      // We finally compress it horizontally by multiplying x by 2: -(2x-1)^2 + 1 = 0
      // Substituting the x by the interpolated time we have the following equation
      optionalWordTextView.setAlpha((float) (-Math.pow(2 * interpolatedTime - 1, 2) + 1));

      float x = optionalWordTextView.getX();
      float y = optionalWordTextView.getY();
      int width = optionalWordTextView.getMeasuredWidth();
      int height = optionalWordTextView.getMeasuredHeight();

      if ((x + width) > (screenWith - 100)) xDirection = -1;

      if (x <= 100) xDirection = 1;

      if ((y + height) > (screenHeight - 100)) yDirection = -1;

      if (y <= 100) yDirection = 1;

      optionalWordTextView.setX(x + xDirection * 1);
      optionalWordTextView.setY(y + yDirection * 1);
    }
  }

  /**
   * A blinking animation that alternates the color of the blinking pane from black to the players
   * color.
   */
  private class BlinkingAnimation extends Animation {
    private int color = -1;

    public void setColor(int color) {
      this.color = color;
    }

    @Override protected void applyTransformation(float interpolatedTime, Transformation t) {
      if (color == -1) throw new RuntimeException("Color must be set before running animation");

      if (interpolatedTime > 0.5) {
        blinkingPane.setBackgroundColor(getResources().getColor(R.color.black));
      } else {
        blinkingPane.setBackgroundColor(color);
      }
    }
  }
}
