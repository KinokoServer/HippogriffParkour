package net.ramuremo.hippogriffparkour.listener;

import net.ramuremo.hippogriffparkour.CancelCause;
import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourCancelEvent;
import net.ramuremo.hippogriffparkour.utility.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

public final class CallParkourCancelEventListener implements Listener {

    private final ParkourManager manager;

    public CallParkourCancelEventListener(ParkourManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        if (!manager.isStarted(player)) return;

        Util.callEvent(new ParkourCancelEvent(player, CancelCause.QUIT));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerToggleFlight(PlayerToggleFlightEvent event) {
        final Player player = event.getPlayer();

        if (!manager.isStarted(player)) return;

        Util.callEvent(new ParkourCancelEvent(player, CancelCause.FLIGHT));
    }
}
