package com.newegg.framework.common.serialization;

import com.newegg.framework.common.utility.xml.JaxbUtil;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import java.io.File;

public class SerializerBase implements ISerializer {

    @Override
    public <T> T FromFile(Class<T> tClass, String fileName) {
        SAXReader reader = new SAXReader();
        Document doc = null;
        File file = new File(fileName);
        try {
            doc = reader.read(file);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        String documentStr = doc.asXML();
        JaxbUtil resultBinder = new JaxbUtil(tClass, JaxbUtil.CollectionWrapper.class);
        return resultBinder.fromXml(documentStr);
    }

    @Override
    public <T> T FromSerializedString(Class<T> tClass, String serializedString) {
        JaxbUtil resultBinder = new JaxbUtil(tClass, JaxbUtil.CollectionWrapper.class);
        return resultBinder.fromXml(serializedString);
    }
}
