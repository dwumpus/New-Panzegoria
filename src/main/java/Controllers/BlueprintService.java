package Controllers;

import Entities.PlayerManager;
import Entities.PlayerState;
import Entities.Selection;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class BlueprintService {
    private PlayerManager playerManager;

    public boolean IsValidRangeSelected() {
        return isValidRangeSelected;
    }

    private boolean isValidRangeSelected=false;

    public BlueprintService(PlayerManager bpState) {
        playerManager = bpState;
    }

    public void SetRangePoint1(Player player, Block clickedBlock) {
        isValidRangeSelected = false;
        PlayerState state = playerManager.GetPlayerState(player.getName());
        state.Point1 = clickedBlock.getLocation().toVector();
    }

    public void SetRangePoint2(Player player, Block clickedBlock) {
        PlayerState state = playerManager.GetPlayerState(player.getName());
        state.Point2 = clickedBlock.getLocation().toVector();
        state.SelectedLocations = new Selection(player.getWorld(), state.Point1, state.Point2);
        playerManager.SetPlayerState(state);
        isValidRangeSelected=true;
        LogPlayerSelection(player,state);
    }

    private void LogPlayerSelection(Player player, PlayerState state) {
        player.sendMessage(state.SelectedLocations.getMinlocation().toString());
        player.sendMessage(state.SelectedLocations.getMaxlocation().toString());
    }

}
