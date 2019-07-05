package com.xzn.uploader.prop;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author ruancl@xkeshi.com
 * @date 2017/10/17.
 */
@ConfigurationProperties(prefix = "xkeshi.uploader.ftp")
@Validated
public class FtpProperties {

    private boolean enable = false;

    private String host = "127.0.0.1";

    private int port = 22;

    @NotNull(message = "请填写ftp账号")
    private String user;

    @NotNull(message = "请填写ftp密码")
    private String password;

    @NotNull(message = "请填写ftp根目录")
    private String rootDir;


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }
}
