package com.newegg.framework.common.configuration.manager;

public interface IConfigParameter {
    /// <summary>
    /// 用于缓存Key。
    /// </summary>
    String Name();

    /// <summary>
    /// 用于监控配置改变。
    /// </summary>
    String GroupName();
}
