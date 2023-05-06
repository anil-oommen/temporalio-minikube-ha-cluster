package com.oom.temporal.service;

import com.oom.temporal.baremin.SimpleWorkflowControl;
import com.oom.temporal.config.TioConfig;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class WorkflowConnect {

    @Autowired
    TioConfig tioConfig;

    @Autowired
    SimpleWorkflowControl simpleWorkflowControl;

    @Timed(value = "launchWorkflow.timer")
    @Counted(value = "launchWorkflow.counter")
    public Mono<Map<String,String>> launchWorkflow(String tioInstance, int count) {
        return Mono.just(
                IntStream.range(0,count)
                        .mapToObj(i->String.format("%03d",i))
                        .map(tid->{
                            return Optional.ofNullable(tioConfig.getTemporal().get(tioInstance))
                                    .map(tio->simpleWorkflowControl.launchWorkflow(tid,tio))
                                    .orElseThrow(()->new RuntimeException(("Error Tio Instance not Found!")));
                        })
                        .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));

    }
    public Mono<String> cancelActivity(String tioInstance,String workflowId, String runId,String cancelReason) {
        return Mono.just(simpleWorkflowControl.cancelActivity(tioConfig.getTemporal().get(tioInstance)
                ,workflowId,runId,cancelReason));
    }
}
