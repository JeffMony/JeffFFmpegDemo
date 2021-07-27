//
// Created by jeffli on 2021/7/27.
//

#ifndef JEFFFFMPEGDEMO_VIDEO_COMPOSITE_H
#define JEFFFFMPEGDEMO_VIDEO_COMPOSITE_H

#include <jni.h>

extern "C" {
#include "libavformat/avformat.h"
};

class VideoComposite {
public:
    VideoComposite();
    ~VideoComposite();

    int StartComposite(const char *output_video_path,
                       char** video_paths,
                       int size,
                       jobject composite_listener);

private:
    AVFormatContext *output_context_;
    AVFormatContext *input_context_;

    char** input_paths_;
    int input_size_;
    jobject composite_listener_;

};


#endif //JEFFFFMPEGDEMO_VIDEO_COMPOSITE_H
