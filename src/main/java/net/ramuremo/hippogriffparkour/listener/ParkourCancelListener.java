package net.ramuremo.hippogriffparkour.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.ramuremo.hippogriffparkour.CancelCause;
import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourCancelEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class ParkourCancelListener implements Listener {

    private final ParkourManager manager;

    public ParkourCancelListener(ParkourManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onParkourCancel(ParkourCancelEvent event) {
        final Player player = event.getPlayer();
        final CancelCause cause = event.getCause();
        final String message = cause.getMessage();

        manager.removeTime(player);
        manager.removeCheckpoint(player);

        player.sendMessage(
                Component.text("パルクールがキャンセルされました。 原因: " + message)
                        .color(TextColor.color(0xFF0015))
        );
        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_HOWL, 1, 1);
    }
}