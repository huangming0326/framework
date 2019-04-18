package com.newegg.framework.common.data.access;

import com.newegg.framework.common.collections.KeyedCollection;
import com.newegg.framework.common.configuration.attributes.ConfigFile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

//@ConfigFile(fileName = "D:\\Program Files (x86)\\JetBrains\\IDEA\\demo\\Database.config")
@ConfigFile(fileName = "data/Database.config")
@XmlRootElement(name = "databaseList")
public class DatabaseList {
    @XmlElementWrapper(name = "dbGroup")
    @XmlElement(name = "database")
    public KeyedCollection<DataBaseInstance> DataBaseList;
}
