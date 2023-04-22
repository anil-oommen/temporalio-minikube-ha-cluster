package com.oom.temporal.service;

import com.oom.temporal.baremin.SimpleWorkflowControl;
import com.oom.temporal.config.TioConfig;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.List;
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
    public Mono<List<String>> launchWorkflow(String tioInstance,int count) {
        return Mono.just(
                IntStream.range(0,count)
                        .mapToObj(i->String.format("%03d",i))
                        .map(tid->{
                            return Optional.ofNullable(tioConfig.getTemporal().get(tioInstance))
                                    .map(tio->simpleWorkflowControl.launchFromStub(tid,tio))
                                    .orElseThrow(()->new RuntimeException(("Error Tio Instance not Found!")));
                        })
                        .collect(Collectors.toList()));

    }
}
