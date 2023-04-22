package com.oom.temporal.baremin.activties;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class KitchenSinkActivitiesImpl implements KitchenSinkActivities {
    @Override
    @Timed(value = "KitchenSink.Activity.timer")
    @Counted(value = "KitchenSink.Activity.counter")
    public String doTask(String name) {
        return name+">KitchenSink.Activity:task";
    }

}
