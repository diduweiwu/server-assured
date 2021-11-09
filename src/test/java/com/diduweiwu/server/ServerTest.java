package com.diduweiwu.server;

import cn.hutool.core.map.MapUtil;
import cn.hutool.http.HttpUtil;
import com.diduweiwu.server.response.Delay;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ServerTest {

    @Test
    @SneakyThrows
    public void testServer() {
        // 演示自定义开启和关闭服务
        StubServer serverInit = StubServer
                .of(9998)
                .get("/get", MapUtil.of("key", "future"))
                .get("/get2", MapUtil.of("key", "future"))
                .post("/post", MapUtil.of("key", "future"))
                .put("/put", "put", Delay.builder().delay(1).build())
                .delete("/delete", "delete")
                .get("/file", Files.createTempFile("temp", ".txt").toFile());
        try (StubServer serverRunning = serverInit.start()) {
            String response = HttpUtil.get("http://localhost:9998/get");
            log.info("Response Value is {}", response);

            TimeUnit.SECONDS.sleep(5);
        }
    }
}
