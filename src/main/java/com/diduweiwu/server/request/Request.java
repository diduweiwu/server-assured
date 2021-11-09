package com.diduweiwu.server.request;

import com.github.dreamhead.moco.HttpServer;

/**
 * @author test
 */
public interface Request {
    /**
     * 配置server请求
     *
     * @param server HttpServer
     */
    void config(HttpServer server);
}
