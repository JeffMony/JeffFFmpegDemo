# JeffFFmpegDemo

### 主要功能
> * 1.获取FFmpeg编译的基本信息
> * 2.修改媒体文件的封装格式,例如可以从MP4格式转化为MKV格式,M3U8格式转化为MP4格式
> * 3.获取视频的基本信息,宽高/封装格式/编码格式/时长
> * 4.执行FFmpeg命令行
> * 5.截取视频中的某一段

#### 1.获取FFmpeg编译的基本信息
> * (1)获取ffmpeg编译的protocol信息
> * (2)获取ffmpeg编译的muxer demuxer信息
> * (3)获取ffmpeg的encode decode信息
> * (4)获取ffmpeg的filter信息

#### 2.修改媒体文件的封装格式
> * (1)增加修改封装格式的进度提示
> * (2)只是修改封装格式,不作修改编码格式的工作

#### 3.获取视频的基本信息
> * 获取视频的宽/高/时长/封装格式/视频编码格式/音频编码格式

#### 4.执行FFmpeg命令行

#### 5.截取视频中的某一段
> * 输入起始时间点和结束时间点,可以截取这部分时间段的视频

#### 6.多个MP4视频合并为一个MP4视频
> * 只是解封装和再封装，如果做解码和编码操作，耗时非常严重，接受不了
> * 合并的过程中记得一定要对接PTS和DTS，不然生成的视频有问题，不是一个正常的视频

files文件夹下面有4个视频，其中input1.mp4、input2.mp4、input3.mp4合并成output.mp4视频，可以参考下
```
input1.mp4
Duration: 00:00:10.86, start: 0.000000, bitrate: 11161 kb/s

input2.mp4
Duration: 00:00:11.22, start: 0.000000, bitrate: 10798 kb/s

input3.mp4
Duration: 00:00:11.46, start: 0.000000, bitrate: 11378 kb/s

output.mp4
Duration: 00:00:33.63, start: 0.000000, bitrate: 10513 kb/s
```
