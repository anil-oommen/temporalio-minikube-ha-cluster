package com.oom.temporal.workers.activties;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface CancelableActivities {
    String simpleTaskFunction1(String name);
    String longRunningTaskFunction2(String name);
}
