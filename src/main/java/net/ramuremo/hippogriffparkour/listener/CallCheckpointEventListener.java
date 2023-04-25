package net.ramuremo.hippogriffparkour.listener;

import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourCheckpointEvent;
import net.ramuremo.hippogriffparkour.utility.MultipleRateLimiter;
import net.ramuremo.hippogriffparkour.utility.Util;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class CallCheckpointEventListener implements Listener {

    private final ParkourManager manager;
    private final MultipleRateLimiter<Player> rateLimiter;

    public CallCheckpointEventListener(ParkourManager manager) {
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
        if (!manager.isStarted(player)) return;
        if (!rateLimiter.tryAcquire(player)) return;

        final int checkpoint = manager.config.getCheckpoints().indexOf(block.getLocation());
        if (checkpoint == -1) return;

        event.setCancelled(true);

        final int currentCheckpoint = manager.getCheckpoint(player);
        if (currentCheckpoint + 1 != checkpoint) return;

        Util.callEvent(new ParkourCheckpointEvent(player, block, checkpoint));
    }
}
