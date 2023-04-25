package net.ramuremo.hippogriffparkour.listener;

import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourStartEvent;
import net.ramuremo.hippogriffparkour.utility.MultipleRateLimiter;
import net.ramuremo.hippogriffparkour.utility.Util;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public final class CallParkourStartEventListener implements Listener {

    private final ParkourManager manager;
    private final MultipleRateLimiter<Player> rateLimiter;

    public CallParkourStartEventListener(ParkourManager manager) {
        this.manager = manager;
        rateLimiter = new MultipleRateLimiter<>(manager.plugin, 10);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Action action = event.getAction();
        final Player player = event.getPlayer();
        final Block block = event.getClickedBlock();

        if (!action.equals(Action.PHYSICAL)) return;
        if (block == null) return;

        event.setCancelled(true);

        if (!rateLimiter.tryAcquire(player)) return;

        if (!manager.config.getStarPoint().equals(block.getLocation())) return;

        final ParkourStartEvent startEvent = new ParkourStartEvent(player, block);

        Util.callEvent(startEvent);
    }
}
