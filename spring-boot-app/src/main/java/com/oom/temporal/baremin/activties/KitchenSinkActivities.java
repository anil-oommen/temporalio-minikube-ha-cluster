package com.oom.temporal.baremin.activties;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface KitchenSinkActivities {
    String doTask(String name);
}
