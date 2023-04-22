package com.oom.temporal.baremin.activties;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class SimpleActivitiesImpl implements SimpleActivities {
    @Override
    @Timed(value = "taskFunction1.timer")
    @Counted(value = "taskFunction1.counter")
    public String taskFunction1(String name) {
        var sleepSeconds = new Random().nextInt(60);
        log.info("taskFunction1:{}, sleep:{}s", name, sleepSeconds);
        try {
            Thread.sleep(sleepSeconds *1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return name+">func1";
    }

    @Override
    @Timed(value = "taskFunction2.timer")
    @Counted(value = "taskFunction2.counter")
    public String taskFunction2(String name) {
        log.info("taskFunction2:{}", name);
        return name+">func2";
    }
}
