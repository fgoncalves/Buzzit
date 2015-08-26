package com.buzzit.buzzit;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import dagger.ObjectGraph;
import java.util.List;

/**
 * This activity is a shortcut to create an activity that get injected with a scoped graph. The
 * activity's onCreate will create the scoped graph and inject the activity. On onDestroy the
 * scoped
 * graph is cleaned to avoid memory leaks.
 * <p/>
 * Created by fred on 26.08.15.
 */
public abstract class BuzzitBaseActivity extends AppCompatActivity {
  protected ObjectGraph scopedGraph;

  @Override public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
    super.onCreate(savedInstanceState, persistentState);
    scopedGraph = ((BuzzitApplication) getApplication()).getScopedGraph(getModules().toArray());
    scopedGraph.inject(this);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    scopedGraph = null;
  }

  /**
   * Retrieve the modules to inject the concrete activity.
   *
   * @return The modules to add to the app graph and create the scoped graph
   */
  protected abstract List<Object> getModules();
}
