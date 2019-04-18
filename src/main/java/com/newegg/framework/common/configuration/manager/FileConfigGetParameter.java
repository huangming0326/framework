package com.newegg.framework.common.configuration.manager;

import com.newegg.framework.common.io.PathUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;

/*
 * 获取文件配置参数
 * */
public abstract class FileConfigGetParameter implements IConfigParameter {


    private String[] files;
    private String directory;
    private String filter;
    private Boolean includeSubdirectories;

    public FileConfigGetParameter() {
        this.setDirectory("");
        this.setFilter("*.config");
        this.setIncludeSubdirectories(false);
    }

    public FileConfigGetParameter(String path) {
        this(PathUtils.GetDirectoryName(path), PathUtils.GetFileName(path));
    }

    public FileConfigGetParameter(String path, String filter) {
        this(path, filter, false);
    }


    public FileConfigGetParameter(String path, boolean includeSubdirectories) {
        this(PathUtils.GetDirectoryName(path), PathUtils.GetFileName(path), includeSubdirectories);
    }

    public FileConfigGetParameter(String path, String filter, boolean includeSubdirectories) {
        this.setDirectory(PathUtils.GetFullPath(path));
        this.setFilter(filter);
        this.setIncludeSubdirectories(includeSubdirectories);
    }

    public boolean HasFiles() {
        return this.RefreshFiles();
    }

    public String[] getFiles() {
        if (files == null || files.length == 0) {
            this.RefreshFiles();
        }
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        directory = (directory == null) ? "" : directory;
        if (this.directory == null || !this.directory.toUpperCase().equals(directory.toUpperCase())) {
            this.directory = directory.replace("\\", "/");
            if (!this.directory.endsWith("/")) {
                this.directory = this.directory + "/";
            }
        }
    }

    public String getFilter() {
        return filter;
    }

    /// <summary>
    /// 设置筛选字符串，用于确定在目录中那些文件用于配置。
    /// </summary>
    /// <value>筛选器字符串。默认值为“*.*”（所有文件用于配置）。</value>
    public void setFilter(String filter) {
        if (filter == null || filter == "") {
            filter = "*.config";
        }
        filter = filter.replace("\\", "/");
        if (filter.startsWith("/")) {
            filter = filter.substring(1);
        }
        if (this.filter == null || !this.filter.toUpperCase().equals(filter.toUpperCase())) {
            this.filter = filter;
        }
    }

    public Boolean getIncludeSubdirectories() {
        return includeSubdirectories;
    }

    public void setIncludeSubdirectories(Boolean includeSubdirectories) {
        this.includeSubdirectories = includeSubdirectories;
    }

    @Override
    public String Name() {
        return String.format("FileConfig_%s", PathUtils.UrlPathCombine(this.getDirectory(), this.getFilter()));
    }

    @Override
    public String GroupName() {
        return String.format("FileConfig_%s", PathUtils.UrlPathCombine(this.getDirectory(), this.getFilter()));
    }

    /// <summary>
    /// 刷新配置入口参数对应的配置文件。
    /// </summary>
    public boolean RefreshFiles() {
        File file = new File(this.getDirectory());
        FileFilter fileFilter = new WildcardFileFilter(this.getFilter());
        File[] tempList = file.listFiles(fileFilter);
        String[] filePaths = new String[tempList.length];
        if (tempList.length > 0) {
            for (int i = 0; i < tempList.length; i++) {
                filePaths[i] = tempList[i].getPath();
            }
            this.setFiles(filePaths);
        }
        return filePaths != null && filePaths.length > 0;
    }
}
