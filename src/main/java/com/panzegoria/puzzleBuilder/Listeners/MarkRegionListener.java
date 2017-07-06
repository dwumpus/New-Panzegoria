package com.panzegoria.puzzleBuilder.Listeners;

import com.panzegoria.puzzleBuilder.Entities.Selection;
import com.panzegoria.puzzleBuilder.Entities.Vector3D;
import com.panzegoria.puzzleBuilder.Services.Capabilities.ObjectWrappers;
import com.panzegoria.puzzleBuilder.Services.Capabilities.Stateful;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayerState;
import com.panzegoria.puzzleBuilder.Services.SpigotWrappers;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import static com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin.wrappers;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class MarkRegionListener implements Listener {

    private Stateful _stateContainer;
    private ItemStack _regionMarker;

    public MarkRegionListener(Stateful stateContainer, ItemStack regionMarker) {

        _stateContainer = stateContainer;
        _regionMarker = regionMarker;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(IsValidEvent(event)) {

            final Player player = event.getPlayer();
            WrappedPlayerState puzzlePlayer =
                    _stateContainer.getState(player.getName()); //make one if it doesn't have one

            Selection current = puzzlePlayer.getVectorsSelected();

            Action action = event.getAction();
            final Block clickedBlock = event.getClickedBlock();
            Vector clickedVector = clickedBlock.getLocation().toVector();
            Vector3D v3D = wrappers.wrapVector(clickedVector);

            if ((action == Action.LEFT_CLICK_BLOCK)) {
                current.setPoint1(v3D);
                puzzlePlayer.setVectorsSelected(current);
                _stateContainer.savePlayerState(puzzlePlayer);
                event.setCancelled(true);
            }

            if ((action == Action.RIGHT_CLICK_BLOCK)) {
                //Check to see if this is already a used book
                current.setPoint2(v3D);
                puzzlePlayer.setVectorsSelected(current);
                _stateContainer.savePlayerState(puzzlePlayer);
                event.setCancelled(true);
            }

        } else {
            event.setCancelled(false);
        }
    }

    private boolean IsValidEvent(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        //Ensure we are allowed to use items at all. Could be in spawn zone or something...
        if (event.useItemInHand() == Event.Result.DENY) {
            return false;
        }

        //Make sure we have an item in our hand
        if (player.getInventory() == null || player.getInventory().getItemInMainHand() == null) return false;

        //Make sure we have a book in our hand
        return player.getInventory().getItemInMainHand().getType() == _regionMarker.getType();
    }

    private boolean IsChestClicked(Block block) {
        //Make sure we know the clicked block is a chest
        if(block.getType() != Material.CHEST) return false;

        return true;
    }
}

