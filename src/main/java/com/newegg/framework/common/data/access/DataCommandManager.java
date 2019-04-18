package com.newegg.framework.common.data.access;

import com.newegg.framework.common.configuration.manager.ConfigManager;
import com.newegg.framework.common.data.access.configuration.DataOperationCommand;
import com.newegg.framework.common.data.access.configuration.DataOperationConfiguration;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DataCommandManager {
    private static final String EventCategory = "DataCommandManager";
    //Logger
    private static final Object s_CommandFileListSyncObject = new Object();

    private static HashMap<String, DataCommand> s_DataCommands = new HashMap<String, DataCommand>();

    public DataCommandManager() {
        System.out.println("DataCommandManager is Scan !");
        //初始化 查询数据库所用的配置文件
        try {
            EnsureCommandLoaded();
        } catch (Exception ex) {
            //logger.Error
        }
    }

    private static void EnsureCommandLoaded() throws Exception {
        synchronized (s_CommandFileListSyncObject) {
            HashMap<String, DataCommand> newCommands = new HashMap<String, DataCommand>();
            //获取所有sql 配置
            DataOperationConfiguration commands = ConfigManager.GetConfig(DataOperationConfiguration.class);
            if (commands == null) {
                throw new Exception("SQL 脚本配置错误");
            }
            if (commands.dataCommandList != null && commands.dataCommandList.size() > 0) {
                for (DataOperationCommand cmd : commands.dataCommandList) {
                    if (DataCommandManager.s_DataCommands.containsKey(cmd.name)) {
                        throw new Exception(String.format("The DataCommand configuration file has a same key %s now.", cmd.name));
                    }
                    s_DataCommands.put(cmd.name, cmd.GetDataCommand());

                }
            }
        }
    }

    public static DataCommand GetDataCommand(String name) {
        // Logger.LogSystemInfo(EventCategory, "Retrieving datacommand: " + name);
        try {
            return (DataCommand) s_DataCommands.get(name).clone();
        } catch (Exception ex) {
            return null;
        }
    }
}
