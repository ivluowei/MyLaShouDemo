package cn.lashou.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import cn.lashou.R;

/**
 * Created by luow on 2016/8/10.
 */
public class SplashActivity extends AppCompatActivity {
    private Handler mHandler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 1000);
    }
}
