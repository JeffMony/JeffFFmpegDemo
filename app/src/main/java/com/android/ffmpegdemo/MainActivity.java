package com.android.ffmpegdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mFFmpegInfoBtn;
    private Button mTransformBtn;
    private Button mVideoInfoBtn;
    private Button mFFmpegCmdBtn;
    private Button mCutVideoBtn;
    private Button mSplitStreamBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFFmpegInfoBtn = findViewById(R.id.ffmpeg_info_btn);
        mTransformBtn = findViewById(R.id.transform_btn);
        mVideoInfoBtn = findViewById(R.id.info_btn);
        mFFmpegCmdBtn = findViewById(R.id.ffmpeg_cmd_btn);
        mCutVideoBtn = findViewById(R.id.cut_video_btn);
        mSplitStreamBtn = findViewById(R.id.split_stream_btn);

        mFFmpegInfoBtn.setOnClickListener(this);
        mTransformBtn.setOnClickListener(this);
        mVideoInfoBtn.setOnClickListener(this);
        mFFmpegCmdBtn.setOnClickListener(this);
        mCutVideoBtn.setOnClickListener(this);
        mSplitStreamBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFFmpegInfoBtn) {
            Intent intent = new Intent(this, FFmpegInfoActivity.class);
            startActivity(intent);
        } else if (v == mTransformBtn) {
            Intent intent = new Intent(this, VideoTransformActivity.class);
            startActivity(intent);
        } else if (v == mVideoInfoBtn) {
            Intent intent = new Intent(this, VideoInfoActivity.class);
            startActivity(intent);
        } else if (v == mFFmpegCmdBtn) {
            Intent intent = new Intent(this, FFmpegCmdActivity.class);
            startActivity(intent);
        } else if (v == mVideoInfoBtn) {
            Intent intent = new Intent(this, VideoInfoActivity.class);
            startActivity(intent);
        } else if (v == mCutVideoBtn) {
            Intent intent = new Intent(this, VideoCutActivity.class);
            startActivity(intent);
        } else if (v == mSplitStreamBtn) {
            Intent intent = new Intent(this, VideoSplitStreamActivity.class);
            startActivity(intent);
        }
    }
}
