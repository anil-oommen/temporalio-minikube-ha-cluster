package com.oom.temporal.workers.workflow;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface CancelableWorkflow {
    @WorkflowMethod
    String runPrimaryWorkflow(String name);

    @SignalMethod
    void signalCancelPrimaryActivity(String reason);
}
