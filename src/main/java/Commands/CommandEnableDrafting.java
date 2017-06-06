package Commands;

import Entities.PlayerManager;
import Entities.PlayerState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class CommandEnableDrafting implements CommandExecutor{

    private PlayerManager _playerManager;

    public CommandEnableDrafting(PlayerManager playerManager) {
        _playerManager = playerManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player)commandSender;

            PlayerState state = _playerManager.GetPlayerState(player.getName());
            state.IsDrafting = true;
            _playerManager.SetPlayerState(state);
            player.sendMessage("Your ability to draft new blueprints has been enabled!  Use a stick to designate points, hit a block with your stick (left for point1, and right for point 2).");
            return true;
        }

        return false;
    }
}
