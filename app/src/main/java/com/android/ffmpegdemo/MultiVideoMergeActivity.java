package com.android.ffmpegdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jeffmony.ffmpeglib.LogUtils;
import com.jeffmony.ffmpeglib.VideoProcessor;
import com.jeffmony.ffmpeglib.listener.OnVideoCompositeListener;

import java.util.ArrayList;
import java.util.List;

public class MultiVideoMergeActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MultiVideoMergeActivity";

    private EditText mInputVideoTxt1;
    private EditText mInputVideoTxt2;
    private EditText mInputVideoTxt3;
    private EditText mOutputVideoTxt;
    private Button mMergeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_video_merge);

        mInputVideoTxt1 = findViewById(R.id.src_path_txt1);
        mInputVideoTxt2 = findViewById(R.id.src_path_txt2);
        mInputVideoTxt3 = findViewById(R.id.src_path_txt3);
        mOutputVideoTxt = findViewById(R.id.output_video_txt);
        mMergeBtn = findViewById(R.id.merge_btn);
        mMergeBtn.setOnClickListener(this);
    }

    private void doMergeVideo() {
        String inputVideoPath1 = mInputVideoTxt1.getText().toString();
        String inputVideoPath2 = mInputVideoTxt2.getText().toString();
        String inputVideoPath3 = mInputVideoTxt3.getText().toString();
        String outputVideoPath = mOutputVideoTxt.getText().toString();
        if (TextUtils.isEmpty(inputVideoPath1) || TextUtils.isEmpty(inputVideoPath2)
                || TextUtils.isEmpty(inputVideoPath3) || TextUtils.isEmpty(outputVideoPath)) {
            Toast.makeText(this, "请检查输入的文件路径", Toast.LENGTH_SHORT).show();
            return;
        }

        VideoProcessor videoProcessor = new VideoProcessor();
        List<String> inputVideos = new ArrayList<>();
        inputVideos.add(inputVideoPath1);
        inputVideos.add(inputVideoPath2);
        inputVideos.add(inputVideoPath3);
        videoProcessor.compositeVideos(outputVideoPath, inputVideos, new OnVideoCompositeListener() {
            @Override
            public void onComplete() {
                LogUtils.i(TAG, "compositeVideos finish");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MultiVideoMergeActivity.this, "合并视频成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(int errCode) {
                LogUtils.e(TAG, "compositeVideos onError : " + errCode);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MultiVideoMergeActivity.this, "合并视频失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == mMergeBtn) {
            doMergeVideo();
        }
    }
}
