package com.oom.temporal.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TioInstance {
    String name;
    String target;
    String namespace;
    String taskQueue;
    boolean useSearchAttribute;
}
