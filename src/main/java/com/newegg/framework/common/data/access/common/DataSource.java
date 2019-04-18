package com.newegg.framework.common.data.access.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.HashMap;

public class DataSource {

    //private static HikariConfig config = new HikariConfig();
    //private static HikariDataSource ds;
    private static HashMap<String, HikariDataSource> dss = new HashMap<String, HikariDataSource>();
    private static Object _syncObj = new Object();

    ///获取连接
    public static HikariDataSource getConnection(Database db) throws SQLException {
        if (!dss.containsKey(db.connectionString)) {
            synchronized (_syncObj) {
                if (!dss.containsKey(db.connectionString)) {
                    HikariConfig config = new HikariConfig();
                    config.setJdbcUrl(db.connectionString);
                    dss.put(db.connectionString, new HikariDataSource(config));
                }
            }
        }
        return dss.get(db.connectionString);
    }
}
