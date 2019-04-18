package com.newegg.framework.common.data.access.configuration;

import com.newegg.framework.common.collections.IKeyedObject;

import javax.xml.bind.annotation.XmlAttribute;
import java.sql.JDBCType;

public class OperationParameter implements IKeyedObject<String>, Cloneable {

    @XmlAttribute(name = "name")
    public String name;

    @XmlAttribute(name = "dbType")
    public JDBCType dbType;

    @XmlAttribute(name = "size")
    public int size;

    @XmlAttribute(name = "direction")
    public ParameterDirection direction;

    public Object content;

    public void setValue(Object val) {
        this.content = val;
    }

    public Object getValue() {
        return content;
    }

    @Override
    public String getKey() {
        return this.name;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return new OperationParameter();
    }
}
