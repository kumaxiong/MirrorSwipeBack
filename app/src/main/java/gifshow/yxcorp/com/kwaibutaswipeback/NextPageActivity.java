package gifshow.yxcorp.com.kwaibutaswipeback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import gifshow.yxcorp.com.kwaibutaswipeback.view.SwipeBackFrameLayout;

public class NextPageActivity extends AppCompatActivity {
  private SwipeBackFrameLayout mSwipeBackFrameLayout;

  private static final String TAG = "NextPageActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.moudle_activity_next_page);
    initView();
  }

  private void initView () {
    mSwipeBackFrameLayout = findViewById(R.id.layout_swipe_back);
    mSwipeBackFrameLayout.setSwipeBackListener(new SwipeBackFrameLayout.OnSwipeBackListener() {
      @Override
      public void completeSwipeBack() {
        Log.d(TAG, "completeSwipeBack: ");
        finish();
      }
    });
    mSwipeBackFrameLayout.setRightSwipeEnable(true);
    mSwipeBackFrameLayout.setLeftSwipeEnable(true);
  }
}
