import Controllers.BlueprintService;
import Controllers.PlayerManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class BlueprintMouseActionListener implements Listener {

    private BlueprintService _blueprintService;
    private PlayerManager _playerManager;
    private ItemStack _configuredDraftingTool;

    public BlueprintMouseActionListener(BlueprintService blueprintService, PlayerManager playerManager, ItemStack configuredDraftingTool) {
        this._blueprintService = blueprintService;
        this._playerManager = playerManager;
        _configuredDraftingTool = configuredDraftingTool;
    }

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

        if(!_playerManager.GetPlayerState(player.getName()).IsDrafting){
            event.setCancelled(false);
            return;
        }

        Action action = event.getAction();
        if (action == Action.LEFT_CLICK_BLOCK) {
            if(player.getInventory() == null || player.getInventory().getItemInMainHand() == null) return;

            if(player.getInventory().getItemInMainHand().getType() == _configuredDraftingTool.getType()){
                final Block clickedBlock = event.getClickedBlock();

                _blueprintService.SetRangePoint1(player, clickedBlock);

                event.setCancelled(true);
            }
        }
        else if (action == Action.RIGHT_CLICK_BLOCK) {
            if(player.getInventory() == null || player.getInventory().getItemInMainHand() == null) return;

            if(player.getInventory().getItemInMainHand().getType() == _configuredDraftingTool.getType()) {
                final Block clickedBlock = event.getClickedBlock();

                _blueprintService.SetRangePoint2(player, clickedBlock);

                event.setCancelled(true);
            }
        }
    }
}
