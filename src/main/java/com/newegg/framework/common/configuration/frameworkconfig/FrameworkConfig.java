package com.newegg.framework.common.configuration.frameworkconfig;

public class FrameworkConfig {

    /// <summary>
    ///
    /// </summary>
    /// <typeparam name="T"></typeparam>
    /// <returns></returns>
    public static void GetConfig(Class tClass)
    {
        /*DotNetConfigFileAttribute attribute = AttributeHelper.GetConfigAttribute<T, DotNetConfigFileAttribute>();
        if (attribute == null)
        {
            ConfigThrowHelper.ThrowConfigException(
                    SR.ConfigError_NoConfigAttribute, typeof(T).FullName, typeof(DotNetConfigFileAttribute).FullName);
        }

        return GetConfig<T>(attribute.SectionName);*/

    }
}
