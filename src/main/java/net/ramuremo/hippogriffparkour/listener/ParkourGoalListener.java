package net.ramuremo.hippogriffparkour.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourGoalEvent;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.Collections;
import java.util.Map;

public final class ParkourGoalListener implements Listener {

    private final ParkourManager manager;

    public ParkourGoalListener(ParkourManager manager) {
        this.manager = manager;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onParkourGoal(ParkourGoalEvent event) {
        final Player player = event.getPlayer();
        final double time = manager.getTime(player);
        final Map<OfflinePlayer, Double> times = manager.playerDataManager.getTimes();
        final double ownRecord = times.getOrDefault(player, Double.NaN);

        final double maxTime = times.isEmpty() ? 0 : Collections.max(times.values());
        final boolean isOwnRecord = Double.isNaN(ownRecord) || times.get(player) > time;
        final boolean record = maxTime > time;

        if (isOwnRecord) {
            times.put(player, time);
            manager.playerDataManager.save(times);
        }

        manager.removeTime(player);
        manager.removeCheckpoint(player);

        sendGoalMessage(player, time);
        if (record) sendRecordMessage(player);
    }

    private void sendGoalMessage(Player player, double time) {
        player.sendMessage(
                Component.text("パルクールをゴールしました! 記録は" + time / 20 + "秒です!")
                        .color(TextColor.color(0xFCAAD9))
        );
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_BURP, 1, 0);
    }

    private void sendRecordMessage(Player player) {
        player.sendMessage(
                Component.text("サーバー記録を更新しました!! おめでとうございます!!")
                        .color(TextColor.color(0xFEFF00))
        );
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
    }
}
