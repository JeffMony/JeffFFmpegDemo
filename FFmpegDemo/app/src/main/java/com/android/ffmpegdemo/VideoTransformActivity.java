package com.android.ffmpegdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jeffmony.ffmpeglib.LogUtils;
import com.jeffmony.ffmpeglib.VideoProcessManager;
import com.jeffmony.ffmpeglib.listener.IM3U8MergeListener;

import java.io.File;

public class VideoTransformActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "VideoTransformActivity";

    private EditText mSrcTxt;
    private EditText mDestTxt;
    private Button mConvertBtn;
    private TextView mTransformProgressTxt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_transform);

        initViews();
    }

    private void initViews() {
        mSrcTxt = findViewById(R.id.src_path_txt);
        mDestTxt = findViewById(R.id.dest_path_txt);
        mConvertBtn = findViewById(R.id.convert_btn);
        mTransformProgressTxt = findViewById(R.id.video_transform_progress_txt);

        mConvertBtn.setOnClickListener(this);
    }

    private void doConvertVideo(String inputPath, String outputPath) {
        if (TextUtils.isEmpty(inputPath) || TextUtils.isEmpty(outputPath)) {
            LogUtils.i(TAG, "InputPath or OutputPath is null");
            return;
        }
        File inputFile = new File(inputPath);
        if (!inputFile.exists()) {
            return;
        }
        File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
            } catch (Exception e) {
                LogUtils.w(TAG, "Create file failed, exception = " + e);
                return;
            }
        }
        LogUtils.i(TAG, "inputPath="+inputPath+", outputPath="+outputPath);
        VideoProcessManager.getInstance().mergeVideo(inputPath, outputPath, new IM3U8MergeListener() {

            @Override
            public void onM3U8MergeProgress(float progress) {
                LogUtils.i(TAG, "onM3U8MergeProgress progress="+progress);
                mTransformProgressTxt.setText(progress + "%");
            }

            @Override
            public void onMergedFinished() {
                LogUtils.i(TAG, "onMergedFinished");
            }

            @Override
            public void onMergeFailed(Exception e) {
                LogUtils.i(TAG, "onMergeFailed, e="+e.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mConvertBtn) {
            doConvertVideo(mSrcTxt.getText().toString(), mDestTxt.getText().toString());
        }
    }
}
