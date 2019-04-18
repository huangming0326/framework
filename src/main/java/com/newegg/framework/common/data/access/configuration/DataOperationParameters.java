package com.newegg.framework.common.data.access.configuration;

import com.newegg.framework.common.collections.KeyedCollection;

import javax.xml.bind.annotation.XmlElement;

public class DataOperationParameters {

    @XmlElement(name = "param")
    public KeyedCollection<DataOperationParameter> parameterList;

    @XmlElement(name = "paramGroup")
    public KeyedCollection<DataOperationParameterGroup> parameterGroupCollection;

}
