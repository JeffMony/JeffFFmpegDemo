package com.jeffmony.ffmpeglib.m3u8;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.jeffmony.ffmpeglib.LogUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class M3U8Utils {

    private static final String TAG = "M3U8Utils";

    private static final String TAG_HEADER = "#EXT";
    private static final String TAG_MEDIA_DURATION = "#EXTINF"; // must
    private static final Pattern REGEX_MEDIA_DURATION = Pattern.compile(TAG_MEDIA_DURATION + ":([\\d\\.]+)\\b");
    /**
     * 获取M3U8文件中第一个分片的地址
     * @param inputM3U8FilePath 本地的M3U8文件路径
     * @return
     */
    public static String getFirstSegFilePath(@NonNull String inputM3U8FilePath) {
        File m3u8File = new File(inputM3U8FilePath);
        if (!m3u8File.exists()) {
            return null;
        }
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String firstSegFilePath = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(m3u8File));
            bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            float segDuration = 0;
            while((line = bufferedReader.readLine()) != null) {
                LogUtils.i(TAG, "line="+line);
                if (line.startsWith(TAG_HEADER)) {
                    if (line.startsWith(TAG_MEDIA_DURATION)) {
                        String ret = parseStringAttr(line, REGEX_MEDIA_DURATION);
                        if (!TextUtils.isEmpty(ret)) {
                            segDuration = Float.parseFloat(ret);
                        }
                    }
                    continue;
                }
                if (Math.abs(segDuration) > 0.01f) {
                    firstSegFilePath = line;
                }
                if (!TextUtils.isEmpty(firstSegFilePath)) {
                    break;
                }
            }
        } catch (Exception e) {
            LogUtils.i(TAG, "getFirstSegFilePath failed, exception="+e.getMessage());
            return null;
        } finally {
            close(inputStreamReader);
            close(bufferedReader);
        }
        return firstSegFilePath;
    }

    public static String parseStringAttr(String line, Pattern pattern) {
        if (pattern == null)
            return null;
        Matcher matcher = pattern.matcher(line);
        if (matcher.find() && matcher.groupCount() == 1) {
            return matcher.group(1);
        }
        return null;
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                LogUtils.w(TAG, "close " + closeable + " failed, exception = " + e);
            }
        }
    }
}
