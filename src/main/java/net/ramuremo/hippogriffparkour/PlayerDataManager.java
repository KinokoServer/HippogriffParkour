package net.ramuremo.hippogriffparkour;

import net.ramuremo.hippogriffparkour.config.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class PlayerDataManager {

    public final ParkourManager manager;
    private final ConfigFile data;

    PlayerDataManager(ParkourManager manager) {
        this.manager = manager;
        data = new ConfigFile(manager.plugin, "player-data.yml");
        initialize();
    }

    private void initialize() {
        data.saveDefaultConfig();
        try {
            manager.plugin.getLogger().info("Saved players: " + getTimes().size());
        } catch (Exception e) {
            manager.plugin.getLogger().warning("An error occurred while loading player-data.yml. Is it in the correct format?");
            e.printStackTrace();
        }
    }

    public Map<OfflinePlayer, Double> getTimes() {
        final ConfigurationSection timesSection = data.getConfig().getConfigurationSection("times");
        if (timesSection == null) return new HashMap<>();

        final Set<String> keys = timesSection.getKeys(false);
        final Map<OfflinePlayer, Double> times = new HashMap<>();

        keys.forEach(key -> times.put(Bukkit.getOfflinePlayer(UUID.fromString(key)), data.getConfig().getDouble("times." + key, 0.0)));

        return times;
    }

    public void save(Map<OfflinePlayer, Double> times) {
        data.getConfig().set("times", null);
        times.forEach((key, value) -> data.getConfig().set("times." + key.getUniqueId(), value));
        data.saveConfig();
    }
}
