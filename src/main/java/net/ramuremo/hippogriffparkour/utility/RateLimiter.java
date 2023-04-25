package net.ramuremo.hippogriffparkour.utility;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class RateLimiter {

    public final Plugin plugin;
    private long rate;
    private BukkitTask task = null;

    public RateLimiter(Plugin plugin, long rate) {
        this.plugin = plugin;
        this.rate = rate;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public boolean tryAcquire() {
        final boolean result = task == null || task.isCancelled();
        if (result) run();

        return result;
    }

    private void run() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                task.cancel();
            }
        }.runTaskLaterAsynchronously(plugin, rate);
    }
}
