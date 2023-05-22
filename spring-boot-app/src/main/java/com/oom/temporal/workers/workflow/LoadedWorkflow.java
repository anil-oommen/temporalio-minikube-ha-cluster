package com.oom.temporal.workers.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface LoadedWorkflow {
    @WorkflowMethod
    String runLoadedWorkflow(String name);

}
