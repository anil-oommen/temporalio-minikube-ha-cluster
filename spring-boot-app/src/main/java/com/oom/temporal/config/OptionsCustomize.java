package com.oom.temporal.config;

import io.temporal.client.WorkflowClientOptions;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.spring.boot.TemporalOptionsCustomizer;
import io.temporal.spring.boot.autoconfigure.properties.TemporalProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Nonnull;

@Configuration
public class OptionsCustomize {

    /**
     * https://github.com/temporalio/samples-java/blob/main/springboot/src/main/java/io/temporal/samples/springboot/customize/TemporalOptionsConfig.java
     */

    TemporalProperties properties;
    public  OptionsCustomize(TemporalProperties properties){
        this.properties = properties;
    }

    @Bean
    public TemporalOptionsCustomizer<WorkflowServiceStubsOptions.Builder>
    customServiceStubsOptions() {
        return new TemporalOptionsCustomizer<WorkflowServiceStubsOptions.Builder>() {
            @Nonnull
            @Override
            public WorkflowServiceStubsOptions.Builder customize(
                    @Nonnull WorkflowServiceStubsOptions.Builder optionsBuilder) {
                optionsBuilder.setTarget(properties.getConnection().getTarget());
                return optionsBuilder;
            }
        };
    }

    @Bean
    public TemporalOptionsCustomizer<WorkflowClientOptions.Builder> customClientOptions() {
        return new TemporalOptionsCustomizer<WorkflowClientOptions.Builder>() {
            @Nonnull
            @Override
            public WorkflowClientOptions.Builder customize(
                    @Nonnull WorkflowClientOptions.Builder optionsBuilder) {
                optionsBuilder.setNamespace(properties.getNamespace());
                return optionsBuilder;
            }
        };
    }

}
