package net.ramuremo.hippogriffparkour;

import net.ramuremo.hippogriffparkour.config.Config;
import org.bukkit.plugin.java.JavaPlugin;

public final class HippogriffParkourPlugin extends JavaPlugin {

    private ParkourManager parkourManager = null;

    @Override
    public void onEnable() {
        parkourManager = new ParkourManager(this, new Config(this));

        getLogger().info("The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("The plugin has been disabled.");
        parkourManager.rankingManager.remove();
    }
}
