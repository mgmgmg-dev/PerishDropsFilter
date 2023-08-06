package me.mgmgmg.perishdropsfilter.listeners;

import me.mgmgmg.perishdropsfilter.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PerishDropEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerPerish(PlayerDeathEvent event) {
        // Removes all non-whitelisted drops
        event.getDrops().removeIf(
                itemStack -> itemStack != null && !ConfigManager.getWhitelist().contains(itemStack.getType())
        );
    }
}
