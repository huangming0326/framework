package com.newegg.framework.common.data.access.configuration;

import com.newegg.framework.common.data.access.DataCommand;
import com.newegg.framework.common.data.access.common.DbCommand;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataOperationCommand {

    @XmlElement(name = "commandText")
    public String commandText;

    @XmlElement(name = "parameters")
    public DataOperationParameters parametersField;

    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "database")
    public String database;

    @XmlAttribute(name = "commandType")
    public CommandType commandType;

    public int mtimeOut;

    public DataCommand GetDataCommand() {
        DataCommand command = new DataCommand(database, GetDbCommand());
        Pattern pattern = Pattern.compile("@([A-Z]|[a-z]|\\d)+");
        String sqlText = this.commandText;
        Matcher matcher = pattern.matcher(sqlText);
        //SQL 中的参数列表 --顺序的
        LinkedHashMap<String, Object> parametKey = new LinkedHashMap<String, Object>();
        while (matcher.find()) {
            String name = matcher.group();
            if (this.parametersField.parameterList.hasKey(name)) {
                parametKey.put(name, this.parametersField.parameterList.get(name));
            } else if (this.parametersField.parameterGroupCollection.hasKey(name)) {
                parametKey.put(name, this.parametersField.parameterGroupCollection.get(name));
            }
        }
        command.setM_ConfigParameter(parametKey);
        return command;
    }

    private DbCommand GetDbCommand() {
        DbCommand dbCommand = new DbCommand();
        dbCommand.setCommandType(this.commandType);
        dbCommand.setCommandText(this.commandText);
        /*if (parameters.size() > 0) {
            dbCommand.setParameters(parameters.toArray(new String[parameters.size()]));
        }*/

/*
        StringBuffer buffer = new StringBuffer();
        buffer.append(sqlText);

        switch (this.commandType) {
            case StoredProcedure:
                buffer.insert(0, "call ");
                if (this.parametersField.parameterList.size() > 0) {
                    int i = 1;
                    while (i <= this.parametersField.parameterList.size()) {
                        buffer.append(" ?,");
                        i++;
                    }
                    buffer.delete(buffer.length() - 1, 1);
                }
                dbCommand.setCommandText(buffer.toString());
                break;
            default:
                for(String p : parameters){
                    sqlText.replace(p, "?");
                }
                dbCommand.setCommandText(sqlText);
        }*/

        return dbCommand;
    }

}
