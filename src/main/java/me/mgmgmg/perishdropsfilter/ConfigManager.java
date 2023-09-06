package me.mgmgmg.perishdropsfilter;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ConfigManager {
    private static final HashMap<String, WorldSettings> worldSettings = new HashMap<>();

    /**
     * Initializes the entire plugin
     */
    protected static void init(JavaPlugin plugin) {
        for(String string : plugin.getConfig().getConfigurationSection("worlds").getKeys(false)) {
            worldSettings.put(
                    string,
                    new WorldSettings(plugin.getConfig().getConfigurationSection("worlds." + string))
            );
        }
    }

    public static HashMap<String, WorldSettings> getWorldSettings() {
        return worldSettings;
    }

    public static class WorldSettings {
        private final Set<Material> materials = new HashSet<>();
        private final boolean mode; // true = whitelist, false = blacklist
        private final boolean keepInventory;
        private final boolean voidNonValid;

        public WorldSettings(ConfigurationSection section) {
            // mode
            switch (section.getString("mode").toLowerCase()) {
                case "blacklist":
                    mode = false;
                    break;
                default:
                    Bukkit.getLogger().severe("\"" + section.getCurrentPath() +
                            ".mode\" was not setup correctly. Defaulting to whitelist.");
                case "whitelist":
                    mode = true;

            }

            // keep-inventory
            keepInventory = section.getBoolean("keep-inventory");

            // void-non-valid
            voidNonValid = section.getBoolean("void-non-valid");

            // items
            for (String string : section.getStringList("items")) {
                string = string.toUpperCase();
                try {
                    Material material = Material.valueOf(string);
                    if (materials.contains(material))
                        Bukkit.getLogger().warning("\"" + string + "\" was inserted twice!");
                    else materials.add(material);
                } catch (IllegalArgumentException ignored) {
                    Bukkit.getLogger().severe("No such material \"" + string + "\"");
                }
            }
        }

        public boolean isValid(Material material) {
            return materials.contains(material) == mode;
        }

        public boolean isNotValid(Material material) {
            return materials.contains(material) != mode;
        }

        public boolean isVoidNonValid() {
            return voidNonValid;
        }

        public boolean isKeepInventory() {
            return keepInventory;
        }
    }
}
