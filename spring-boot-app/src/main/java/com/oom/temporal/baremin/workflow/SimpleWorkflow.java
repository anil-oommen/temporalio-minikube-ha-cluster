package com.oom.temporal.baremin.workflow;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SimpleWorkflow {
    @WorkflowMethod
    String runWorkflow(String name);

    @SignalMethod
    void signalCancelActivity(String reason);
}
