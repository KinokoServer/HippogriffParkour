package net.ramuremo.hippogriffparkour.listener;

import net.ramuremo.hippogriffparkour.RankingManager;
import net.ramuremo.hippogriffparkour.event.ParkourGoalEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public final class RankingUpdateListener implements Listener {

    private final RankingManager manager;

    public RankingUpdateListener(RankingManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onParkourGoal(ParkourGoalEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                manager.spawn();
            }
        }.runTaskLater(manager.parkourManager.plugin, 20);
    }
}
