package com.oom.temporal.workers.activties;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.temporal.activity.Activity;
import io.temporal.client.ActivityCanceledException;
import io.temporal.client.ActivityNotExistsException;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class CancelableActivitiesImpl implements CancelableActivities {
    @Override
    @Timed(value = "simpleTaskFunction1.timer")
    @Counted(value = "simpleTaskFunction1.counter")
    public String simpleTaskFunction1(String name) {
        return name+">simpleTaskFunction1";
    }

    @Override
    @Timed(value = "longRunningTaskFunction2.timer")
    @Counted(value = "longRunningTaskFunction2.counter")
    public String longRunningTaskFunction2(String name) {
        String runTrace = String.format(" [%3d]  ", new Random().nextInt(1, 999));

        int startPoint =
                Activity.getExecutionContext()
                        .getHeartbeatDetails(Integer.class)
                        .orElse(0);

        log.info("{} START from :{}",runTrace, startPoint);
        var bhSecs = 3;
        boolean abortOnCancelOrHeartBeatFail = false;
        for (int i = startPoint; i <= 50; i++) {
            try {
                log.info("{} Active & Sending HeartBeat: {} (every {} sec)",runTrace, i,bhSecs);
                Activity.getExecutionContext().heartbeat(i);
            }catch (ActivityNotExistsException activityNotExistsException){
                log.error("{} **** ActivityNotExistsException on Heartbeat {}",runTrace,i);
                //log.error(">>", activityNotExistsException);
                abortOnCancelOrHeartBeatFail= simulateCleanupProcessingAfterHeartBeatFailure(runTrace);
                break;
            }catch (ActivityCanceledException activityCanceledException){
                log.error("{} **** ActivityCanceledException on Heartbeat {}",runTrace,i);
                //log.error(">>", activityCanceledException);
                abortOnCancelOrHeartBeatFail= simulateCleanupProcessingAfterHeartBeatFailure(runTrace);
                break;
            }

            try {
                Thread.sleep(bhSecs*1000);
            } catch (InterruptedException ignored) { }
        }

        log.info("{} FINIS isAbortOnCancelOrHeartBeatFail:{}",runTrace, abortOnCancelOrHeartBeatFail);
        return name+">longRunningTaskFunction2 , wasAborted/Cancelled?="
                + abortOnCancelOrHeartBeatFail;
    }

    boolean simulateCleanupProcessingAfterHeartBeatFailure(String runTrace){
        var bhSecs =2;
        for (int j = 0; j <= 10; j++){
            log.info("{} Canceled But Still working to Finish:{} (every {} sec)",runTrace, j,bhSecs);
            try{Thread.sleep(1000*bhSecs);}catch (InterruptedException ignored){};
        }
        return true;
    }
}
