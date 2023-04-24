package com.oom.temporal.baremin;

import com.oom.temporal.config.TioConfig;
import com.oom.temporal.baremin.activties.SimpleActivitiesImpl;
import com.oom.temporal.baremin.workflow.SimpleWorkflow;
import com.oom.temporal.baremin.workflow.SimpleWorkflowImpl;
import com.oom.temporal.config.TioInstance;
import com.uber.m3.tally.RootScopeBuilder;
import com.uber.m3.tally.Scope;
import com.uber.m3.util.ImmutableMap;
import io.micrometer.core.instrument.MeterRegistry;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowClientOptions;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.common.reporter.MicrometerClientStatsReporter;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Component
@Slf4j
public class SimpleWorkflowControl {

    @Autowired
    TioConfig tioConf;

    @Autowired
    Function<String,Scope> funScope;

    @PostConstruct
    void startAllWorkers(){
        tioConf.getTemporal().values()
                        .forEach(this::startWorker);
    }
    void startWorker(TioInstance tioInstance){

        // Get a Workflow service stub.
        WorkflowServiceStubs service =
                WorkflowServiceStubs.newInstance(
                        WorkflowServiceStubsOptions.newBuilder()
                                .setMetricsScope(funScope.apply(tioInstance.getName()))
                                .setTarget(tioInstance.getTarget())
                                .build());;

        //WorkflowServiceStubs.newLocalServiceStubs();

        /*
         * Get a Workflow service client which can be used to start, Signal, and Query Workflow Executions.
         */
        WorkflowClientOptions clientOptions = WorkflowClientOptions.newBuilder()
                .setNamespace(tioInstance.getNamespace()).build();

        WorkflowClient client = WorkflowClient.newInstance(service,clientOptions);

        /*
         * Define the workflow factory. It is used to create workflow workers that poll specific Task Queues.
         */
        WorkerFactory factory = WorkerFactory.newInstance(client);

        /*
         * Define the workflow worker. Workflow workers listen to a defined task queue and process
         * workflows and activities.
         */
        Worker worker = factory.newWorker(tioInstance.getTaskQueue());

        /*
         * Register our workflow implementation with the worker.
         * Workflow implementations must be known to the worker at runtime in
         * order to dispatch workflow tasks.
         */
        worker.registerWorkflowImplementationTypes(SimpleWorkflowImpl.class);

        /*
         * Register our Activity Types with the Worker. Since Activities are stateless and thread-safe,
         * the Activity Type is a shared instance.
         */
        worker.registerActivitiesImplementations(new SimpleActivitiesImpl());

        /*
         * Start all the workers registered for a specific task queue.
         * The started workers then start polling for workflows and activities.
         */
        factory.start();
        log.info("Worker Started: {}" , tioInstance.toString());
    }

    public String launchFromStub(String inputId,TioInstance tioInstance){
        WorkflowServiceStubs service = WorkflowServiceStubs.newInstance(
                WorkflowServiceStubsOptions.newBuilder()
                        .setMetricsScope(funScope.apply(tioInstance.getName()))
                        .setTarget(tioInstance.getTarget())
                        .build());

        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        WorkflowClientOptions clientOptions = WorkflowClientOptions.newBuilder()
                .setNamespace(tioInstance.getNamespace()).build();
        WorkflowClient client = WorkflowClient.newInstance(service,clientOptions);

        /*
         * Set Workflow options such as WorkflowId and Task Queue so the worker knows where to list and which workflows to execute.
         */

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setWorkflowId(String.format("SWF%s",inputId))
                .setTaskQueue(tioInstance.getTaskQueue())
                .setSearchAttributes(addSearchAttributes(inputId))
                .build();

        // Create the workflow client stub. It is used to start our workflow execution.
        SimpleWorkflow workflow = client.newWorkflowStub(SimpleWorkflow.class, options);

        CompletableFuture<String> futureResult = WorkflowClient.execute(workflow::start, inputId);

        return WorkflowStub.fromTyped(workflow).getExecution().getRunId();
    }

    private Map<String, String> addSearchAttributes(String appReference) {
        Map<String, String> searchAttributes = new HashMap<>();
        searchAttributes.put(Shared.SEARCH_ATTRIBUTE_APP_REF,String.format("APPREF%s",appReference));
        return searchAttributes;
    }
}
