package com.android.ffmpegdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jeffmony.ffmpeglib.VideoProcessor;
import com.jeffmony.ffmpeglib.LogUtils;
import com.jeffmony.ffmpeglib.model.VideoInfo;

public class FFmpegVideoInfoActivity extends AppCompatActivity {

    private static final String TAG = "FFmpegVideoInfoActivity";

    private EditText mSrcText;
    private Button mInfoBtn;
    private TextView mInfoTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoinfo);

        mSrcText = findViewById(R.id.src_path_txt);
        mInfoBtn = findViewById(R.id.video_info_btn);
        mInfoTxt = findViewById(R.id.video_info_txt);

        mInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputPath = mSrcText.getText().toString();
                if (TextUtils.isEmpty(inputPath)) {
                    LogUtils.w(TAG, "当前输入的url为空");
                    return;
                }
                VideoProcessor processor = new VideoProcessor();
                VideoInfo videoInfo = processor.getVideoInfo(inputPath);
                if (videoInfo == null) {
                    LogUtils.w(TAG, "获取视频信息失败");
                    return;
                }
                mInfoTxt.setText(videoInfo.toString());
            }
        });
    }


}
