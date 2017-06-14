package com.panzegoria.puzzleBuilder.Listeners;

import com.panzegoria.puzzleBuilder.Entities.IPlayersState;
import com.panzegoria.puzzleBuilder.Entities.MODE;
import com.panzegoria.puzzleBuilder.Entities.WrappedBlockSet;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import com.panzegoria.puzzleBuilder.Services.IBlockService;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

/**
 * Created by roger.boone on 6/13/2017.
 */
public class SetupPuzzleListener implements Listener{

    private IBlockService _blockService;
    private Material _configuredDraftingTool;
    private IPlayersState _stateContainer;


    public SetupPuzzleListener(Material configuredDraftingTool, IPlayersState stateContainer, IBlockService blockService) {
        _configuredDraftingTool = configuredDraftingTool;
        _stateContainer = stateContainer;
        _blockService = blockService;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws Exception {

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
        WrappedPlayer puzzlePlayer = _stateContainer.getState(player.getName()); //make one if it doesn't have one

        if ((puzzlePlayer).Mode != MODE.SETUP_PUZZLE){
            event.setCancelled(false);
            return;
        }

        Action action = event.getAction();

        if (player.getInventory() == null || player.getInventory().getItemInMainHand() == null) return;

        Material itemTypeInMainHand = player.getInventory().getItemInMainHand().getType();

        if ((action == Action.LEFT_CLICK_BLOCK) && (itemTypeInMainHand == _configuredDraftingTool)) {
            final Block clickedBlock = event.getClickedBlock();
            //ToDo: Will need undo session...
            if(puzzlePlayer.LoadedBlocks == null)
            {
                player.sendMessage("You do not have a map loaded.");
                event.setCancelled(false);
                return;
            }

            World _world = player.getWorld();
            Vector targetVector = clickedBlock.getLocation().toVector();

            player.sendMessage("Vector " + targetVector.toString());

            WrappedBlockSet blockSet = new WrappedBlockSet(_world, puzzlePlayer.LoadedBlocks);

            player.sendMessage(blockSet.toString());
            player.sendMessage(puzzlePlayer.Direction.toString());

            _blockService.setBlocks(_world, blockSet,targetVector, puzzlePlayer.Direction, false);

            event.setCancelled(true);
        }
    }
}



