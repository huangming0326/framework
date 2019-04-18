package com.newegg.framework.common.data.access.configuration;

import com.newegg.framework.common.configuration.attributes.ConfigFile;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@ConfigFile(fileName = "/data/DataCommand_*.config")
@XmlRootElement(name = "dataOperations")
public class DataOperationConfiguration {

    @XmlElement(name = "dataCommand")
    public List<DataOperationCommand> dataCommandList;
}
