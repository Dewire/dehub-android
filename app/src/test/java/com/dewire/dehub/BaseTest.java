package com.dewire.dehub;

import android.util.Log;

import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * Created by kl on 11/5/16.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Log.class)
public abstract class BaseTest {
  public BaseTest() {
    PowerMockito.mockStatic(Log.class);
  }
}
