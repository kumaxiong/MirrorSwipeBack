package com.kumaxiong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

  private Button mBtnNextPage;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.moudle_activity_main);
    initView();
  }

  private void initView() {
    mBtnNextPage = findViewById(R.id.btn_next_page);
    mBtnNextPage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,NextPageActivity.class);
        startActivity(intent);
      }
    });
  }
}
