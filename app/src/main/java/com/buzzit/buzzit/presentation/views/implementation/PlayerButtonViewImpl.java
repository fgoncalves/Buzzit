package com.buzzit.buzzit.presentation.views.implementation;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.buzzit.buzzit.BuzzitApplication;
import com.buzzit.buzzit.R;
import com.buzzit.buzzit.presentation.presenters.PlayerButtonPresenter;
import com.buzzit.buzzit.presentation.views.PlayerButtonView;
import com.buzzit.buzzit.presentation.views.modules.PlayerButtonViewModule;
import javax.inject.Inject;

public class PlayerButtonViewImpl extends RelativeLayout implements PlayerButtonView {
  private static final int NO_BLINKING_COLOR_SET = -1;
  @Bind(R.id.playerCounter) TextView counter;
  @Inject PlayerButtonPresenter presenter;
  private int blinkingColor;
  private int score;

  public PlayerButtonViewImpl(Context context) {
    this(context, null);
    throw new RuntimeException(
        "Attribute blinkingColor is mandatory for view " + PlayerButtonViewImpl.class);
  }

  public PlayerButtonViewImpl(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PlayerButtonViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    if (attrs == null && !isInEditMode()) {
      throw new RuntimeException(
          "View " + PlayerButtonView.class + " need both the id and blinkingColor attributes");
    }

    TypedArray typedArray =
        context.getTheme().obtainStyledAttributes(attrs, R.styleable.PlayerButtonView, 0, 0);

    try {
      if (typedArray != null) {
        blinkingColor =
            typedArray.getColor(R.styleable.PlayerButtonView_blinkingColor, NO_BLINKING_COLOR_SET);

        if (blinkingColor == NO_BLINKING_COLOR_SET && !isInEditMode()) {
          throw new RuntimeException(
              "Attribute blinkingColor is mandatory for view " + PlayerButtonViewImpl.class);
        }
      }
    } finally {
      typedArray.recycle();
    }
    score = 0;
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();

    ButterKnife.bind(this);
    if (!isInEditMode()) {
      ((BuzzitApplication) ((Activity) getContext()).getApplication()).getScopedGraph(
          new PlayerButtonViewModule(this)).inject(this);

      if (getId() == NO_ID && !isInEditMode()) {
        throw new RuntimeException(
            "Attribute id is mandatory for view " + PlayerButtonViewImpl.class);
      }

      presenter.onFinishingInflate(getId());
    }
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (!isInEditMode()) presenter.onAttachWindow();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    presenter.onDetachWindow();
  }

  @Override public void resetCounter() {
    score = 0;
    counter.setText(String.valueOf(score));
  }

  @Override public void incrementPlayerScore() {
    score++;
    counter.setText(String.valueOf(score));
  }

  public int getBlinkingColor() {
    return blinkingColor;
  }
}
