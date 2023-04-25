package net.ramuremo.hippogriffparkour.listener;

import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourGoalEvent;
import net.ramuremo.hippogriffparkour.utility.MultipleRateLimiter;
import net.ramuremo.hippogriffparkour.utility.Util;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class CallParkourGoalEventListener implements Listener {

    private final ParkourManager manager;
    private final MultipleRateLimiter<Player> rateLimiter;

    public CallParkourGoalEventListener(ParkourManager manager) {
        this.manager = manager;
        this.rateLimiter = new MultipleRateLimiter<>(manager.plugin, 10);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Action action = event.getAction();
        if (!action.equals(Action.PHYSICAL)) return;

        final Block block = event.getClickedBlock();
        if (block == null) return;

        final Player player = event.getPlayer();
        if (!rateLimiter.tryAcquire(player)) return;

        if (!manager.isStarted(player)) return;
        if (!manager.config.getGoalPoint().equals(block.getLocation())) return;
        if (manager.getCheckpoint(player) != manager.config.getCheckpoints().size() - 1) return;

        event.setCancelled(true);

        Util.callEvent(new ParkourGoalEvent(player, block));
    }
}
