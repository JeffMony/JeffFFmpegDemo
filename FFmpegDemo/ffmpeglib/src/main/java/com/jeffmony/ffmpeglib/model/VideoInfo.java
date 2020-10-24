package com.jeffmony.ffmpeglib.model;

public class VideoInfo {

    private String mName;
    private long mDuration;
    private int mWidth;
    private int mHeight;

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public long getDuration() {
        return mDuration;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public int getHeight() {
        return mHeight;
    }

}
