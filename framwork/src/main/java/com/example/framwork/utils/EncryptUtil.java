package com.example.framwork.utils;


import com.yanzhenjie.nohttp.Logger;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * Created by ${wjw} on 2016/5/18.
 * 请求加密算法
 */
public class EncryptUtil {
    public String ALGORITHM_DES = "DES";
    public String key = "IaRt5201";

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     * @author xuzhaohu
     */
    private static class SingletonHolder {
        private static EncryptUtil instance = new EncryptUtil();
    }

    /**
     * 私有的构造函数
     */
    private EncryptUtil() {

    }

    public static EncryptUtil getInstance() {
        return EncryptUtil.SingletonHolder.instance;
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public String encode(String data) {
        try {
            return encode(data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * DES算法，加密
     *
     * @param data 待加密字符串
     * @return 加密后的字节数组，一般结合Base64编码使用
     */
    public String encode(byte[] data) throws Exception {
        try {
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecureRandom sr = new SecureRandom();
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

            byte[] bytes = cipher.doFinal(data);
            Logger.d("---->请求参数：" + decodeValue(Base64.encode(bytes)));
            return Base64.encode(bytes);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * DES算法，解密
     *
     * @param data 待解密字符串
     * @return 解密后的字节数组
     * @throws Exception 异常
     */
    public byte[] decode(byte[] data) throws Exception {
        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // key的长度不能够小于8位字节
            Key secretKey = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
            // 生成一个可信任的随机数源
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    /**
     * 获取编码后的值
     *
     * @param data
     * @return
     * @throws Exception
     */
    public String decodeValue(String data) {
        byte[] datas;
        String value = null;
        try {
            if (System.getProperty("os.name") != null
                    && (System.getProperty("os.name").equalsIgnoreCase("sunos") || System
                    .getProperty("os.name").equalsIgnoreCase("linux"))) {
                datas = decode(Base64.decode(data));
            } else {
                datas = decode(Base64.decode(data));
            }

            value = new String(datas);
        } catch (Exception e) {
            value = "";
        }
        return value;
    }


}
