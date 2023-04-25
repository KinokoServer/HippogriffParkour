package net.ramuremo.hippogriffparkour;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.ramuremo.hippogriffparkour.listener.RankingUpdateListener;
import net.ramuremo.hippogriffparkour.utility.Util;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.*;

public final class RankingManager {

    public final ParkourManager parkourManager;
    private final List<ArmorStand> lines = new ArrayList<>();

    public RankingManager(ParkourManager parkourManager) {
        this.parkourManager = parkourManager;
        initialize();
        spawn();
    }

    public List<ArmorStand> getLines() {
        return List.copyOf(lines);
    }

    public void initialize() {
        Util.registerListener(
                parkourManager.plugin,
                new RankingUpdateListener(this)
        );
    }

    public void spawn() {
        remove();


        final Location location = parkourManager.config.getRankingLocation();
        final TreeMap<Double, OfflinePlayer> sortedTimes = getSorted();

        int i = 0;
        spawnTitleLine(location.clone().add(0, 0.2, 0));

        for (Map.Entry<Double, OfflinePlayer> entry : sortedTimes.entrySet()) {
            i++;

            final OfflinePlayer player = entry.getValue();
            final double time = entry.getKey() / 20;
            spawnLine(location.clone().add(0, -(0.2 * i), 0), Component.text((i) + "位: " + player.getName() + " - " + time + "秒").color(TextColor.color(0xFCAAD9)));

            if (i == 6) return;
        }
    }

    public void remove() {
        lines.forEach(Entity::remove);
        lines.clear();
    }

    public TreeMap<Double, OfflinePlayer> getSorted() {
        final Map<OfflinePlayer, Double> times = parkourManager.playerDataManager.getTimes();

        final Comparator<Double> comparator = Double::compareTo;
        final TreeMap<Double, OfflinePlayer> sortedTimes = new TreeMap<>(comparator);
        times.forEach((key, value) -> sortedTimes.put(value, key));

        return sortedTimes;
    }

    private void spawnLine(Location location, Component message) {
        final ArmorStand line = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        line.customName(message);
        line.setCustomNameVisible(true);
        line.setVisible(false);
        line.setGravity(false);
        line.setSmall(true);
        line.setMarker(true);
        line.setBasePlate(false);

        lines.add(line);
    }

    private void spawnTitleLine(Location location) {
        final ArmorStand line = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        line.customName(
                Component.text("[").color(TextColor.color(0xFFFFFF))
                        .append(Component.text("ランキング").color(TextColor.color(0xA193FF)))
                        .append(Component.text("]").color(TextColor.color(0xFFFFFF)))
                        .decorate(TextDecoration.BOLD)
        );
        line.setCustomNameVisible(true);
        line.setVisible(false);
        line.setGravity(false);
        line.setSmall(true);
        line.setMarker(true);
        line.setBasePlate(false);

        lines.add(line);
    }
}
