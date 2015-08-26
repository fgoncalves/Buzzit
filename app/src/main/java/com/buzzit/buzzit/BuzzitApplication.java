package com.buzzit.buzzit;

import android.app.Application;
import com.buzzit.buzzit.utils.logging.ProductionTree;
import dagger.ObjectGraph;
import timber.log.Timber;

public class BuzzitApplication extends Application {

  private ObjectGraph appGraph;

  @Override public void onCreate() {
    super.onCreate();

    appGraph = ObjectGraph.create(new AppModule(this));

    Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new ProductionTree());
  }

  /**
   * Get the scopped graph for a given entity in the app.
   *
   * @param modules The modules to add to the scope
   * @return A scopped graph ready for injection
   */
  public ObjectGraph getScopedGraph(Object... modules) {
    return appGraph.plus(modules);
  }
}
