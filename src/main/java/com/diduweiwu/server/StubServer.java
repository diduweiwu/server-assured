package com.diduweiwu.server;

import com.diduweiwu.server.request.*;
import com.diduweiwu.server.response.ResponseSetting;
import com.github.dreamhead.moco.HttpServer;
import com.github.dreamhead.moco.Runner;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.util.Objects;

import static com.github.dreamhead.moco.Moco.httpServer;

/**
 * 场景接口调用编排
 *
 * @author diduweiwu
 */
@Slf4j
public class StubServer implements Closeable {
    /**
     * 初始化response
     */
    private HttpServer server;

    /**
     * 服务启动器
     */
    private Runner runner;

    @Override
    public void close() {
        this.stop();
    }

    /**
     * 初始化服务对象
     *
     * @param port
     * @return
     */
    public static StubServer of(int port) {
        StubServer server = new StubServer();
        // 初始化服务器请求
        server.server = httpServer(port);
        return server;
    }

    /**
     * 添加一个get请求,支持回调配置
     *
     * @param apiPath
     * @param data
     * @param responseSettings
     * @return
     */
    public StubServer get(String apiPath, Object data, ResponseSetting... responseSettings) {
        GetRequest.builder()
                .apiPath(apiPath)
                .data(data)
                .responseSettings(responseSettings)
                .build()
                .config(this.server);
        return this;
    }

    /**
     * post请求
     *
     * @param apiPath
     * @param data
     * @param responseSettings
     * @return
     */
    public StubServer post(String apiPath, Object data, ResponseSetting... responseSettings) {
        PostRequest.builder()
                .apiPath(apiPath)
                .data(data)
                .responseSettings(responseSettings)
                .build()
                .config(this.server);
        return this;
    }

    /**
     * put请求
     *
     * @param apiPath
     * @param data
     * @param options
     * @return
     */
    public StubServer put(String apiPath, Object data, ResponseSetting... responseSettings) {
        PutRequest.builder()
                .apiPath(apiPath)
                .data(data)
                .responseSettings(responseSettings)
                .build()
                .config(this.server);
        return this;
    }

    /**
     * delete请求
     *
     * @param apiPath
     * @param data
     * @param options
     * @return
     */
    public StubServer delete(String apiPath, Object data, ResponseSetting... responseSettings) {
        DeleteRequest.builder()
                .apiPath(apiPath)
                .data(data)
                .responseSettings(responseSettings)
                .build()
                .config(this.server);
        return this;
    }

    /**
     * 自定义接口请求
     *
     * @param request Request
     * @return
     */
    public StubServer request(Request request) {
        request.config(this.server);
        return this;
    }

    /**
     * 服务配置完成,执行同步启动
     *
     * @return
     */
    public StubServer start() {
        if (Objects.nonNull(this.runner)) {
            log.warn("服务已经启动,忽略start操作");
            return this;
        }
        this.runner = Runner.runner(this.server);

        Objects.requireNonNull(this.runner, "启动对象不能为空!");
        this.runner.start();
        return this;
    }

    /**
     * 重启服务,其实就是先关闭再启动
     *
     * @return
     */
    public StubServer restart() {
        return this.stop().start();
    }

    /**
     * 停止服务
     *
     * @return
     */
    public StubServer stop() {
        if (Objects.isNull(this.runner)) {
            log.warn("服务还未启动,忽略stop操作");
            return this;
        }
        this.runner.stop();
        this.runner = null;
        return this;
    }
}
