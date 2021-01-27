package com.ysl.materialjetpack.shizhan;

import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String getStringMd5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getFileMd5(File file) {
        try {
            StringBuffer sb = new StringBuffer();
            MessageDigest digest = MessageDigest.getInstance("md5");
            FileInputStream fin = new FileInputStream(file);
            int len = -1;
            byte[] buffer = new byte[1024];//设置输入流的缓存大小 字节
            //将整个文件全部读入到加密器中
            while ((len = fin.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            //对读入的数据进行加密
            byte[] bytes = digest.digest();
            for (byte b : bytes) {
                // 数byte 类型转换为无符号的整数
                int n = b & 0XFF;
                // 将整数转换为16进制
                String s = Integer.toHexString(n);
                // 如果16进制字符串是一位，那么前面补0
                if (s.length() == 1) {
                    sb.append("0" + s);
                } else {
                    sb.append(s);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
