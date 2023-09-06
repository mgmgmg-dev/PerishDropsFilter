package me.mgmgmg.perishdropsfilter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PerishEvent implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        ConfigManager.WorldSettings settings = ConfigManager.getWorldSettings().get(event.getEntity().getWorld().getName());
        if (settings == null) return;
        if (settings.isKeepInventory()) {
            // Makes sure the player gets to keep their inventory
            event.setKeepInventory(true);

            // Removes all non-valid items
            for(ItemStack stack : event.getEntity().getInventory().getStorageContents()) {
                if (stack != null && settings.isNotValid(stack.getType()))
                    event.getEntity().getInventory().remove(stack);
            }

            // Either prevents items from being dropped or drops non-valid items
            if (settings.isVoidNonValid()) event.getDrops().clear();
            else event.getDrops().removeIf(stack -> stack != null && settings.isValid(stack.getType()));
        } else {
            // Removes all non-valid drops
            event.getDrops().removeIf(stack -> stack != null && settings.isNotValid(stack.getType()));

            // Keeps all non-valid items if necessary
            if (settings.isVoidNonValid()) {
                event.setKeepInventory(true);
                for (ItemStack stack : event.getEntity().getInventory().getStorageContents()) {
                    if (stack != null && settings.isValid(stack.getType()))
                        event.getEntity().getInventory().remove(stack);
                }
            }
        }
    }
}
