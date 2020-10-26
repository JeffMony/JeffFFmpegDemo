package com.android.ffmpegdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mFFmpegInfoBtn;
    private Button mRemuxBtn;
    private Button mVideoInfoBtn;
    private Button mFFmpegCmdBtn;
    private Button mCutVideoBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFFmpegInfoBtn = findViewById(R.id.ffmpeg_info_btn);
        mRemuxBtn = findViewById(R.id.remux_btn);
        mVideoInfoBtn = findViewById(R.id.info_btn);
        mFFmpegCmdBtn = findViewById(R.id.ffmpeg_cmd_btn);
        mVideoInfoBtn = findViewById(R.id.info_btn);

        mFFmpegInfoBtn.setOnClickListener(this);
        mRemuxBtn.setOnClickListener(this);
        mVideoInfoBtn.setOnClickListener(this);
        mFFmpegCmdBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFFmpegInfoBtn) {
            Intent intent = new Intent(this, FFmpegInfoActivity.class);
            startActivity(intent);
        } else if (v == mRemuxBtn) {
            Intent intent = new Intent(this, FFmpegRemuxActivity.class);
            startActivity(intent);
        } else if (v == mVideoInfoBtn) {
            Intent intent = new Intent(this, FFmpegVideoInfoActivity.class);
            startActivity(intent);
        } else if (v == mFFmpegCmdBtn) {
            Intent intent = new Intent(this, FFmpegCmdActivity.class);
            startActivity(intent);
        } else if (v == mVideoInfoBtn) {
            Intent intent = new Intent(this, FFmpegVideoInfoActivity.class);
            startActivity(intent);
        }
    }
}
