package com.xzn.ons;

import com.aliyun.openservices.ons.api.Message;

public class DefaultMessageProcess extends AbstractMessageProcess {

    @Override
    protected void doProcess(Message message) {
        if (logger.isInfoEnabled()) {
            logger.info("没有找到正确的消息处理类。。。");
        }
    }

    /************
     * 单例配置
     *********/
    static class SingletonHolder {
        static DefaultMessageProcess instance = new DefaultMessageProcess();
    }

    public static DefaultMessageProcess getInstance() {
        return SingletonHolder.instance;
    }

    private DefaultMessageProcess() {
    }
}
