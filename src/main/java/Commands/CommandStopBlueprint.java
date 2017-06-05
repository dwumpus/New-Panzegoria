package Commands;

import Entities.BlueprintState;
import Entities.PlayerState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class CommandStopBlueprint implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player)commandSender;

            PlayerState state = BlueprintState.getInstance().GetPlayerState(player.getName());
            state.IsDrafting = false;
            BlueprintState.getInstance().SetPlayerState(state);
            return true;
        }

        return false;
    }
}
