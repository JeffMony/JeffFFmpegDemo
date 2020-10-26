package com.jeffmony.ffmpeglib;

import com.jeffmony.ffmpeglib.model.VideoInfo;

public class FFmpegVideoUtils {

    static {
        System.loadLibrary("jeffmony");
        System.loadLibrary("avcodec");
        System.loadLibrary("avfilter");
        System.loadLibrary("avformat");
        System.loadLibrary("avutil");
        System.loadLibrary("postproc");
        System.loadLibrary("swresample");
        System.loadLibrary("swscale");
    }

    public static native VideoInfo getVideoInfo(String inputPath);

    public static native int remux(String inputPath, String outputPath);

    public static native int cutVideo(double start, double end, String inputPath, String outputPath);
}
