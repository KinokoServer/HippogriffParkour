package net.ramuremo.hippogriffparkour;

import net.ramuremo.hippogriffparkour.command.CheckpointCommand;
import net.ramuremo.hippogriffparkour.config.Config;
import net.ramuremo.hippogriffparkour.listener.*;
import net.ramuremo.hippogriffparkour.utility.Util;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public final class ParkourManager {

    public final Plugin plugin;
    public final Config config;
    public final PlayerDataManager playerDataManager;
    public final RankingManager rankingManager;
    private final Map<Player, Long> times = new HashMap<>();
    private final Map<Player, Integer> checkpoints = new HashMap<>();

    ParkourManager(Plugin plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
        this.playerDataManager = new PlayerDataManager(this);
        this.rankingManager = new RankingManager(this);

        initialize();
    }

    private void initialize() {
        registerListeners();
        registerCommands();
        runTimer();
    }

    private void registerListeners() {
        Util.registerListener(
                plugin,
                new CallCheckpointEventListener(this),
                new CallParkourCancelEventListener(this),
                new CallParkourGoalEventListener(this),
                new CallParkourStartEventListener(this),
                new ParkourCancelListener(this),
                new ParkourCheckpointListener(this),
                new ParkourGoalListener(this),
                new ParkourStartListener(this)
        );
    }

    private void registerCommands() {
        Util.registerCommand(
                new CheckpointCommand(this)
        );
    }

    private void runTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                addTime();
            }
        }.runTaskTimerAsynchronously(plugin, 1, 1);
    }

    private void addTime() {
        times.keySet().forEach(player -> setTime(player, getTime(player) + 1));
    }

    public boolean isStarted(Player player) {
        return times.containsKey(player);
    }

    public long getTime(Player player) {
        return times.getOrDefault(player, 0L);
    }

    public void setTime(Player player, long time) {
        times.put(player, time);
    }

    public void removeTime(Player player) {
        times.remove(player);
    }

    public int getCheckpoint(Player player) {
        return checkpoints.getOrDefault(player, -1);
    }

    public void setCheckpoint(Player player, int i) {
        checkpoints.put(player, i);
    }

    public void removeCheckpoint(Player player) {
        checkpoints.remove(player);
    }
}
