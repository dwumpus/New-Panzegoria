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
public class CommandDisableDrafting implements CommandExecutor{
    private PlayerManager playerManager;

    public CommandDisableDrafting(PlayerManager bpState) {
        playerManager = bpState;
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player)commandSender;

            PlayerState state = playerManager.GetPlayerState(player.getName());
            state.IsDrafting = false;
            playerManager.SetPlayerState(state);
            player.sendMessage("Your ability to draft new blueprints has been disabled.");
            return true;
        }

        return false;
    }
}
