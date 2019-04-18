package com.newegg.framework.common.configuration.manager;

public interface IConfigChangeWatcher extends AutoCloseable {
    public IConfigParameter getParameter();
}
