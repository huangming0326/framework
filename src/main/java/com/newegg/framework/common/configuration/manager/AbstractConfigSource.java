package com.newegg.framework.common.configuration.manager;

import java.util.HashMap;
import java.util.List;

public abstract class AbstractConfigSource implements IConfigSource, AutoCloseable {

    //Logger
    private static final Object _syncObj = new Object();
    private static final HashMap<String, IConfigChangeWatcher> _watcherMappings = new HashMap<String, IConfigChangeWatcher>();
    private static final HashMap<String, Object> _cachedConfigs = new HashMap<String, Object>();

    /// <summary>
    /// 开始监控。
    /// </summary>
    @Override
    public void StartWatching(IConfigParameter getParameter) {

    }

    /// <summary>
    /// 停止监控。
    /// </summary>
    @Override
    public void StopWatching(IConfigParameter getParameter) {

    }

    /// <summary>
    /// 安装监控。
    /// </summary>
    /// <param name="watcher">监控。</param>
    protected void SetupWacher(IConfigChangeWatcher watcher) {

    }

    /// <summary>
    /// 从缓存中获取配置信息。
    /// </summary>
    /// <param name="getParameter"></param>
    /// <returns></returns>
    protected <T> T GetConfigFromCache(IConfigParameter getParameter) {
        Object cachedObj = null;
        if (_cachedConfigs.containsKey(getParameter.Name())) {
            cachedObj = _cachedConfigs.get(getParameter.Name());
        }

        return (T) cachedObj;
    }

    /// <summary>
    /// 从缓存中获取配置信息列表。
    /// </summary>
    /// <typeparam name="T"></typeparam>
    /// <param name="getParameter"></param>
    /// <returns></returns>
    protected <T> List<T> GetConfigListFromCache(IConfigParameter getParameter) {
        Object cachedObj = null;
        if (_cachedConfigs.containsKey(getParameter.Name())) {
            cachedObj = _cachedConfigs.get(getParameter.Name());
        }

        return (List<T>) cachedObj;
    }

    /// <summary>
    /// 添加配置到缓存中。
    /// </summary>
    /// <param name="getParameter">获取配置入口参数。</param>
    /// <param name="config">要缓存的配置。</param>
    protected void AddConfigToCache(IConfigParameter getParameter, Object config) {
        String cachedKey = getParameter.Name();
        if (!_cachedConfigs.containsKey(cachedKey)) {
            synchronized (_syncObj) {
                if (!_cachedConfigs.containsKey(cachedKey)) {
                    _cachedConfigs.put(cachedKey, config);
                }
            }
        }
    }

    /// <summary>
    /// 从缓存中移除配置。
    /// </summary>
    /// <param name="getParameter">获取配置入口参数。</param>
    protected void RemoveConfigFromCache(IConfigParameter getParameter) {
        if (_cachedConfigs.containsKey(getParameter.Name())) {
            synchronized (_syncObj) {
                if (_cachedConfigs.containsKey(getParameter.Name())) {
                    _cachedConfigs.remove(getParameter.Name());
                }
            }
        }
    }

    @Override
    public void close() throws Exception {

    }

    protected Object SyncLock()
    {
        return _syncObj;
    }
}
