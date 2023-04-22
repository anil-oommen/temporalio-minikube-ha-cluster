package com.oom.temporal.web;


import com.oom.temporal.baremin.SimpleWorkflowControl;
import com.oom.temporal.config.TioConfig;
import com.oom.temporal.service.WorkflowConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/temporal")

public class LaunchController {

    @Autowired
    WorkflowConnect workflowConnect;
    @GetMapping("/launch/{tio}/{count}")
    public Mono<List<String>> launch(@PathVariable("tio") String tioInstance,
                                     @PathVariable("count") int count) {
        return workflowConnect.launchWorkflow(tioInstance,count);

    }

}
