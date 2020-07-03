/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:06
 */

package fr.strow.core.api.utils;

import com.google.inject.Inject;
import fr.strow.api.utils.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.concurrent.TimeUnit;

public class SchedulerImpl implements Scheduler {

    private final JavaPlugin plugin;
    private final BukkitScheduler scheduler;

    @Inject
    public SchedulerImpl(JavaPlugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    @Override
    public void runTaskAsynchronously(Runnable task) {
        scheduler.runTaskAsynchronously(plugin, task);
    }

    @Override
    public void runTaskLater(Runnable task, long delay, TimeUnit timeUnit) {
        scheduler.runTaskLater(plugin, task, toTicks(delay, timeUnit));
    }

    @Override
    public void runRepeatingTask(Runnable task, long delay, long period, TimeUnit timeUnit) {
        scheduler.runTaskTimer(plugin, task, toTicks(delay, timeUnit), toTicks(period, timeUnit));
    }

    private long toTicks(long time, TimeUnit timeUnit) {
        return timeUnit.toSeconds(time) * 20;
    }
}
