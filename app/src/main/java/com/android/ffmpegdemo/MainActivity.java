package com.android.ffmpegdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE = 1;

    private Button mFFmpegInfoBtn;
    private Button mTransformBtn;
    private Button mVideoInfoBtn;
    private Button mFFmpegCmdBtn;
    private Button mCutVideoBtn;
    private Button mSplitStreamBtn;
    private Button mMultiVideoMergeBtn;
    private Button mReverseVideoBtn;

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
        mMultiVideoMergeBtn = findViewById(R.id.multi_video_merge_btn);
        mReverseVideoBtn = findViewById(R.id.reverse_video_btn);

        mFFmpegInfoBtn.setOnClickListener(this);
        mTransformBtn.setOnClickListener(this);
        mVideoInfoBtn.setOnClickListener(this);
        mFFmpegCmdBtn.setOnClickListener(this);
        mCutVideoBtn.setOnClickListener(this);
        mSplitStreamBtn.setOnClickListener(this);
        mMultiVideoMergeBtn.setOnClickListener(this);
        mReverseVideoBtn.setOnClickListener(this);

        requestPermissions();
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "权限申请成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "权限申请失败", Toast.LENGTH_SHORT).show();
            }
        }
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
        } else if (v == mMultiVideoMergeBtn) {
            Intent intent = new Intent(this, MultiVideoMergeActivity.class);
            startActivity(intent);
        } else if (v == mReverseVideoBtn) {

        }
    }
}
