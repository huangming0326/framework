package com.newegg.framework.common.data.access;

import com.newegg.framework.common.configuration.manager.ConfigManager;

public final class DatabaseManager {

    public static DataBaseInstance GetDatabaseInstance(String name) {
        DatabaseList list = null;
        try {
            list = ConfigManager.GetConfig(DatabaseList.class);

            if (list == null || list.DataBaseList == null || list.DataBaseList.size() == 0) {
                throw new Exception("DatabaseManager Error");
            }
            if (list.DataBaseList.hasKey(name)) {
                return list.DataBaseList.get(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
