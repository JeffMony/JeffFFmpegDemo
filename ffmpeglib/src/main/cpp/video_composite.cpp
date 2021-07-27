//
// Created by jeffli on 2021/7/27.
//

#include "video_composite.h"

#include "video_jni_env.h"
#include "process_error.h"

VideoComposite::VideoComposite() :
output_context_(nullptr),
input_context_(nullptr),
input_paths_(nullptr),
input_size_(0),
composite_listener_(nullptr) {
    //构造函数初始化操作
}

VideoComposite::~VideoComposite() {

}

int VideoComposite::StartComposite(const char *output_video_path, char **video_paths, int size,
                                   jobject composite_listener) {

    input_paths_ = video_paths;
    input_size_ = size;

    if (composite_listener != nullptr) {
        JNIEnv *env = nullptr;
        int ret = jni_get_env(&env);
        if (env == nullptr) {
            return ERR_COMPOSITE_NO_ENV;
        }
        composite_listener_ = env->NewGlobalRef(composite_listener);

        if (ret == JNI_EDETACHED) {
            jni_detach_thread_env();
        }
    }

    int ret = avformat_alloc_output_context2(&output_context_, nullptr, "mp4", output_video_path);
    if (ret < 0) {
        JLOGE("%s alloc context2 error : %s", __func__ , av_err2str(ret));
        return ret;
    }

    if (output_context_ == nullptr) {
        JLOGE("%s output context is nullptr", __func__ );
        return ERR_COMPOSITE_NO_OUTPUT_CONTEXT;
    }

    ret = avformat_open_input(&input_context_, video_paths[0], nullptr, nullptr);
    if (ret != 0) {
        JLOGE("%s open input: %s error : %s", __func__ , video_paths[0], av_err2str(ret));
        return ret;
    }
    if (input_context_ == nullptr) {
        JLOGE("%s input context is nullptr", __func__ );
        return ERR_COMPOSITE_NO_INPUT_CONTEXT;
    }
    ret = avformat_find_stream_info(input_context_, nullptr);
    if (ret < 0) {
        JLOGE("%s find stream info error : %s", __func__ , av_err2str(ret));
        return ret;
    }

    av_dump_format(input_context_, 1, video_paths[0], 0);

    return 0;
}
