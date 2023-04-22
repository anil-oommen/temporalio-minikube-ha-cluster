package com.oom.temporal.baremin.workflow;

import com.oom.temporal.baremin.activties.SimpleActivities;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
public class SimpleWorkflowImpl implements SimpleWorkflow {

    ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofSeconds(60))
            .build();
    private final SimpleActivities activity = Workflow
            .newActivityStub(SimpleActivities.class, options);

    @Override
    @Timed(value = "simpleWorkflow.timer")
    @Counted(value = "simpleWorkflow.counter")
    public String start(String name) {
        log.info("startWorkflow:{}", name);
        var ret1 = activity.taskFunction1(name);
        var ret2 = activity.taskFunction2(ret1);
        log.info("endWorkflow:{}", ret2);
        return ret2;
    }
}
