package com.newegg.framework.common.configuration.configsources;

import com.newegg.framework.common.configuration.manager.FileConfigGetParameter;

public class XmlConfigGetParameter extends FileConfigGetParameter {

    /// <summary>
    /// 初始化 <see cref="XmlConfigGetParameter"/> 类的新实例。
    /// </summary>
    public XmlConfigGetParameter(){
        super();
    }

    /// <summary>
    /// 给定配置文件所在的目录，初始化 <see cref="XmlConfigGetParameter"/> 类的新实例。
    /// </summary>
    /// <param name="path">配置文件的路径。</param>
    public XmlConfigGetParameter(String path){
        super(path);
    }

    /// <summary>
    /// 给定配置文件所在的目录，初始化 <see cref="XmlConfigGetParameter"/> 类的新实例。
    /// </summary>
    /// <param name="path">配置文件所在的目录。</param>
    /// <param name="filter">配置文件的类型。例如，“*.config”所有以.config扩展名的配置文件。</param>

    public XmlConfigGetParameter(String path,String filter){
        super(path,filter);
    }

    /// <summary>
    /// 给定配置文件所在的目录和文件类型，初始化 <see cref="XmlConfigGetParameter"/> 类的新实例。
    /// </summary>
    /// <param name="path">配置文件所在的目录。</param>
    /// <param name="filter">配置文件的类型。例如，“*.config”所有以.config扩展名的配置文件。</param>
    /// <param name="includeSubdirectories">指示在所有子目录中搜索配置文件的标识。</param>
    public XmlConfigGetParameter(String path,String filter,boolean includeSubdirectories){
        super(path, filter, includeSubdirectories);
    }

}
