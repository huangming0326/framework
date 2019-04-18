package com.newegg.framework.common.configuration.utility;

import com.newegg.framework.common.configuration.attributes.ConfigFile;

import java.util.HashMap;

/// <summary>
/// 配置属性辅助类。
/// </summary>
public class AttributeHelper {

    private static HashMap<Class, ConfigFile> typeConfigFileAttributes = new HashMap<Class, ConfigFile>();
    private static Object syncObj = new Object();

    /// <summary>
    /// 获取配置属性。
    /// </summary>
    /// <typeparam name="TAttribute">表示配置属性类型。</typeparam>
    /// <param name="configType">配置类型。</param>
    /// <returns>配置属性。</returns>
    public static <T> T GetConfigAttribute(Class<T> tClass) {
        ConfigFile configFileAttribute = GetConfigAttributeBase(tClass);
        return (T) configFileAttribute;
    }


    public static ConfigFile GetConfigAttributeBase(Class configClass) {
        ConfigFile configFile = null;
        if (typeConfigFileAttributes.containsKey(configClass)) {
            configFile = typeConfigFileAttributes.get(configClass);
        } else {
            synchronized (syncObj) {
                if (!typeConfigFileAttributes.containsKey(configClass)) {
                    configFile = (ConfigFile) configClass.getAnnotation(ConfigFile.class);
                    if (configFile != null) {
                        typeConfigFileAttributes.put(configClass, configFile);
                    }
                }
            }
        }
        return configFile;
    }
}
