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
public class CommandBlueprintState implements CommandExecutor{
    private PlayerManager playerManager;

    public CommandBlueprintState(PlayerManager bpState) {
        playerManager = bpState;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player)commandSender;

            PlayerState state = playerManager.GetPlayerState(player.getName());
            player.sendMessage(String.format("Drafting is currently %s", state.IsDrafting ? "Enabled": "Disabled"));
            if(state.IsDrafting)
                player.sendMessage(String.format("The current selection is: %s", state.SelectedLocations));

            return true;
        }
        return false;
    }
}
