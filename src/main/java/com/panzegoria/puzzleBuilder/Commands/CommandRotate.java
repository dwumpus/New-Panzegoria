package com.panzegoria.puzzleBuilder.Commands;

import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import com.panzegoria.puzzleBuilder.Services.BlockService;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class CommandRotate implements CommandExecutor {

    PlayersState _playerState;

    public CommandRotate(PlayersState playerState) {
        _playerState = playerState;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;

            WrappedPlayer state = _playerState.get(player.getName());
            state.Direction = getRotation(state.Direction);
            state.Save();
            player.sendMessage(String.format("New Build Direction %s", state.Direction.toString()));
            return true;
        }
        return false;
    }

    private BlockService.DIRECTION getRotation(BlockService.DIRECTION currentDirection) {
        switch (currentDirection) {
            case NORTH_EAST: {
                return BlockService.DIRECTION.SOUTH_EAST;
            }
            case SOUTH_EAST: {
                return BlockService.DIRECTION.SOUTH_WEST;
            }
            case SOUTH_WEST: {
                return BlockService.DIRECTION.NORTH_WEST;
            }
            case NORTH_WEST: {
                return BlockService.DIRECTION.NORTH_EAST;
            }
            default:
                return BlockService.DIRECTION.NORTH_EAST;
        }
    }
}
