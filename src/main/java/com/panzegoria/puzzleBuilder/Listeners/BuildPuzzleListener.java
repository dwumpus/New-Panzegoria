package com.panzegoria.puzzleBuilder.Listeners;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

/**
 * Created by roger.boone on 6/10/2017.
 */
public class BuildPuzzleListener implements Listener {

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();

        if (entity.getType() == EntityType.ARMOR_STAND) {
            ArmorStand stand = (ArmorStand) entity;
            Player player = event.getPlayer();
            Location location = entity.getLocation();

            if (stand.getHelmet().getType() == player.getInventory().getItemInMainHand().getType()) {
                entity.remove();
                location.add(0, 1, 0).getBlock().setType(player.getInventory().getItemInMainHand().getType());
            }

        }
    }

}
