package com.oom.temporal.baremin.activties;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.temporal.activity.Activity;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class SimpleActivitiesImpl implements SimpleActivities {
    @Override
    @Timed(value = "simpleTaskFunction1.timer")
    @Counted(value = "simpleTaskFunction1.counter")
    public String simpleTaskFunction1(String name) {
        var sleepSeconds = new Random().nextInt(60);
        log.info("simpleTaskFunction1:{}, sleep:{}s", name, sleepSeconds);
        try {
            Thread.sleep(sleepSeconds *1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return name+">simpleTaskFunction1";
    }

    @Override
    @Timed(value = "longRunningTaskFunction2.timer")
    @Counted(value = "longRunningTaskFunction2.counter")
    public String longRunningTaskFunction2(String name) {
        String runId = String.format(" [%d]  ", new Random().nextInt(100, 999));
        log.info("{} taskFunction2:{}",runId, name);


        int startPoint =
                Activity.getExecutionContext()
                        .getHeartbeatDetails(Integer.class)
                        .orElse(0);
        System.out.println(runId + "Retrieved HeartBeat: " + startPoint);
        log.info("{} Retrieved HeartBeat: {}",runId, startPoint);
        var bhSecs = 3;
        for (int i = startPoint; i <= 50; i++) {
            Activity.getExecutionContext().heartbeat(i);
            log.info("{} Sent out HeartBeat: {}, every {}secs",runId, i,bhSecs);
            try {
                Thread.sleep(bhSecs*1000);
            } catch (InterruptedException ignored) { }
        }

        return name+">longRunningTaskFunction2";
    }
}
