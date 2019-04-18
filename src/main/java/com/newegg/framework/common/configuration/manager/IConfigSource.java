package com.newegg.framework.common.configuration.manager;


public interface IConfigSource {

    /// <summary>
    /// 开始监控。
    /// </summary>
    /// <param name="getParameter"></param>
    void StartWatching(IConfigParameter getParameter);

    /// <summary>
    /// 停止监控。
    /// </summary>
    /// <param name="getParameter"></param>
    void StopWatching(IConfigParameter getParameter);

    /// <summary>
    /// 为配置添加扩展更改处理机制。
    /// </summary>
    /// <typeparam name="T"></typeparam>
    /// <param name="handler"></param>
    //<T> void AddChangeHandler(Class<T> tClass,ConfigChangedEventHandler handler);

    /// <summary>
    /// 获取配置。
    /// </summary>
    /// <typeparam name="T">配置类型。</typeparam>
    /// <returns></returns>
    <T> T GetConfig(Class<T> tClass) throws Exception;

    /// <summary>
    /// 获取配置。
    /// </summary>
    /// <typeparam name="T">配置类型。</typeparam>
    /// <param name="getParameter">获取配置参数。</param>
    /// <param name="restartAppDomainOnChange">当配置改变时重新启动应用程序域。</param>
    /// <returns>配置。</returns>
    <T> T GetConfig(Class<T> tClass, IConfigParameter getParameter, boolean restartAppDomainOnChange);

    /// <summary>
    /// 获取配置列表。
    /// </summary>
    /// <typeparam name="T">配置类型。</typeparam>
    /// <returns></returns>
    //<T> List<T> GetConfigList(Class<T> tClass);

    /// <summary>
    /// 获取配置。
    /// </summary>
    /// <typeparam name="T">配置类型。</typeparam>
    /// <param name="getParameter">获取配置参数。</param>
    /// <param name="restartAppDomainOnChange">当配置改变时重新启动应用程序域。</param>
    /// <returns>配置。</returns>
    //<T> List<T> GetConfigList(Class<T> tClass, IConfigParameter getParameter, boolean restartAppDomainOnChange);

    /// <summary>
    ///
    /// </summary>
    /// <typeparam name="T"></typeparam>
    /// <returns></returns>
    <T> T GetMergedConfig(Class<T> tClass);

    /// <summary>
    /// 获取配置。
    /// </summary>
    /// <typeparam name="T">配置类型。</typeparam>
    /// <param name="getParameter">获取配置参数。</param>
    /// <param name="restartAppDomainOnChange">当配置改变时重新启动应用程序域。</param>
    /// <returns>配置。</returns>
    <T> T GetMergedConfig(Class<T> tClass, IConfigParameter getParameter, boolean restartAppDomainOnChange);
}
