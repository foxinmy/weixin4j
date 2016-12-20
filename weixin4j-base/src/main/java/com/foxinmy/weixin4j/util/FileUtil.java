package com.foxinmy.weixin4j.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 文件工具类
 *
 * @author jinyu(foxinmy@gmail.com)
 * @className FileUtil
 * @date 2014年11月21日
 * @see
 * @since JDK 1.6
 */
public class FileUtil {

    private final static Map<String, String> FILE_TYPE_MAP = new HashMap<String, String>();

    static {
        FILE_TYPE_MAP.put("ffd8ffe000104a464946", "jpg");
        FILE_TYPE_MAP.put("89504e470d0a1a0a0000", "png");
        FILE_TYPE_MAP.put("47494638396126026f01", "gif");
        FILE_TYPE_MAP.put("49492a00227105008037", "tif");
        FILE_TYPE_MAP.put("424d228c010000000000", "bmp"); // 16色位图(bmp)
        FILE_TYPE_MAP.put("424d8240090000000000", "bmp"); // 24位位图(bmp)
        FILE_TYPE_MAP.put("424d8e1b030000000000", "bmp"); // 256色位图(bmp)

        FILE_TYPE_MAP.put("49443303000000002176", "mp3");
        FILE_TYPE_MAP.put("52494646", "wav");
        FILE_TYPE_MAP.put("00005741", "wav");
        FILE_TYPE_MAP.put("2321414d", "amr");
        FILE_TYPE_MAP.put("520a3c91", "amr");

        FILE_TYPE_MAP.put("2e524d46000000120001", "rmvb"); // rmvb、rm
        FILE_TYPE_MAP.put("464c5601050000000900", "flv"); // flv、f4v
        FILE_TYPE_MAP.put("667479706d70", "mp4");
        FILE_TYPE_MAP.put("667479706973", "mp4");
        FILE_TYPE_MAP.put("000001ba210001000180", "mpg");
        FILE_TYPE_MAP.put("3026b2758e66cf11a6d9", "wmv"); // wmv、asf
        FILE_TYPE_MAP.put("52494646d07d60074156", "avi");

        FILE_TYPE_MAP.put("41433130313500000000", "dwg");
        FILE_TYPE_MAP.put("3c21444f435459504520", "html");
        FILE_TYPE_MAP.put("3c21646f637479706520", "htm");
        FILE_TYPE_MAP.put("48544d4c207b0d0a0942", "css");
        FILE_TYPE_MAP.put("696b2e71623d696b2e71", "js");
        FILE_TYPE_MAP.put("7b5c727466315c616e73", "rtf");
        FILE_TYPE_MAP.put("38425053000100000000", "psd");
        FILE_TYPE_MAP.put("46726f6d3a203d3f6762", "eml");
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "doc"); // MS Excel、Word、Msi
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "vsd");
        FILE_TYPE_MAP.put("5374616E64617264204A", "mdb");
        FILE_TYPE_MAP.put("255044462d312e350d0a", "pdf");
        FILE_TYPE_MAP.put("4d546864000000060001", "mid");
        FILE_TYPE_MAP.put("504b0304140000000800", "zip");
        FILE_TYPE_MAP.put("526172211a0700cf9073", "rar");
        FILE_TYPE_MAP.put("235468697320636f6e66", "ini");
        FILE_TYPE_MAP.put("504b03040a0000000000", "jar");
        FILE_TYPE_MAP.put("4d5a9000030000000400", "exe");
        FILE_TYPE_MAP.put("3c25402070616765206c", "jsp");
        FILE_TYPE_MAP.put("4d616e69666573742d56", "mf");
        FILE_TYPE_MAP.put("3c3f786d6c2076657273", "xml");
        FILE_TYPE_MAP.put("494e5345525420494e54", "sql");
        FILE_TYPE_MAP.put("7061636b616765207765", "java");
        FILE_TYPE_MAP.put("406563686f206f66660d", "bat");
        FILE_TYPE_MAP.put("1f8b0800000000000000", "gz");
        FILE_TYPE_MAP.put("6c6f67346a2e726f6f74", "properties");
        FILE_TYPE_MAP.put("cafebabe0000002e0041", "class");
        FILE_TYPE_MAP.put("49545346030000006000", "chm");
        FILE_TYPE_MAP.put("04000000010000001300", "mxp");
        FILE_TYPE_MAP.put("504b0304140006000800", "docx");
        FILE_TYPE_MAP.put("d0cf11e0a1b11ae10000", "wps");// WPS(wps、et、dps)
        FILE_TYPE_MAP.put("6431303a637265617465", "torrent");
        FILE_TYPE_MAP.put("6D6F6F76", "mov");
        FILE_TYPE_MAP.put("FF575043", "wpd");
        FILE_TYPE_MAP.put("CFAD12FEC5FD746F", "dbx");
        FILE_TYPE_MAP.put("2142444E", "pst");
        FILE_TYPE_MAP.put("AC9EBD8F", "qdf");
        FILE_TYPE_MAP.put("E3828596", "pwl");
        FILE_TYPE_MAP.put("2E7261FD", "ram");
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 获取文件类型
     */
    public static String getFileType(InputStream is) {
        String fileType = "file";
        try {
            byte[] b = new byte[10];
            int t = is.read(b, 0, b.length);
            if (t > 0) {
                String fileCode = bytesToHexString(b).toLowerCase();
                for (Entry<String, String> entry : FILE_TYPE_MAP.entrySet()) {
                    String key = entry.getKey().toLowerCase();
                    if (key.startsWith(fileCode) || fileCode.startsWith(key) || key.endsWith(fileCode) || fileCode.endsWith(key)) {
                        fileType = entry.getValue();
                        break;
                    }
                }
            }
        } catch (IOException e) {
            ;
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ignore) {
                    ;
                }
            }
        }
        return fileType;
    }

    /**
     * 获取文件后缀
     */
    public static String getFileExtension(String fileName) {
        int extensionPos = fileName.lastIndexOf(".");
        if (extensionPos < 0) {
            return "";
        }
        int lastUnixPos = fileName.lastIndexOf("/");
        int lastWindowsPos = fileName.lastIndexOf("\\");
        int lastSeparator = Math.max(lastUnixPos, lastWindowsPos);
        return lastSeparator > extensionPos ? "" : fileName.substring(extensionPos + 1);
    }

    /**
     * 删除文件
     * @param filePath
     * @return
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        InputStream is = new FileInputStream(new File("/Users/jy/Downloads/test.mp4"));
        System.err.println(getFileType(is));
        System.err.println(URLConnection.guessContentTypeFromStream(is));
    }
}