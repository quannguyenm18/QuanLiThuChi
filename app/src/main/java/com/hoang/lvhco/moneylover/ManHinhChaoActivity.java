package com.hoang.lvhco.moneylover;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hoang.lvhco.moneylover.activity.LoginActivity;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                } catch (Exception e){

                }finally {
                    Intent intent = new Intent(ManHinhChaoActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        thread.start();
    }
}

