package net.ramuremo.hippogriffparkour.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.ramuremo.hippogriffparkour.ParkourManager;
import net.ramuremo.hippogriffparkour.event.ParkourCheckpointTeleportEvent;
import net.ramuremo.hippogriffparkour.utility.Util;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

public final class CheckpointCommand extends Command {

    private final ParkourManager manager;

    public CheckpointCommand(ParkourManager manager) {
        super("checkpoint");
        this.manager = manager;
    }

    @Override
    public boolean execute(@Nonnull CommandSender sender, @Nonnull String s, @Nonnull String[] args) {
        if (!(sender instanceof Player player)) return false;

        final int checkpoint = manager.getCheckpoint(player);

        if (checkpoint == -1) {
            warnNoCheckpoint(player);
            return false;
        }

        final Location location;
        try {
            location = manager.config.getCheckpoints().get(checkpoint);
        } catch (Exception e) {
            e.printStackTrace();
            warnAnErrorOccurred(player);
            return false;
        }

        final ParkourCheckpointTeleportEvent event = new ParkourCheckpointTeleportEvent(player, checkpoint);
        Util.callEvent(event);
        if (event.isCancelled()) {
            warnCancelled(player);
            return true;
        }
        player.teleportAsync(location);

        return true;
    }

    private void warnNoCheckpoint(Player player) {
        player.sendMessage(
                Component.text("通過したチェックポイントはありません!")
                        .color(TextColor.color(0xFF0015))
        );
        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_AMBIENT, 1, 1);
    }

    private void warnCancelled(Player player) {
        player.sendMessage(
                Component.text("操作がキャンセルされました。")
                        .color(TextColor.color(0xFF0015))
        );
        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_AMBIENT, 1, 1);
    }

    private void warnAnErrorOccurred(Player player) {
        player.sendMessage(
                Component.text("エラーが発生しました! 管理者にお問い合わせください。")
                        .color(TextColor.color(0xFF0015))
        );
        player.playSound(player.getLocation(), Sound.ENTITY_WOLF_HOWL, 1, 1);
    }
}
