package com.newegg.framework.common.configuration.manager;

public class ConfigManager {
    /// <summary>
    /// 获取配置。
    /// </summary>
    /// <typeparam name="T">配置对象类型。</typeparam>
    /// <returns>配置。</returns>
    public static <T> T GetConfig(Class<T> tClass) throws Exception {
        return GetConfigSource().GetConfig(tClass);
    }

    /// <summary>
    /// 获取默认配置数据源。
    /// </summary>
    /// <returns>配置源。</returns>
    public static IConfigSource GetConfigSource()
    {
        return ConfigSource.Create(ConfigSourceType.XmlConfig);
    }
}
