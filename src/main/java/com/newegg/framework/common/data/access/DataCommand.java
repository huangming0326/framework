package com.newegg.framework.common.data.access;

import com.newegg.framework.common.data.access.common.Database;
import com.newegg.framework.common.data.access.common.DbCommand;
import com.newegg.framework.common.data.access.common.SqlDatabase;
import com.newegg.framework.common.data.access.configuration.CommandType;
import com.newegg.framework.common.data.access.configuration.DataOperationParameter;
import com.newegg.framework.common.data.access.configuration.DataOperationParameterGroup;
import com.newegg.framework.common.data.access.configuration.OperationParameter;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class DataCommand implements Cloneable {

    private DbCommand m_dbCommand;
    private String m_databaseName;
    private DataBaseInstance m_DbInstance;
    private boolean m_SupportTransaction;
    private Connection m_Connection;
    //输入的参数
    private HashMap<String, Object> m_InputParameter = new HashMap<String, Object>();
    //sql 中的参数(顺序的)
    LinkedHashMap<String, Object> m_ConfigParameter = new LinkedHashMap<String, Object>();

    public DataCommand(String databaseName, DbCommand dbCommand) {
        this.setM_databaseName(databaseName);
        this.m_dbCommand = dbCommand;
    }

    private Database ActualDatabase() {
        return new SqlDatabase(m_DbInstance.ConnectionString);
    }

    public void setM_ConfigParameter(LinkedHashMap<String, Object> m_ConfigParameter) {
        this.m_ConfigParameter = m_ConfigParameter;
    }

    public void setM_DbInstance(DataBaseInstance m_DbInstance) {
        this.m_DbInstance = m_DbInstance;
    }

    public void setM_databaseName(String m_databaseName) {
        this.m_databaseName = m_databaseName;
        if (!StringUtils.isEmpty(m_databaseName)) {
            this.setM_DbInstance(DatabaseManager.GetDatabaseInstance(m_databaseName));
        }
    }

    public void SetParameterValue(String paramName, Object val) {
        this.m_InputParameter.put(paramName, val);
    }

    public void SetParameterGroupValue(String name, String parameters) {

    }

    private String SetParameterGroupValue(DbCommand dbCommand, OperationParameter para, Object obj) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        if (obj instanceof Collection<?>) {
            Collection<?> parameters = (Collection<?>) obj;
            parameters.forEach(value -> {
                OperationParameter sub = (OperationParameter) para.clone();
                sub.setValue(value);
                String newName = String.format("%s_%s", sub.name, String.valueOf(i));
                sb.append(",?");
                dbCommand.addParametKey(newName, sub);
            });
            sb.delete(0, 1);
        }
        return sb.toString();
    }

    public int ExecuteNonQuery() {
        PrepareCommand(this.m_dbCommand);
        return ActualDatabase().ExecuteNonQuery(this.m_dbCommand);
    }

    public ResultSet ExecuteDataSet() {
        PrepareCommand(this.m_dbCommand);
        return ActualDatabase().ExecuteResultSet(this.m_dbCommand);
    }

    //准备命令
    private void PrepareCommand(DbCommand dbCommand) {
        String sqlText = dbCommand.getCommandText();
        if (dbCommand.getCommandType() == CommandType.Text) {
            //1、拿到SQL中顺序的参数
            //2、循环参数
            //3.1组装输入参数，并将SQL中的参数用？代替
            //3.2 如果是List，将值循环作为新的参数（name_0,name_1）组装，并将SQL中的参数用（?,?,?...）代替
            if (this.m_ConfigParameter.size() > 0) {
                for (Map.Entry<String, Object> para : this.m_ConfigParameter.entrySet()) {
                    String key = para.getKey();
                    Object paraValue = para.getValue();
                    OperationParameter p = (OperationParameter) paraValue;
                    if (paraValue instanceof DataOperationParameter) {
                        p.setValue(this.m_InputParameter.get(key));
                        dbCommand.addParametKey(key, p);
                        sqlText = sqlText.replace(key, "?");
                    } else if (paraValue instanceof DataOperationParameterGroup) {
                        Object value = this.m_InputParameter.get(key);
                        String paramStr = SetParameterGroupValue(dbCommand, p, value);
                        sqlText = sqlText.replace(key, paramStr);
                    }
                }
            }
            if (dbCommand.getParametKey().size() > 0) {
                String[] parameters = new String[dbCommand.getParametKey().size()];
                int i = 0;
                for (Map.Entry<String, OperationParameter> para : dbCommand.getParametKey().entrySet()) {
                    parameters[i] = para.getKey();
                    i++;
                }
                dbCommand.setParameters(parameters);
            }
            dbCommand.setCommandText(sqlText);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(sqlText);
            sb.insert(0, "call ");
            if (this.m_ConfigParameter.size() > 0) {
                int i = 1;
                while (i <= this.m_ConfigParameter.size()) {
                    sb.append(" ,?");
                    i++;
                }
                sb.delete(0, 1);
            }
            dbCommand.setCommandText(sb.toString());
        }
    }


    private void PrepareTransaction() {
        //m_Connection = ActualDatabase().CreateConnection();


    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
