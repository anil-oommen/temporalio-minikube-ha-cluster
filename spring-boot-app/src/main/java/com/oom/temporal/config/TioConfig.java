package com.oom.temporal.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.config")
public class TioConfig {

    /*TioInstance tio1;
    TioInstance tio2;*/
    public static final String INSTANCE_TIO1 = "tio1";
    public static final String INSTANCE_TIO2 = "tio2";

    HashMap<String,TioInstance> temporal = new HashMap<>();
}
