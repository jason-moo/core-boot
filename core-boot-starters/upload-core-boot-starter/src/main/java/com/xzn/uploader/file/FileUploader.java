package com.xzn.uploader.file;


import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author ruancl@xkeshi.com
 * @date 2017/1/10
 */
public interface FileUploader {

    /**
     * 文件上传
     *
     * @param in
     * @param ext 文件后缀   不含'.'
     * @return
     */
    String upload(InputStream in, String ext);

    /**
     * 批量上传
     *
     * @param pathFiles
     * @return
     */
    List<String> upload(List<FileEntry> pathFiles);

    /**
     * 文件下载
     *
     * @param filePath 下载路径
     * @return
     */
    InputStream downLoad(String filePath);

    /**
     * 批量下载
     *
     * @param filePaths
     * @return
     */
    Map<String, InputStream> downLoad(HashSet<String> filePaths);


}
