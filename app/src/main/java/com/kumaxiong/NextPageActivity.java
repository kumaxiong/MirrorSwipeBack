package com.kumaxiong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class NextPageActivity extends AppCompatActivity {
    private MirrorSwipeBackLayout mMirrorSwipeBackLayout;

    private static final String TAG = "NextPageActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moudle_activity_next_page);
        initView();
    }

    private void initView() {
        mMirrorSwipeBackLayout = MirrorSwipeBack.attach(this, R.layout.swipe_back);
        // mMirrorSwipeBackLayout.setRightSwipeEnable(true);
        // mMirrorSwipeBackLayout.setLeftSwipeEnable(true);
        mMirrorSwipeBackLayout.setSwipeBackListener(new MirrorSwipeBackLayout.OnSwipeBackListener() {
            @Override
            public void completeSwipeBack() {
                Toast.makeText(NextPageActivity.this, "滑动返回 completeSwipeBack", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
