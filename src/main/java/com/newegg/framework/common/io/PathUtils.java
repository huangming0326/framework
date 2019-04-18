package com.newegg.framework.common.io;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PathUtils {

    private static int MAX_PATH_LENGTH = 260;

    // 磁盘目录模式 (格式：D:/)
    static Pattern dirRegex = Pattern.compile("^[A-Z]:(\\\\{1,2}([^\\\\/:\\*\\?<>\\|]*))*$", Pattern.CASE_INSENSITIVE);

    // 磁盘共享目录模式(格式：\\MyComputer)
    static Pattern sharedDirRegex = Pattern.compile("^(\\\\{1,2}([^\\\\/:\\*\\?<>\\|]*))*$", Pattern.CASE_INSENSITIVE);

    // 磁盘目录文件模式 (格式：D:\\Test.gif)
    static Pattern dirFileRegex = Pattern.compile("^[A-Z]:\\\\{1,2}(([^\\\\/:\\*\\?<>\\|]+)\\\\{1,2})+([^\\\\/:\\*\\?<>\\|]+)(\\.[A-Z]+)$", Pattern.CASE_INSENSITIVE);

    // 磁盘共享文件模式(格式：\\MyComputer\Test.gif)
    static Pattern sharedDirFileRegex = Pattern.compile("^\\\\{2}(([^\\\\/:\\*\\?<>\\|]+)\\\\{1,2})+([^\\\\/:\\*\\?<>\\|]+)(\\.[A-Z]+)$", Pattern.CASE_INSENSITIVE);

    //来得到当前的classpath的相对路径的URI表示法
    public static String GetDirectoryName(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        return path.substring(0, path.lastIndexOf("/") + 1); //Path.GetDirectoryName(path);
    }

    public static String GetFileName(String path) {
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        if (path.lastIndexOf("/") > 0) {
            return path.substring(path.lastIndexOf("/") + 1, path.length());
        } else {
            return path;
        }
    }

    /// <summary>
    /// 获取完全限定位置路径。
    /// </summary>
    /// </returns>
    public static String GetFullPath(String relativePath) {
        //---/home/buyabs.corp/hh60/src/
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().replaceAll("%20"," ");
        String path = relativePath.replaceAll("%20"," ");
        if (path.startsWith("/")) {
            path = path.replaceAll("^/+", "");
        }
        return String.format("%s%s", rootPath, path);
    }

    public static boolean IsFullPath(String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        // 分别验证 1.磁盘目录模式 (格式：D:\\) 2.磁盘共享目录模式(格式：\\MyComputer)
        return dirRegex.matcher(path).find() || sharedDirRegex.matcher(path).find();
    }

    public static String UrlPathCombine(String... pathArray) {
        if (pathArray == null || pathArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (String path : pathArray) {
            if (!StringUtils.isEmpty(path)) {
                if (path.length() > 0) {
                    if (!path.startsWith("/")) {
                        sb.append("/");
                    }
                }
                sb.append(path);
            }
        }
        return sb.toString().replaceFirst("^/", "");
    }

    public static String TestPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }
}
