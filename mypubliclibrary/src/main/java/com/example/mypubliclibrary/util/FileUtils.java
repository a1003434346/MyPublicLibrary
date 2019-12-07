package com.example.mypubliclibrary.util;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * function:
 * describe:
 * Created By LiQiang on 2019/8/8.
 */
public class FileUtils {
    /**
     * 获取MultipartBody.Part,上传文件的时候使用
     *
     * @param files files
     * @return
     */
    public static List<MultipartBody.Part> getParts(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);  //表单类型
        for (File file : files) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            builder.addFormDataPart("file", file.getName(), requestBody);//file 后台接收图片流的参数名
        }
//        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        return builder.build().parts();
    }

    public static File getPathToFile(String path) {
        return new File(path);
    }
}
