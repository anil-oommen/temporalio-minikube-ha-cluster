package com.oom.temporal.baremin.activties;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface SimpleActivities {
    String taskFunction1(String name);
    String taskFunction2(String name);
}
