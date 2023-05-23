package com.oom.temporal.workers.workflow;

import com.oom.temporal.workers.activties.CancelableActivities;
import com.oom.temporal.workers.activties.LoadedActivities;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.temporal.activity.ActivityCancellationType;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.CancellationScope;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
public class LoadedWorkflowImpl implements LoadedWorkflow {

    ActivityOptions options = ActivityOptions.newBuilder()
            .setStartToCloseTimeout(Duration.ofMinutes(10))
            .setRetryOptions(RetryOptions.newBuilder()
                    .setInitialInterval(Duration.ofSeconds(2))
                    .setMaximumInterval(Duration.ofSeconds(2))
                    .build())
            .build();

    private final LoadedActivities activity = Workflow
            .newActivityStub(LoadedActivities.class, options);

    @Override
    public String runLoadedWorkflow(String name) {
        activity.tasFunction01(name);
        activity.tasFunction02(name);
        activity.tasFunction03(name);
        activity.tasFunction04(name);
        activity.tasFunction05(name);
        activity.tasFunction06(name);
        activity.tasFunction07(name);
        activity.tasFunction08(name);
        activity.tasFunction09(name);
        activity.tasFunction10(name);
        return name;
    }
}
