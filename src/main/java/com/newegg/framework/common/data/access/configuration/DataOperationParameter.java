package com.newegg.framework.common.data.access.configuration;


import javax.xml.bind.annotation.XmlAttribute;

public class DataOperationParameter extends OperationParameter {

    @XmlAttribute(name = "precision")
    public byte precision = 0;

    @XmlAttribute(name = "scale")
    public byte scale = 0;
}
