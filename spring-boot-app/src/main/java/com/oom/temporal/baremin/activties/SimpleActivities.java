package com.oom.temporal.baremin.activties;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SimpleActivities {
    String simpleTaskFunction1(String name);
    String longRunningTaskFunction2(String name);
}
