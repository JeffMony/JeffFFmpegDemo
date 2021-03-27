package com.jeffmony.ffmpeglib.listener;

public interface IM3U8MergeListener {

    void onMergedFinished();

    void onMergeFailed(Exception e);
}
