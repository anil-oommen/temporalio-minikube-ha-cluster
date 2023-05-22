package com.oom.temporal.workers.activties;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.temporal.activity.Activity;
import io.temporal.client.ActivityCanceledException;
import io.temporal.client.ActivityNotExistsException;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class LoadedActivitiesImpl implements LoadedActivities {
    @Override
    public String tasFunction01(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction02(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction03(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction04(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction05(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction06(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction07(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction08(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction09(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }

    @Override
    public String tasFunction10(String name) {
        try {Thread.sleep(500);} catch (InterruptedException ignored) {}
        return null;
    }
}
