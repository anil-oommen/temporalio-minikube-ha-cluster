package com.oom.temporal.service;

import com.oom.temporal.client.WorkflowControl;
import com.oom.temporal.config.TioConfig;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@Service
public class WorkflowConnect {

    @Autowired
    TioConfig tioConfig;

    @Autowired
    WorkflowControl workflowControl;

    @Timed(value = "launchCancellableWorkflow.timer")
    @Counted(value = "launchCancellableWorkflow.counter")
    public Mono<Map<String,String>> launchCancellableWorkflow(String tioInstance, int count) {
        return Mono.just(
                IntStream.range(0,count)
                        .mapToObj(i->String.format("%03d",i))
                        .map(tid->{
                            return Optional.ofNullable(tioConfig.getTemporal().get(tioInstance))
                                    .map(tio-> workflowControl.launchCancellableWorkflow(tid,tio))
                                    .orElseThrow(()->new RuntimeException(("Error Tio Instance not Found!")));
                        })
                        .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));

    }

    @Timed(value = "launchLoadedWorkflow.timer")
    @Counted(value = "launchLoadedWorkflow.counter")
    public Mono<Map<String,String>> launchLoadedWorkflow(String tioInstance, int count) {
        return Mono.just(
                IntStream.range(0,count)
                        .mapToObj(i->String.format("%03d",i))
                        .map(tid->{
                            return Optional.ofNullable(tioConfig.getTemporal().get(tioInstance))
                                    .map(tio-> workflowControl.launchLoadedWorkflow(tid,tio))
                                    .orElseThrow(()->new RuntimeException(("Error Tio Instance not Found, or could not launch any workflows.")));
                        })
                        .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));

    }

    public Mono<String> cancelActivity(String tioInstance,String workflowId, String runId,String cancelReason) {
        return Mono.just(workflowControl.cancelActivity(tioConfig.getTemporal().get(tioInstance)
                ,workflowId,runId,cancelReason));
    }
}
