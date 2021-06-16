package com.jeffmony.ffmpeglib;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.jeffmony.ffmpeglib.listener.IM3U8MergeListener;
import com.jeffmony.ffmpeglib.listener.IVideoTransformProgressListener;

import java.io.File;

public class VideoProcessManager {

    private static final String TAG = "VideoProcessManager";

    private static volatile VideoProcessManager sInstance = null;

    public static VideoProcessManager getInstance() {
        if (sInstance == null) {
            synchronized (VideoProcessManager.class) {
                if (sInstance == null) {
                    sInstance = new VideoProcessManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 将M3U8文件合并为MP4文件
     * @param inputFilePath
     * @param outputFilePath
     * @param listener
     * @return
     */
    public void mergeVideo(final String inputFilePath, final String outputFilePath, @NonNull final IM3U8MergeListener listener) {
        if (TextUtils.isEmpty(inputFilePath) || TextUtils.isEmpty(outputFilePath)) {
            listener.onMergeFailed(new Exception("Input or output File is empty"));
            return;
        }
        final File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            listener.onMergeFailed(new Exception("Input file is not existing"));
            return;
        }
        VideoProcessThreadHandler.submitRunnableTask(new Runnable() {
            @Override
            public void run() {
                final VideoProcessor processor = new VideoProcessor();
                processor.setOnVideoTransformProgressListener(new IVideoTransformProgressListener() {
                    @Override
                    public void onTransformProgress(float progress) {
                        notifyOnMergeProgress(listener, progress);
                    }
                });
                int result = processor.transformVideo(inputFilePath, outputFilePath);
                if (result == 1) {
                    notifyOnMergeFinished(listener);
                } else {
                    notifyOnMergeFailed(listener, result);
                }
            }
        });
    }

    private void notifyOnMergeProgress(@NonNull final IM3U8MergeListener listener, final float progress) {
        VideoProcessThreadHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onM3U8MergeProgress(progress);
            }
        });
    }

    private void notifyOnMergeFinished(@NonNull final IM3U8MergeListener listener) {
        VideoProcessThreadHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onMergedFinished();
            }
        });
    }

    private void notifyOnMergeFailed(@NonNull final IM3U8MergeListener listener, final int result) {
        VideoProcessThreadHandler.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listener.onMergeFailed(new Exception("mergeVideo failed, result="+result));
            }
        });
    }
}
