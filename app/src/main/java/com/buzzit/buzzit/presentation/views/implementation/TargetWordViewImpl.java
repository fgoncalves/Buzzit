package com.buzzit.buzzit.presentation.views.implementation;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.buzzit.buzzit.BuzzitApplication;
import com.buzzit.buzzit.R;
import com.buzzit.buzzit.presentation.presenters.TargetWordPresenter;
import com.buzzit.buzzit.presentation.views.TargetWordView;
import com.buzzit.buzzit.presentation.views.TargetWordViewModule;
import javax.inject.Inject;

public class TargetWordViewImpl extends RelativeLayout implements TargetWordView {
  @Bind(R.id.targetWordTextViewOne) TextView one;
  @Bind(R.id.targetWordTextViewTwo) TextView two;

  @Inject TargetWordPresenter presenter;

  public TargetWordViewImpl(Context context) {
    super(context);
  }

  public TargetWordViewImpl(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public TargetWordViewImpl(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
    ((BuzzitApplication) ((Activity) getContext()).getApplication()).getScopedGraph(
        new TargetWordViewModule(this));
  }

  @Override protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    presenter.onAttachWindow();
  }

  @Override protected void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    presenter.onDetachWindow();
  }

  @Override public void displayNewTargetWord(@NonNull String targetWord) {
    one.setText(targetWord);
    two.setText(targetWord);
  }
}
