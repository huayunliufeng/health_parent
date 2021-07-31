package com.zhong.kangan.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


/**
 * @author 华韵流风
 * @ClassName FileUploadUtil
 * @Date 2021/7/29 17:38
 * @packageName com.zhong.kangan.utils
 * @Description TODO
 */
public class FileUploadUtil {

    /**
     * 构造一个带指定 Region 对象的配置类
     */
    private static final Configuration CFG = new Configuration(Region.region0());
    /**
     * ...其他参数参考类注释
     */
    private static final UploadManager UPLOAD_MANAGER = new UploadManager(CFG);
    /**
     * ...生成上传凭证，然后准备上传
     */
    private static final String ACCESS_KEY = "2koFLEmA2fA3bAjoTANgJ2I7Vhtt15f5Hc2Cv8G8";
    private static final String SECRET_KEY = "IZFuXCnL4Dtal9kZKlMYls-qp79w-R3TcxdP3RRi";
    private static final String BUCKET = "3300358443";
    /**
     * 默认不指定key的情况下，以文件内容的hash值作为文件名
     */
    private static final Auth AUTH = Auth.create(ACCESS_KEY, SECRET_KEY);

    public static boolean uploadFile(byte[] uploadBytes, String fileName) {

        String upToken = AUTH.uploadToken(BUCKET);
        try {
            Response response = UPLOAD_MANAGER.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return true;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
                ex2.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteFile(String fileName){

        BucketManager bucketManager = new BucketManager(AUTH, CFG);
        try {
            bucketManager.delete(BUCKET, fileName);
            return true;
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
        return false;
    }
}
