package me.mgmgmg.perishdropsfilter;

import me.mgmgmg.perishdropsfilter.listeners.PerishDropEvent;
import me.mgmgmg.perishdropsfilter.listeners.PerishKeepEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class ConfigManager {
    private static final Set<Material> whitelist = new HashSet<>();

    /**
     * Initializes the entire plugin
     */
    protected static void init(JavaPlugin plugin) {
        // Loops through the configured materials
        for(String string : plugin.getConfig().getStringList("whitelisted")) {
            try {
                // Tries to add each material
                whitelist.add(Material.valueOf(string.toUpperCase()));
            } catch (Exception ignored) {
                // In case it fails, it logs an error.
                Bukkit.getLogger().severe("There is no such material as \"" + string + "\". You should check your config.yml!");
            }
        }

        // Registers the correct PerishEvent according to the configuration
        plugin.getServer().getPluginManager().registerEvents(
                plugin.getConfig().getBoolean("keep-inventory") ? new PerishKeepEvent() : new PerishDropEvent(),
                plugin
        );
    }

    /**
     * Returns a set of whitelisted materials
     */
    public static Set<Material> getWhitelist() {
        return whitelist;
    }
}
