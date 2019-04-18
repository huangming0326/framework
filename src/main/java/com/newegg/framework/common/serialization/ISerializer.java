package com.newegg.framework.common.serialization;

/// <summary>
/// 序列化器。
/// </summary>
public interface ISerializer {

    /// <summary>
    /// 把xml文件反序列化成对象。
    /// </summary>
    /// <typeparam name="T">要转换成的对象类型。</typeparam>
    /// <param name="fileName">xml文件。</param>
    /// <returns>对象。</returns>
    /// <remarks>
    /// 默认编码为UTF8。
    /// </remarks>
    <T> T FromFile(Class<T> tClass, String fileName);

    /// <summary>
    /// 把文本反序列化成对象。
    /// </summary>
    /// <typeparam name="T">要转换成的对象类型。</typeparam>
    /// <param name="serializedString">序列化的文本。</param>
    /// <returns>对象。</returns>
    /// <remarks>
    /// 默认编码为UTF8。
    /// </remarks>
    <T> T FromSerializedString(Class<T> tClass, String serializedString);

}
