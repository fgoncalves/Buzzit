package com.buzzit.buzzit.utils.logging;

import android.util.Log;
import timber.log.Timber;

/**
 * The production tree omits all info except errors or fatals
 * Created by fred on 25.08.15.
 */
public class ProductionTree extends Timber.Tree {
  @Override protected void log(int priority, String tag, String message, Throwable t) {
    switch (priority) {
      case Log.VERBOSE:
      case Log.DEBUG:
      case Log.INFO:
      case Log.WARN:
        return;
    }

    Log.println(priority, tag, message);
    Log.println(priority, tag, Log.getStackTraceString(t));
  }
}
