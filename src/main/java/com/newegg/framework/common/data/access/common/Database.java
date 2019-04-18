package com.newegg.framework.common.data.access.common;

import com.newegg.framework.common.data.access.configuration.OperationParameter;

import java.sql.*;
import java.util.HashMap;

public abstract class Database {

    protected String connectionString;

    public Database(String connectionString) {
        this.connectionString = connectionString;
    }

    ///打开连接
    private Connection GetOpenConnection() throws SQLException {
        return DataSource.getConnection(this).getConnection();
    }

    private PreparedStatement GetPreparedStatement(Connection con,DbCommand command) throws SQLException {
        PreparedStatement ps = con.prepareStatement(command.getCommandText());
        HashMap<String, OperationParameter> paramets = command.getParametKey();
        String[] parameters = command.getParameters();
        for (int i = 1; i <= parameters.length; i++) {
            OperationParameter subp = paramets.get(parameters[i - 1]);
            ps.setObject(i, subp.content, subp.dbType);
        }
        return ps;
    }

    public int ExecuteNonQuery(DbCommand command) {
        try (Connection con = GetOpenConnection()) {
            PreparedStatement ps = GetPreparedStatement(con,command);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ResultSet ExecuteResultSet(DbCommand command){
        try (Connection con = GetOpenConnection()) {
            PreparedStatement ps = GetPreparedStatement(con,command);
            ResultSet set =ps.executeQuery();
            while (set.next()) {  //通过next来索引：判断是否有下一个记录
                String name = set.getString("CustomerID");
                String Status = set.getString("Status");
                Timestamp timestamp = set.getTimestamp("RegisterTime");
                java.util.Date StartDate  = new java.util.Date(timestamp.getTime());
                System.out.println("name=" + name + ",Status=" + Status + ",StartDate=" + StartDate.toString());
            }

            return set;



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
