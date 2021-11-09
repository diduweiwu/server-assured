package com.diduweiwu.server.response;

import com.github.dreamhead.moco.HttpResponseSetting;

/**
 * @author test
 */
public interface ResponseSetting {
    /**
     * 返回逻辑自定义配置
     *
     * @param responseSetting
     */
    void config(HttpResponseSetting responseSetting);
}
