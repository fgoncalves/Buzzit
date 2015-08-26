package com.buzzit.buzzit;

import android.os.Bundle;
import java.util.List;

public class BuzzitActivity extends BuzzitBaseActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_buzzit);
  }

  @Override protected List<Object> getModules() {
    // TODO: Add modules once presentation layer is ready
    return null;
  }
}
