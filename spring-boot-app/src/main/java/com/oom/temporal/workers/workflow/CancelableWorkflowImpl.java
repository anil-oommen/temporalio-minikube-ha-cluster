package com.oom.temporal.workers.workflow;

import com.oom.temporal.workers.Shared;
import com.oom.temporal.workers.activties.CancelableActivities;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.temporal.activity.ActivityCancellationType;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Async;
import io.temporal.workflow.CancellationScope;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@WorkflowImpl(taskQueues = Shared.TASK_QUEUE)
public class CancelableWorkflowImpl implements CancelableWorkflow {

    ActivityOptions options = ActivityOptions.newBuilder()
            /*Scenario 1 */
            .setStartToCloseTimeout(Duration.ofSeconds(30))
            .setHeartbeatTimeout(Duration.ofSeconds(5))
            /*Scenario 2: long setStartToCloseTimeout(300) but no HeartBeatTimeOut (none after cancel received)*/
            .setCancellationType(ActivityCancellationType.WAIT_CANCELLATION_COMPLETED)
            .setRetryOptions(RetryOptions.newBuilder()
                    .setInitialInterval(Duration.ofSeconds(2))
                    .setMaximumInterval(Duration.ofSeconds(2))
                    .build())
            .build();
    private final CancelableActivities activity = Workflow
            .newActivityStub(CancelableActivities.class, options);

    private String cancellationRequest = null;
    @Override
    public void signalCancelPrimaryActivity(String reason){
        cancellationRequest = reason;
    }

    private boolean isActivityCompleted(AtomicReference<Promise<String>> atmPromise){
        return  Optional.of(atmPromise)
                .map(AtomicReference::get)
                .map(Promise::isCompleted).orElse(false);
    }
    private boolean isCancelRequested(){
        return Optional.ofNullable(cancellationRequest).isPresent();
    }

    @Override
    @Timed(value = "runWorkflow.timer")
    @Counted(value = "runWorkflow.counter")
    public String runPrimaryWorkflow(String name) {
        log.info("starting.Workflow:{}", name);
        var ret1 = activity.simpleTaskFunction1(name);
        final AtomicReference<Promise<String>> promiseLRA = new AtomicReference<>();
        CancellationScope scope =
                Workflow.newCancellationScope(
                        () -> promiseLRA.set(Async.function(activity::longRunningTaskFunction2, ret1)));
        scope.run();

        Workflow.await(()->isActivityCompleted(promiseLRA) || isCancelRequested());

        if(!isActivityCompleted(promiseLRA) && isCancelRequested()){
            /* Important, Sending .cancel() does not really wait for Activity Completion.
             * So you need to still wait or Promise to complete.
             * Refer : https://community.temporal.io/t/cancel-of-a-cancellationscope-not-working-as-expected/3885/5
             *
            */
            log.info("initiate Cancel On Activity:{} /* but is not really blocking. */", name);
            scope.cancel(Optional.ofNullable(cancellationRequest).orElse("none"));//send out cancellation.
        }

        var ret2 = Optional.ofNullable(promiseLRA.get())
                .map(Promise::get)
                .orElseThrow(()->new RuntimeException("Unexpected Promise should be done."));

        log.info("ending.Workflow:{} , returned Value: '{}' ", name,ret2);
        return ret2;
    }
}
