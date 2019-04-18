package com.newegg.framework.common.configuration.frameworkconfig.configsettings;

import com.newegg.framework.common.configuration.attributes.ConfigFile;
import com.newegg.framework.common.configuration.utility.AttributeHelper;
import com.newegg.framework.common.io.PathUtils;

import java.util.ArrayList;
import java.util.List;

/// <summary>
/// 框架配置设置
/// </summary>
public class ConfigSettings {

    public String RootPath() {
        return "";
    }

    /// <summary>
    /// 获取配置的所有可能路径。
    /// </summary>
    /// <typeparam name="T"></typeparam>
    /// <returns>仅组合配置根路径和配置相对路径，如果组合后的路径不为绝对路径，不会计算其绝对路径。</returns>
    public static  <T> List<String> GetConfigPaths(Class<T> tClass) throws Exception {
        List<String> pathList = new ArrayList<String>();

        ConfigFile configAttribute = (ConfigFile) AttributeHelper.GetConfigAttribute(tClass);
        if (configAttribute == null) {
            throw new Exception("");
        }

        //Boolean supportMultiLanguages = configAttribute.supportMultiLanguages();
        //String configRelativePath = configAttribute.fileName();

        String path = "configs";
        //String rootPath = "";
        //List<String> fileNames = new ArrayList<String>();
        String fileName = configAttribute.fileName();
        String[] names = fileName.split(";");
        for (String name : names) {
            pathList.add(PathUtils.UrlPathCombine(path, name));
        }

        return pathList;
    }
}
