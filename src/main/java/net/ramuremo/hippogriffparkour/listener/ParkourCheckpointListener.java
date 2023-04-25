package net.ramuremo.hippogriffparkour.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourCheckpointEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class ParkourCheckpointListener implements Listener {

    private final ParkourManager manager;

    public ParkourCheckpointListener(ParkourManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onParkourCheckpoint(ParkourCheckpointEvent event) {
        final Player player = event.getPlayer();
        final int checkpoint = event.getCheckpoint();

        manager.setCheckpoint(player, checkpoint);

        player.sendMessage(
                Component.text("チェックポイント " + checkpoint + "を通過しました! /checkpoint で戻れます!")
                        .color(TextColor.color(0xFEFF00))
        );
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 0);
    }
}
