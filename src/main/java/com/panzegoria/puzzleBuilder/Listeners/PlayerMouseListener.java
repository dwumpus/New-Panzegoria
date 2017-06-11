package com.panzegoria.puzzleBuilder.Listeners;

import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class PlayerMouseListener implements Listener {

    private Material _configuredDraftingTool;
    private PlayersState _stateContainer;

    public PlayerMouseListener(Material configuredDraftingTool, PlayersState stateContainer) {
        _configuredDraftingTool = configuredDraftingTool;
        _stateContainer = stateContainer;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }

        try {
            if (event.getHand() == EquipmentSlot.OFF_HAND) {
                return; // TODO api needs to be able to get either hand depending on event
                // for now just ignore all off hand interacts
            }
        } catch (NoSuchMethodError ignored) {
        } catch (NoSuchFieldError ignored) {
        }

        final Player player = event.getPlayer();
        WrappedPlayer puzzlePlayer = _stateContainer.getOrDefault(player.getName(),
                new WrappedPlayer(player,_stateContainer)); //make one if it doesn't have one

        if (!(puzzlePlayer).IsDrafting){
            event.setCancelled(false);
            return;
        }

        Action action = event.getAction();

        if (player.getInventory() == null || player.getInventory().getItemInMainHand() == null) return;

        Material itemTypeInMainHand = player.getInventory().getItemInMainHand().getType();

        if ((action == Action.LEFT_CLICK_BLOCK) && (itemTypeInMainHand == _configuredDraftingTool)) {
            final Block clickedBlock = event.getClickedBlock();
            puzzlePlayer.Selection.setPoint1(clickedBlock.getLocation().toVector());
            puzzlePlayer.Save();
            event.setCancelled(true);
        }

        if ((action == Action.RIGHT_CLICK_BLOCK) && (itemTypeInMainHand == _configuredDraftingTool)) {
            final Block clickedBlock = event.getClickedBlock();
            puzzlePlayer.Selection.setPoint2(clickedBlock.getLocation().toVector());
            puzzlePlayer.Save();
            event.setCancelled(true);
        }
    }
}

