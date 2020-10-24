//
// Created by JeffMony Lee on 2020/10/25.
//
#include <jni.h>
#include <string>
#include <complex.h>

extern "C" {
#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libavfilter/avfilter.h"
#include "libavutil/avutil.h"
#include "android_log.h"
}

extern "C"
JNIEXPORT jobject JNICALL
Java_com_jeffmony_ffmpeglib_FFmpegVideoInfoUtils_getVideoInfo(JNIEnv *env, jclass clazz,
                                                              jstring input_path) {

    if(use_log_report)
    {
        av_log_set_callback(ffp_log_callback_report);
    }
    else
    {
        av_log_set_callback(ffp_log_callback_brief);
    }

    const char *in_filename = env->GetStringUTFChars(input_path, 0);
    AVFormatContext *ifmt_ctx = NULL;
    int ret;
    int i;
    int video_index = -1;
    int width, height;
    int64_t duration;

    if ((ret == avformat_open_input(&ifmt_ctx, in_filename, 0, 0)) < 0) {
        LOGE("Could not open input file '%s'", in_filename);
        avformat_close_input(&ifmt_ctx);
        LOGE("avformat_open_input failed, ret=%d", ret);
        return NULL;
    }
    if ((ret = avformat_find_stream_info(ifmt_ctx, 0)) < 0) {
        LOGE("Failed to retrieve input stream information");
        avformat_close_input(&ifmt_ctx);
        LOGE("avformat_find_stream_info failed, ret=%d", ret);
        return NULL;
    }

    duration = ifmt_ctx->duration;

    for(i = 0; i < ifmt_ctx->nb_streams; i++) {
        AVStream *in_stream = ifmt_ctx->streams[i];
        AVCodecParameters *in_codecpar = in_stream->codecpar;
        if (in_codecpar->codec_type == AVMEDIA_TYPE_VIDEO) {
            video_index = i;
            width = in_codecpar->width;
            height = in_codecpar->height;
        }
    }

    if (video_index == -1) {
        LOGE("Cannot find the video track index");
        return NULL;
    }

    LOGE("duration=%lld, width=%d, height=%d", duration, width, height);

    jclass objClass = env->FindClass("com/jeffmony/ffmpeglib/model/VideoInfo");
    jmethodID mid = env->GetMethodID(objClass, "<init>", "()V");
    jobject constObj = env->NewObject(objClass, mid);

    mid = env->GetMethodID(objClass, "setDuration", "(J)V");
    env->CallVoidMethod(constObj, mid, duration);

    mid = env->GetMethodID(objClass, "setWidth", "(I)V");
    env->CallVoidMethod(constObj, mid, width);

    mid = env->GetMethodID(objClass, "setHeight", "(I)V");
    env->CallVoidMethod(constObj, mid, height);
    return constObj;
}