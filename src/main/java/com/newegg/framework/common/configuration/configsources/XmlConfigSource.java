package com.newegg.framework.common.configuration.configsources;

import com.newegg.framework.common.configuration.attributes.ConfigFile;
import com.newegg.framework.common.configuration.frameworkconfig.configsettings.ConfigSettings;
import com.newegg.framework.common.configuration.manager.AbstractConfigSource;
import com.newegg.framework.common.configuration.manager.ConfigParameterCache;
import com.newegg.framework.common.configuration.manager.IConfigParameter;
import com.newegg.framework.common.configuration.utility.AttributeHelper;
import com.newegg.framework.common.serialization.XmlSerializerWrapper;
import com.newegg.framework.common.utility.xml.XmlUtils;

import java.util.ArrayList;
import java.util.List;

public class XmlConfigSource extends AbstractConfigSource {

    private static XmlConfigSource configSource = new XmlConfigSource();

    public XmlConfigSource() {

    }

    public static XmlConfigSource getConfigSource() {
        return configSource;
    }


    @Override
    public <T> T GetConfig(Class<T> tClass) throws Exception {

        List<IConfigParameter> getParameters = ConfigParameterCache.GetList(tClass);
        if (getParameters == null) {
            synchronized (this.SyncLock()) {
                getParameters = ConfigParameterCache.GetList(tClass);
                if (getParameters == null) {

                    getParameters = new ArrayList<IConfigParameter>();
                    ConfigFile attribute = (ConfigFile) AttributeHelper.GetConfigAttribute(tClass);
                    if (attribute == null) {
                        throw new Exception("");
                    }

                    List<String> configPaths = ConfigSettings.GetConfigPaths(tClass);
                    for (String path : configPaths) {
                        XmlConfigGetParameter getParameter = new XmlConfigGetParameter(path);
                        getParameters.add(getParameter);
                    }
                    ConfigParameterCache.Set(tClass, getParameters);
                }
            }
        }

        T config = null;
        boolean success = false;
        for (IConfigParameter getParameter : getParameters) {
            try {
                config = GetConfig(tClass, getParameter, true);
                ConfigParameterCache.Set(tClass, getParameter);
                success = true;
                break;
            } catch (Exception ex) {
                //configErrorList.Add(getParameter, ex);
                System.out.println(ex.toString());
            }
        }


        return config;
    }

    @Override
    public <T> T GetConfig(Class<T> tClass, IConfigParameter getParameter, boolean restartAppDomainOnChange) {
        T config = this.GetConfigFromCache(getParameter);
        if (config == null) {
            synchronized (this.SyncLock()) {
                config = this.GetConfigFromCache(getParameter);
                if (config == null) {
                    XmlConfigGetParameter cp = (XmlConfigGetParameter) getParameter;
                    if (cp.getFiles().length > 0) {
                        if (cp.getFiles().length == 1) {
                            config = XmlSerializerWrapper.GetInstance().FromFile(tClass, cp.getFiles()[0]);
                        } else {
                            //合并XML
                            //try {
                                XmlUtils.FileMergeResult fileMergeResult = XmlUtils.getInstance().MergeFiles(cp.getFiles());
                                if(fileMergeResult.HasFileMerged()){
                                    config = XmlSerializerWrapper.GetInstance().FromSerializedString(tClass, fileMergeResult.getFileContentMerged());
                                }
                            //} catch (ParserConfigurationException e) {
                             //   e.printStackTrace();
                            //}
                        }
                    }
                    AddConfigToCache(getParameter, config);
                }
            }
        }
        return config;
    }

    @Override
    public <T> T GetMergedConfig(Class<T> tClass) {
        return null;
    }

    @Override
    public <T> T GetMergedConfig(Class<T> tClass, IConfigParameter getParameter, boolean restartAppDomainOnChange) {
        return null;
    }
}
