package com.panzegoria.puzzleBuilder.Listeners;

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.panzegoria.puzzleBuilder.Entities.*;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.List;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class ModelNewBuildingListener implements Listener {

    private IPlayersState _stateContainer;

    public ModelNewBuildingListener(IPlayersState stateContainer) {
        _stateContainer = stateContainer;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if(IsValidEvent(event)) {

            final Player player = event.getPlayer();
            WrappedPlayer puzzlePlayer = _stateContainer.getState(player.getName()); //make one if it doesn't have one

            Action action = event.getAction();


            if ((action == Action.LEFT_CLICK_BLOCK)) {
                final Block clickedBlock = event.getClickedBlock();
                puzzlePlayer.Selection.setPoint1(clickedBlock.getLocation().toVector());
                puzzlePlayer.Save();
                event.setCancelled(true);
            }

            if ((action == Action.RIGHT_CLICK_BLOCK)) {
                final Block clickedBlock = event.getClickedBlock();
                if(puzzlePlayer.Selection.getPoint1() != null) {
                    HandleRightClick(player, clickedBlock);
                    PuzzleBuilderPlugin.INSTANCE.getLogger().info("Right Click Handled!");
                    event.setCancelled(true);
                }
                event.setCancelled(false);
            }

        } else {
            event.setCancelled(false);
        }
    }

    private boolean HandleRightClick(Player player, Block blockClicked) {
        WrappedPlayer puzzlePlayer = _stateContainer.getState(player.getName());
        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if(itemStack.getType() == Material.BOOK_AND_QUILL) {
            //Check to see if this is already a used book


            puzzlePlayer.Selection.setPoint2(blockClicked.getLocation().toVector());
            WrappedBlockSet blockSet = new WrappedBlockSet(player.getWorld(), puzzlePlayer.Selection);


        }
        return true;
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
        return player.getInventory().getItemInMainHand().getType() == Material.BOOK_AND_QUILL;
    }

    private boolean IsChestClicked(Block block) {
        //Make sure we know the clicked block is a chest
        if(block.getType() != Material.CHEST) return false;

        return true;
    }
}

