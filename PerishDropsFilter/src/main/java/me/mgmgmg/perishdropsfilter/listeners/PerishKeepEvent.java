package me.mgmgmg.perishdropsfilter.listeners;

import me.mgmgmg.perishdropsfilter.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PerishKeepEvent implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerPerish(PlayerDeathEvent event) {
        // Makes sure the player gets to keep their inventory and xp
        event.setKeepInventory(true);
        event.setKeepLevel(true);

        // Removes all non-whitelisted items
        for(ItemStack stack : event.getEntity().getInventory().getStorageContents()) {
            if (stack != null && !ConfigManager.getWhitelist().contains(stack.getType()))
                event.getEntity().getInventory().remove(stack);
        }

        // Prevents items from being dropped
        event.getDrops().clear();
    }
}
