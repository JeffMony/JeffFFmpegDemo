package com.jeffmony.ffmpeglib;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.net.Uri;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.jeffmony.ffmpeglib.listener.IM3U8MergeListener;
import com.jeffmony.ffmpeglib.m3u8.M3U8Utils;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;

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
                final int result = FFmpegVideoUtils.transformVideo(inputFilePath, outputFilePath);
                if (result == 1) {
                    notifyOnMergeFinished(listener);
                } else {

                    if (result == -1004) {
                        LogUtils.i(TAG, "Input file has no width or height");

                        String firstSegFilePath = M3U8Utils.getFirstSegFilePath(inputFilePath);
                        if (TextUtils.isEmpty(firstSegFilePath)) {
                            LogUtils.i(TAG, "First seg file is empty");
                            notifyOnMergeFailed(listener, result);
                            return;
                        }

                        File firstSegFile = new File(firstSegFilePath);
                        if (!firstSegFile.exists()) {
                            LogUtils.i(TAG, "First seg file is not existing");
                            notifyOnMergeFailed(listener, result);
                            return;
                        }

                        LogUtils.i(TAG, "First seg file path="+firstSegFilePath);

                        MediaExtractor extractor = new MediaExtractor();
                        FileInputStream fis = null;
                        try {
                            fis = new FileInputStream(firstSegFile);
                            FileDescriptor fd = fis.getFD();
                            extractor.setDataSource(fd);
                            int trackCount = extractor.getTrackCount();
                            int width = 0;
                            int height = 0;
                            for(int index = 0; index < trackCount; index++) {
                                MediaFormat format = extractor.getTrackFormat(index);
                                LogUtils.i(TAG, "format="+format);
                                String mime = format.getString(MediaFormat.KEY_MIME);
                                if (!TextUtils.isEmpty(mime) && mime.startsWith("video/")) {
                                    width = format.getInteger(MediaFormat.KEY_WIDTH);
                                    height = format.getInteger(MediaFormat.KEY_HEIGHT);
                                    break;
                                }
                            }
                            if (width > 0 && height > 0) {

                                int newResult = FFmpegVideoUtils.transformVideoWithDimensions(inputFilePath, outputFilePath, width, height);
                                if (newResult == 1) {
                                    notifyOnMergeFinished(listener);
                                } else {
                                    notifyOnMergeFailed(listener, newResult);
                                }
                                return;
                            } else {
                                LogUtils.i(TAG, "Extractor get file width or height failed");
                                notifyOnMergeFailed(listener, result);
                                return;
                            }
                        } catch (Exception e) {
                            LogUtils.i(TAG, "Extractor setDataSource failed, exception="+e.getMessage());
                            notifyOnMergeFailed(listener, result);
                            return;
                        } finally {
                            try {
                                if (fis != null) {
                                    fis.close();
                                }
                            } catch (Exception e) {
                                LogUtils.i(TAG, "Fis close failed, exception="+e.getMessage());
                            }
                            if (extractor != null) {
                                extractor.release();
                            }
                        }
                    }
                    notifyOnMergeFailed(listener, result);

                }
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
