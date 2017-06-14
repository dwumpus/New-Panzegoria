package com.panzegoria.puzzleBuilder.Commands;

import com.panzegoria.puzzleBuilder.Entities.IPlayersState;
import com.panzegoria.puzzleBuilder.Entities.MODE;
import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class CommandToggleMode implements CommandExecutor {

    IPlayersState _playerState;

    public CommandToggleMode(IPlayersState playerState) {
        _playerState = playerState;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            WrappedPlayer state = _playerState.getState(player.getName());
            state.Mode = getNextMode(state.Mode);
            player.sendMessage(String.format("Your new mode is: %s", state.Mode.toString()));
            state.Save();
            return true;
        }

        return false;
    }

    private MODE getNextMode(MODE modeIn) {
        switch (modeIn) {
            case BUILD_PUZZLE:
                return MODE.CREATE_BUILDING;
            case CREATE_BUILDING:
                return MODE.MODEL_NEW_BUILDING;
            case MODEL_NEW_BUILDING:
                return MODE.SETUP_PUZZLE;
            default:
                return MODE.BUILD_PUZZLE;
        }
    }
}
