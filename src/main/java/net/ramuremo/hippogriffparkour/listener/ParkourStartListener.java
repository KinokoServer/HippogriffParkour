package net.ramuremo.hippogriffparkour.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourStartEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public final class ParkourStartListener implements Listener {

    private final ParkourManager manager;

    public ParkourStartListener(ParkourManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onStart(ParkourStartEvent event) {
        final Player player = event.getPlayer();
        final boolean started = manager.isStarted(player);

        manager.removeCheckpoint(player);
        manager.removeTime(player);
        manager.setTime(player, 0);

        if (started) {
            sendRestartedMessage(player);
        } else {
            sendStartMessage(player);
        }
    }

    private void sendStartMessage(Player player) {
        player.sendMessage(
                Component.text("計測がスタートしました! 記録更新を目指しましょう!")
                        .color(TextColor.color(0xFCAAD9))
        );
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 0);
    }

    private void sendRestartedMessage(Player player) {
        player.sendMessage(
                Component.text("計測がリセットされました! 記録更新を目指しましょう!")
                        .color(TextColor.color(0xFCAAD9))
        );
        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 0);
    }
}
