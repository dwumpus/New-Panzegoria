package com.panzegoria.puzzleBuilder.Commands;

import com.panzegoria.puzzleBuilder.Entities.IPlayersState;
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
public class CommandBlueprintEnable implements CommandExecutor {

    IPlayersState _playerState;

    public CommandBlueprintEnable(IPlayersState playerState) {
        _playerState = playerState;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            WrappedPlayer state = _playerState.getState(player.getName());
            state.IsDrafting = true;
            state.Save();
            return true;
        }

        return false;
    }
}
