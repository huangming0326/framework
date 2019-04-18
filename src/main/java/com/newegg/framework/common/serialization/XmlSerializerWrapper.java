package com.newegg.framework.common.serialization;

public class XmlSerializerWrapper extends SerializerBase {
    private static ISerializer serializer = new XmlSerializerWrapper();

    /// <summary>
    /// 当前实例。
    /// </summary>
    public static ISerializer GetInstance()
    {
        return serializer;
    }
}
