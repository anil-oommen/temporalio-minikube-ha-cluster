package com.oom.temporal.watch;

import com.uber.m3.tally.RootScopeBuilder;
import com.uber.m3.tally.Scope;
import com.uber.m3.util.ImmutableMap;
import io.micrometer.core.instrument.MeterRegistry;
import io.temporal.common.reporter.MicrometerClientStatsReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ObserverConfig {

    @Autowired
    Environment environment;
    @Bean
    public Function<String,Scope> createMeterScope(MeterRegistry meterRegistry){
        return (tioInstance)->{
            var profile = Arrays.stream(environment.getActiveProfiles())
                    .collect(Collectors.joining());
            var hostName = Optional.ofNullable(environment.getProperty("HOSTNAME")).orElse("notfound");
            return new RootScopeBuilder().tags(
                            ImmutableMap.of(
                                    "profile", profile,
                                    "hostname",hostName,
                                    "tio",tioInstance
                                    ))
                    .reporter(new MicrometerClientStatsReporter(meterRegistry))
                    .reportEvery(com.uber.m3.util.Duration.ofSeconds(5));
        };
    }

}
