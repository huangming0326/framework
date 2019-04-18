package com.newegg.framework.common.data.access.common;

import com.newegg.framework.common.data.access.configuration.CommandType;
import com.newegg.framework.common.data.access.configuration.OperationParameter;

import java.util.LinkedHashMap;

public class DbCommand {
    private String commandText;
    private int commandTimeout;
    private CommandType commandType;

    private LinkedHashMap<String, OperationParameter> paramets = new LinkedHashMap<String, OperationParameter>();
    private String[] parameters;

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public int getCommandTimeout() {
        return commandTimeout;
    }

    public void setCommandTimeout(int commandTimeout) {
        this.commandTimeout = commandTimeout;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }

    public LinkedHashMap<String, OperationParameter> getParametKey() {
        return this.paramets;
    }

    public void addParametKey(String key, OperationParameter paramet) {
        this.paramets.put(key,paramet);
    }
}
