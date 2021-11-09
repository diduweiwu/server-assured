package com.diduweiwu.server.request;

import com.diduweiwu.server.response.ResponseSetting;
import com.github.dreamhead.moco.HttpResponseSetting;
import com.github.dreamhead.moco.HttpServer;
import lombok.experimental.SuperBuilder;

import java.io.File;
import java.util.Objects;

import static com.github.dreamhead.moco.Moco.attachment;
import static com.github.dreamhead.moco.Moco.file;
import static com.github.dreamhead.moco.util.Jsons.toJson;

/**
 * @author test
 */
@SuperBuilder
public abstract class BaseRequest implements Request {
    protected String apiPath;
    protected Object data;
    protected ResponseSetting[] responseSettings;

    /**
     * 子类负责具体请求类型
     *
     * @param server HttpServer
     * @return HttpResponseSetting
     */
    protected abstract HttpResponseSetting compose(HttpServer server);

    @Override
    public void config(HttpServer server) {
        HttpResponseSetting responseSetting = this.compose(server);
        composeResponseValue(responseSetting, this.data);
        composeResponseSettings(responseSetting, this.responseSettings);
    }

    /**
     * 设置接口返回数据
     */
    protected void composeResponseValue(HttpResponseSetting responseSetting, Object data) {
        if (Objects.isNull(data)) {
            return;
        }

        // 字符串类型返回文本
        if (data instanceof String) {
            responseSetting.response(String.valueOf(data));
            return;
        }

        // 文件类型转换为file流
        if (data instanceof File) {
            File file = (File) data;
            responseSetting.response(attachment(file.getName(), file(file.getAbsolutePath())));
            return;
        }

        // 其他类型尝试转为json
        responseSetting.response(toJson(data));
    }

    /**
     * 设置接口返回额外配置
     *
     * @param responseSetting  HttpResponseSetting
     * @param responseSettings ResponseSetting[]
     */
    protected void composeResponseSettings(HttpResponseSetting responseSetting, ResponseSetting... responseSettings) {
        for (ResponseSetting setting : responseSettings) {
            Objects.requireNonNull(setting, "自定义配置不能为空");
            setting.config(responseSetting);
        }
    }
}
