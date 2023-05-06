package com.oom.temporal.web;


import com.oom.temporal.baremin.SimpleWorkflowControl;
import com.oom.temporal.config.TioConfig;
import com.oom.temporal.service.WorkflowConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    String responseContentTemplate = """
            <html><body>%s</body></html>
            """;

    @GetMapping("/launch/{tio}/{count}")
    public Mono<ResponseEntity<String>> launch(@PathVariable("tio") String tioInstance,
                                     @PathVariable("count") int count) {
        return workflowConnect.launchWorkflow(tioInstance,count)
                .map(l->
                    l.entrySet().stream().
                    map((emap)->
                            String.format("%s :: %s &nbsp;<a href='/temporal/cancel-activity/%s/%s?runid=%s&reason=user_cancel' " +
                                    "target='_blank'>cancel activity</a>",
                                    emap.getKey(),emap.getValue(),
                                    tioInstance,emap.getKey(),emap.getValue()))
                        .collect(Collectors.joining("<br>")))
                .map(content->
                    ResponseEntity.accepted().contentType(MediaType.TEXT_HTML)
                        .body(String.format(responseContentTemplate,content)));



    }

    @GetMapping("/cancel-activity/{tio}/{workflowid}")
    public Mono<String> cancelActivity(@PathVariable("tio") String tioInstance,
                                               @PathVariable("workflowid") String workflowid,
                                               @RequestParam("runid") String runid,
                                       @RequestParam(name = "reason")  String reason
                                               ) {
        return workflowConnect.cancelActivity(tioInstance, workflowid,runid,reason);
    }

}
