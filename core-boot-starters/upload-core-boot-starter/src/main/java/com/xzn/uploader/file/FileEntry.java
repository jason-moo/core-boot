package com.xzn.uploader.file;

import java.io.InputStream;

/**
 * @author ruancl@xkeshi.com
 * @date 2017/1/11
 */
public class FileEntry {
    private String ext;
    private InputStream inputStream;

    public FileEntry(String ext, InputStream inputStream) {
        this.ext = ext;
        this.inputStream = inputStream;
    }

    public String getExt() {
        return ext;
    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
