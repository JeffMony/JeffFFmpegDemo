package com.android.ffmpegdemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jeffmony.ffmpeglib.LogUtils;

public class FFmpegCutVideoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FFmpegCutVideoActivity";

    private EditText mSrcPathTxt;
    private EditText mDestPathTxt;
    private EditText mStartTxt;
    private EditText mEndTxt;
    private Button mCutBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutvideo);

        mSrcPathTxt = findViewById(R.id.src_path_txt);
        mDestPathTxt = findViewById(R.id.dest_path_txt);
        mStartTxt = findViewById(R.id.video_start_txt);
        mEndTxt = findViewById(R.id.video_end_txt);
        mCutBtn = findViewById(R.id.video_cut_btn);

        mCutBtn.setOnClickListener(this);

    }

    private void doCutVideo() {
        String srcPath = mSrcPathTxt.getText().toString();
        String destPath = mDestPathTxt.getText().toString();
        long start = 0;
        long end = 0;
        try {
            start = Long.parseLong(mStartTxt.getText().toString());
            end = Long.parseLong(mEndTxt.getText().toString());
        } catch (Exception e) {
            LogUtils.w(TAG, "请输入正确的start-end 时间");
            return;
        }

    }

    @Override
    public void onClick(View v) {
        if (v == mCutBtn) {
            doCutVideo();
        }
    }
}
