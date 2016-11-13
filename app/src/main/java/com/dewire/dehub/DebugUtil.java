package com.dewire.dehub;

import android.os.Looper;
import android.util.Log;

/**
 * Created by kl on 11/13/16.
 */

@SuppressWarnings("unused")
public final class DebugUtil {
  private DebugUtil() {}

  private static final String TAG = "DebugUtil";

  private static long tickStartMillis = -1;

  /**
   * Call this method followd by tock() to log the time taken.
   */
  public static void tick() {
    tickStartMillis = System.currentTimeMillis();
  }

  /**
   * Logs the time taken between the last call to tock().
   * @throws IllegalStateException if tick() was not called before the last call of tock().
   */
  public static void tock() {
    if (tickStartMillis == -1) {
      throw new IllegalStateException("must call tick() before you call tock()");
    }

    long timeTaken = System.currentTimeMillis() - tickStartMillis;
    tickStartMillis = -1;

    Log.d(TAG, "TICK-TOCK: " + timeTaken + " ms");
  }

  /**
   * @return true if the thread that calls this method is the UI thread, otherwise false.
   */
  public static boolean isUiThread() {
    return Thread.currentThread() == Looper.getMainLooper().getThread();
  }

  /**
   * Logs a message with the result from isUiThread().
   */
  public static void logIsUiThread() {
    Log.d(TAG, "isUiThread(): " + isUiThread());
  }
}
