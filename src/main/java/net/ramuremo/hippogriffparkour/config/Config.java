package net.ramuremo.hippogriffparkour.config;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public final class Config extends ConfigFile {

    public Config(Plugin plugin) {
        super(plugin, "config.yml");

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public Location getStarPoint() {
        return getConfig().getLocation("start-location", null);
    }

    public void setStartPoint(Location location) {
        getConfig().set("start-location", location);
    }

    public List<Location> getCheckpoints() {
        return (List<Location>) getConfig().getList("checkpoints", new ArrayList<>());
    }

    public void setCheckpoints(List<Location> locations) {
        getConfig().set("checkpoints", locations);
    }

    public Location getGoalPoint() {
        return getConfig().getLocation("goal-location", null);
    }

    public void setGoalPoint(Location location) {
        getConfig().set("goal-location", location);
    }

    public Location getRankingLocation() {
        return getConfig().getLocation("ranking-location", null);
    }
}
