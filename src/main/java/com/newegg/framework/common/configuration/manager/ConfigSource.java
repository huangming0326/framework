package com.newegg.framework.common.configuration.manager;

import com.newegg.framework.common.configuration.configsources.XmlConfigSource;

public class ConfigSource {

    public static IConfigSource Create(ConfigSourceType sourceType)
    {
        IConfigSource configSource = null;
        switch (sourceType)
        {
            case DataSetConfig:
                //configSource = DataSetConfigSource.Current;
                break;
            case DotNetConfig:
                //configSource = DotNetConfigSource.Current;
                break;
            case XmlConfig:
                configSource = XmlConfigSource.getConfigSource();
                break;
            default:
                configSource = Create(sourceType.toString());
                break;
        }

        return configSource;
    }

    public static IConfigSource Create(String name)
    {
        return null;
    }
}
