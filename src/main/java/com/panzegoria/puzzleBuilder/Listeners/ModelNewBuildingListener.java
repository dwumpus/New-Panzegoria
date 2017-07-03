package com.panzegoria.puzzleBuilder.Listeners;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.panzegoria.puzzleBuilder.Entities.IPlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedBlockSet;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


/**
 * Created by roger.boone on 6/4/2017.
 */
public class ModelNewBuildingListener implements Listener {

    private IPlayersState _stateContainer;
    private static String REST_URI
            = "http://localhost:8080/PuzzleRepositoryRest-0.0.1-SNAPSHOT/rest/puzzle";
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

            try {
                savePuzzle("test", blockSet.toString());
            } catch (Exception ex) {

            }
        }
        return true;
    }

    public void savePuzzle(String name, String puzzleData) throws Exception {
        String navigation;

        String uri = String.format("%s/post?name=%s",REST_URI, name);

        HttpResponse<JsonNode> jsonResponse = Unirest.post(uri)
                .header("accept", "application/json")
                .queryString("name", name)
                .field("name", puzzleData)
                .asJson();

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

