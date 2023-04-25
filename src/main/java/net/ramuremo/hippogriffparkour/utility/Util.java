package net.ramuremo.hippogriffparkour.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public final class Util {

    public static Location toCenterLocation(Location location) {
        final Location loc = location.clone();
        final Location block = loc.getBlock().getLocation();

        loc.set(block.x() + 0.5 , block.y(), block.y() + 0.5);

        return location;
    }

    public static <T> T getRandom(List<T> collection) {
        final int i = new SecureRandom().nextInt(collection.size());

        return collection.get(i);
    }

    public static void requireAsPlayer(CommandSender sender) {
        sender.sendMessage(
                Component.text("そのアクションにはプレイヤーとして実行する必要があります!")
                        .color(TextColor.color(0xFF0015))
        );
    }

    public static void callEvent(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }

    public static void registerListener(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        }
    }

    public static void hideEntity(Plugin plugin, Entity entity, Player... ignorePlayers) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Arrays.asList(ignorePlayers).contains(player)) continue;
            player.hideEntity(plugin, entity);
        }
    }

    public static void hidePlayers(Plugin plugin) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Bukkit.getOnlinePlayers().forEach(p -> player.hidePlayer(plugin, p));
        }
    }

    public static void registerCommand(Command... commands) {
        for (Command command : commands) {
            Bukkit.getCommandMap().register("hippogrifparkour", command);
        }
    }
}
