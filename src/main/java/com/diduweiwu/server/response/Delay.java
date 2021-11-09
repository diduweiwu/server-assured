package com.diduweiwu.server.response;

import com.github.dreamhead.moco.HttpResponseSetting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.concurrent.TimeUnit;

import static com.github.dreamhead.moco.Moco.latency;

/**
 * @author diduweiwu
 */
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@SuperBuilder
public class Delay implements ResponseSetting {

    @Builder.Default
    private final long delay = 0L;

    @Builder.Default
    TimeUnit delayUint = TimeUnit.SECONDS;

    @Override
    public void config(HttpResponseSetting responseSetting) {
        responseSetting.response(latency(this.delay, this.delayUint));
    }
}
