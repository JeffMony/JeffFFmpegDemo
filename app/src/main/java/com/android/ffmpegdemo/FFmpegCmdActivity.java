package com.android.ffmpegdemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jeffmony.ffmpeglib.FFmpegCmdUtils;

public class FFmpegCmdActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mCmdText;
    private Button mExecuteBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ffmpeg_cmd);

        mCmdText = findViewById(R.id.ffmpeg_cmd_txt);
        mExecuteBtn = findViewById(R.id.execute_btn);

        mExecuteBtn.setOnClickListener(this);
    }

    private void doExecuteCmd(String cmd) {
        if (TextUtils.isEmpty(cmd)) {
            return;
        }
        String[] cmds = cmd.split(" ");
        FFmpegCmdUtils.ffmpegExecute(cmds);
    }

    @Override
    public void onClick(View v) {
        if (mExecuteBtn == v) {
            doExecuteCmd(mCmdText.getText().toString());
        }
    }
}
