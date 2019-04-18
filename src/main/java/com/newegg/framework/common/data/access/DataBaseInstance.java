package com.newegg.framework.common.data.access;

import com.newegg.framework.common.collections.IKeyedObject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

public class DataBaseInstance implements Serializable, IKeyedObject<String> {
    @XmlAttribute(name = "name")
    public String Name;

    @XmlElement(name = "connectionString")
    public String ConnectionString;

    @Override
    public String getKey() {
        return Name;
    }
}
