import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class BlueprintListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {

        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }

        try {
            if (event.getHand() == EquipmentSlot.OFF_HAND) {
                return; // TODO api needs to be able to get either hand depending on event
                // for now just ignore all off hand interacts
            }
        } catch (NoSuchMethodError ignored) {
        } catch (NoSuchFieldError ignored) {
        }

        final Player player = event.getPlayer();
        final World world = player.getWorld();

        Action action = event.getAction();
        if (action == Action.LEFT_CLICK_BLOCK) {
            final Block clickedBlock = event.getClickedBlock();

            player.chat(String.format("you clicked on a block %s", clickedBlock.getType().toString()));
            event.setCancelled(true);
        }
        else if (action == Action.RIGHT_CLICK_BLOCK) {
            final Block clickedBlock = event.getClickedBlock();

            player.chat(String.format("you right clicked on a block %s", clickedBlock.getType().toString()));
            event.setCancelled(true);
        }
    }
}