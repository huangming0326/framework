package com.newegg.framework.common.configuration.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigParameterCache {
    private static HashMap<Class, ConfigGetParameterCacheItem> _typedConfigParameters = new HashMap<Class, ConfigGetParameterCacheItem>();
    private static Object _syncObj = new Object();


    /// <summary>
    /// 从缓存中获取配置参数。
    /// </summary>
    /// <param name="configType"></param>
    /// <returns></returns>
    public static List<IConfigParameter> GetList(Class configClass) {
        ConfigGetParameterCacheItem cacheItem = null;
        if (_typedConfigParameters.containsKey(configClass)) {
            cacheItem = _typedConfigParameters.get(configClass);
        }

        return cacheItem == null ? null : cacheItem.get_configParameters();
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="configType"></param>
    public static IConfigParameter Get(Class configClass) {
        IConfigParameter configParameter = null;
        ConfigGetParameterCacheItem cacheItem = null;
        if (_typedConfigParameters.containsKey(configClass)) {
            cacheItem = _typedConfigParameters.get(configClass);
        }

        if (configParameter == null && cacheItem.get_configParameters().size() > 0) {
            configParameter = cacheItem.get_configParameters().get(0);
        }

        return configParameter;
    }

    /// <summary>
    ///
    /// </summary>
    /// <param name="configType"></param>
    /// <param name="configParameters"></param>
    /// <param name="restartAppDomainOnChange"></param>
    public static void Set(Class configClass, List<IConfigParameter> configParameters) {
        if (!_typedConfigParameters.containsKey(configClass)) {
            synchronized (_syncObj) {
                if (!_typedConfigParameters.containsKey(configClass)) {
                    ConfigGetParameterCacheItem cacheItem = new ConfigGetParameterCacheItem(configParameters, true);
                    _typedConfigParameters.put(configClass, cacheItem);
                }
            }
        }
    }

    public static void Set(Class configClass, IConfigParameter configParameter) {
        ConfigGetParameterCacheItem cacheItem = null;
        if (_typedConfigParameters.containsKey(configClass)) {
            _typedConfigParameters.get(configClass).set_configParameter(configParameter);
        }
    }


    static class ConfigGetParameterCacheItem {

        private Boolean _restartAppDomainOnChange = false;
        private List<IConfigParameter> _configParameters = new ArrayList<IConfigParameter>();
        private IConfigParameter _configParameter = null;

        public ConfigGetParameterCacheItem(boolean restartAppDomainOnChange) {
            _restartAppDomainOnChange = restartAppDomainOnChange;
        }

        public ConfigGetParameterCacheItem(List<IConfigParameter> configParameters, boolean restartAppDomainOnChange) {
            _configParameters = configParameters;
            _restartAppDomainOnChange = restartAppDomainOnChange;
        }

        public List<IConfigParameter> get_configParameters() {
            return _configParameters;
        }

        public Boolean get_restartAppDomainOnChange() {
            return _restartAppDomainOnChange;
        }

        public IConfigParameter get_configParameter() {
            return _configParameter;
        }

        public void set_configParameter(IConfigParameter _configParameter) {
            this._configParameter = _configParameter;
        }
    }
}

