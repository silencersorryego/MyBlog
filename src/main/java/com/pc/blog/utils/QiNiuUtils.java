package com.pc.blog.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class QiNiuUtils {

    public final static String ACCESSKEY  = "4f458t0x6dUeGsd_w5aeT1jGKJmMuBiwM3wzEnmy";
    public final static String SECRETKEY  = "Y36zf8lnZRlT-9_JKDGMcrwOmMi1t7KM3_oSWJQu";

    public static String uploadQiNiu(byte[] file, String key, String bucket){
        Auth auth = Auth.create(ACCESSKEY,SECRETKEY);
        UploadManager uploadManager = new UploadManager(new Configuration(Region.region2()));
        try {
            Response response = uploadManager.put(file,key,auth.uploadToken(bucket));
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {

            }
        }
        return key;
    }
}
