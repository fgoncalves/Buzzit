package com.buzzit.buzzit;

import android.app.Application;
import com.buzzit.buzzit.utils.logging.ProductionTree;
import timber.log.Timber;

public class BuzzitApplication extends Application{

  @Override public void onCreate() {
    super.onCreate();

    Timber.plant(BuildConfig.DEBUG? new Timber.DebugTree() : new ProductionTree());
  }
}
