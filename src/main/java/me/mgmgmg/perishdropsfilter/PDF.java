package me.mgmgmg.perishdropsfilter;

import org.bukkit.plugin.java.JavaPlugin;

public final class PDF extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin initialisation
        saveDefaultConfig();
        ConfigManager.init(this);

        // DEBUG
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // DEBUG
        getLogger().info("Plugin enabled!");
    }
}
