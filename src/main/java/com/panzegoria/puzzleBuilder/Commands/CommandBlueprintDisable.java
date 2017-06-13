package com.panzegoria.puzzleBuilder.Commands;

import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class CommandBlueprintDisable implements CommandExecutor {
    PlayersState _playerState;

    public CommandBlueprintDisable(PlayersState playerState) {
        _playerState = playerState;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            WrappedPlayer state = _playerState.getState(player.getName());
            state.IsDrafting = false;
            state.Save();
            player.sendMessage("Your ability to draft new blueprints has been disabled.");
            return true;
        }

        return false;
    }
}
