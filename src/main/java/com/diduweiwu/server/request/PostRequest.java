package com.diduweiwu.server.request;

import com.github.dreamhead.moco.HttpResponseSetting;
import com.github.dreamhead.moco.HttpServer;
import lombok.experimental.SuperBuilder;

import static com.github.dreamhead.moco.Moco.match;
import static com.github.dreamhead.moco.Moco.uri;

/**
 * @author test
 */
@SuperBuilder
public class PostRequest extends BaseRequest {
    @Override
    public HttpResponseSetting compose(HttpServer server) {
        return server.post(match(uri(apiPath)));
    }
}
