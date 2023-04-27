package net.ramuremo.hippogriffparkour.utility;

import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class MultipleRateLimiter<T> {

    public final Plugin plugin;
    private final Set<T> limited = new HashSet<>();
    private long rate;

    public MultipleRateLimiter(Plugin plugin, long rate) {
        this.plugin = plugin;
        this.rate = rate;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public Set<T> getLimited() {
        return limited;
    }

    public boolean tryAcquire(T v) {
        if (limited.contains(v)) return false;
        limited.add(v);
        new BukkitRunnable() {
            @Override
            public void run() {
                limited.remove(v);
            }
        }.runTaskLaterAsynchronously(plugin, rate);
        return true;
    }
}
