/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:03
 */

package fr.strow.api.utils;

import java.util.concurrent.TimeUnit;

public interface Scheduler {

    /**
     * Run a task asynchronously
     *
     * @param task task to run
     */
    void runTaskAsynchronously(Runnable task);

    /**
     * Run a task later
     *
     * @param task task to run later
     * @param delay    delay before running the task
     * @param timeUnit time unit of the timer
     */
    void runTaskLater(Runnable task, long delay, TimeUnit timeUnit);

    /**
     * Run a task with a period
     *
     * @param task task to run with a period
     * @param delay    delay before starting the task
     * @param period   laps of time before running the task another time
     * @param timeUnit time unit of the timer
     */
    void runRepeatingTask(Runnable task, long delay, long period, TimeUnit timeUnit);
}
