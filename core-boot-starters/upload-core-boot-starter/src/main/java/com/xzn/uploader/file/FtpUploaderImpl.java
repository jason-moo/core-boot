package com.xzn.uploader.file;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author ruancl@xkeshi.com
 * @date 2017/1/6
 * 文件夹规则  每天创建一个文件夹存放文件   文件名使用uuid
 */
public class FtpUploaderImpl implements FileUploader {

    private static Logger logger = LoggerFactory.getLogger(FtpUploaderImpl.class);

    private final String host;
    private final int port;
    private final String user;
    private final String password;

    private final String root;


    public FtpUploaderImpl(String host, int port, String user, String password, String root) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.root = root;
    }

    public FTPClient connectFtp() {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            ftpClient.login(user, password);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                logger.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                logger.info("FTP连接成功。");
                // 设置PassiveMode传输
                ftpClient.enterLocalPassiveMode();
                // 设置以二进制流的方式传输
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                ftpClient.setControlEncoding("UTF-8");
            }
        } catch (IOException e) {
            logger.error("io exception");
        }
        return ftpClient;
    }


    @Override
    public List<String> upload(List<FileEntry> pathFiles) {
        FTPClient ftpClient = connectFtp();
        List<String> list = new ArrayList<>();
        for (FileEntry fileEntry : pathFiles) {
            list.add(upload(ftpClient, fileEntry.getInputStream(), fileEntry.getExt()));
        }
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            logger.error("io exception");
        }
        return list;
    }

    @Override
    public Map<String, InputStream> downLoad(HashSet<String> filePaths) {
        FTPClient ftpClient = connectFtp();
        Map<String, InputStream> map = new HashMap<>(filePaths.size());
        for (String path : filePaths) {
            map.put(path, downLoad(ftpClient, path));
        }
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            logger.error("io exception");
        }
        return map;
    }


    private InputStream downLoad(FTPClient ftpClient, String filePath) {
        InputStream in = null;
        try {
            int index = filePath.lastIndexOf('/');
            String fileDirectory = filePath.substring(0, index + 1);
            ftpClient.changeWorkingDirectory(fileDirectory);
            in = ftpClient.retrieveFileStream(filePath);
        } catch (IOException e) {
            logger.error("io exception");
        }
        return in;
    }

    @Override
    public InputStream downLoad(String filePath) {
        FTPClient ftpClient = connectFtp();
        InputStream in = downLoad(ftpClient, filePath);
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            logger.error("io exception");
        }
        return in;
    }

    @Override
    public String upload(InputStream in, String ext) {
        FTPClient ftpClient = connectFtp();
        String returnPath = upload(ftpClient, in, ext);
        try {
            ftpClient.disconnect();
        } catch (IOException e) {
            logger.error("io exception");
        }
        return returnPath;
    }

    private String upload(FTPClient ftpClient, InputStream in, String ext) {

        String fileName;
        String fileDirectory;
        String filePath;
        try {
            fileName = UUID.randomUUID().toString() + "." + ext;
            fileDirectory = root + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
            if (ftpClient.makeDirectory(fileDirectory)) {
                //存在则false 跳过
                logger.info("不存在目录,新建{}", fileDirectory);
            }
            filePath = fileDirectory + "/" + fileName;
            boolean status = ftpClient.storeFile(filePath, in);
            if (!status) {
                logger.error("上传失败,可能是目录 {} 不存在,请先检查目录", fileDirectory);
            }
            in.close();
            logger.info("上传 {} 到ftp{} 地址:{}", fileName, status, filePath);
        } catch (FileNotFoundException e) {
            logger.info("ftp上文件不存在");
            filePath = null;
        } catch (IOException e) {
            logger.info("ftp上文件读取异常");
            filePath = null;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("io exception");
            }
        }

        return filePath;
    }

    public static void main(String[] args) {
        FtpUploaderImpl ftpHandler = new FtpUploaderImpl("localhost", 21, "huihui", "4870025",
                "/Users/huihui/Documents/");
        InputStream inputStream = new ByteArrayInputStream("hhh".getBytes(StandardCharsets.UTF_8));
        ftpHandler.upload(inputStream, "jpg");
    }
}
