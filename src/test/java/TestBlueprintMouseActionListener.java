/**
 * Created by roger.boone on 6/5/2017.
 */
import Controllers.BlueprintService;
import Entities.PlayerManager;
import Entities.PlayerState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


/**
 * Created by roger.boone on 6/5/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Player.class, PlayerInteractEvent.class, PlayerInventory.class})
public class TestBlueprintMouseActionListener {

    PlayerInteractEvent event;
    Player player;
    BlueprintMouseActionListener blueprintMouseActionListener;
    BlueprintService blueprintService;
    Block block;
    Block block2;
    PlayerManager playerManager;
    PlayerState playerState;
    ItemStack configuredItem;
    ItemStack notConfiguredItem;
    PlayerInventory inventory;

    private void initializeMocks() {
        //mock all the minecraft items involved
        World world = PowerMockito.mock(World.class);
        configuredItem = mock(ItemStack.class);
        when(configuredItem.getType()).thenReturn(Material.STICK);
        when(configuredItem.toString()).thenReturn(Material.STICK.toString());
        when(configuredItem.hasItemMeta()).thenReturn(false);

        inventory = mock(PlayerInventory.class);
        when(inventory.getItemInMainHand()).thenReturn(configuredItem);

        block = PowerMockito.mock(Block.class);
        when(block.getLocation()).thenReturn(new Location(world, 10, 4, -2));
        when(block.getType()).thenReturn(Material.STONE);

        block2 = PowerMockito.mock(Block.class);
        when(block2.getLocation()).thenReturn(new Location(world, 110, -24, 22));
        when(block2.getType()).thenReturn(Material.STONE);

        player = PowerMockito.mock(Player.class);
        when(player.getName()).thenReturn("TestPlayer");
        when(player.getInventory()).thenReturn(inventory);

        playerManager = new PlayerManager();
        playerState = new PlayerState("TestPlayer", true);
        playerManager.SetPlayerState(playerState);
        blueprintService = new BlueprintService(playerManager);
        blueprintMouseActionListener = new BlueprintMouseActionListener(blueprintService, playerManager);
    }

    @Test
    public void testLeftClickDraftingOn() {
        //arrange
        initializeMocks();
        event = PowerMockito.mock(PlayerInteractEvent.class);
        when(event.getPlayer()).thenReturn(player);
        when(event.getAction()).thenReturn(Action.LEFT_CLICK_BLOCK);
        when(event.getClickedBlock()).thenReturn(block);

        //act
        blueprintMouseActionListener.onPlayerInteract(event);
        PlayerState state = playerManager.GetPlayerState(player.getName());

        //assert
        Assert.assertTrue(state.Point1.getX()==10);
    }

    @Test
    public void testLeftClickOnAir() {
        //arrange
        initializeMocks();
        event = PowerMockito.mock(PlayerInteractEvent.class);
        when(event.getPlayer()).thenReturn(player);
        when(event.getAction()).thenReturn(Action.LEFT_CLICK_AIR);
        when(event.getClickedBlock()).thenReturn(block);

        //act
        blueprintMouseActionListener.onPlayerInteract(event);
        PlayerState state = playerManager.GetPlayerState(player.getName());

        //assert
        Assert.assertTrue(state.Point1 == null);
    }

    @Test
    public void testLeftClickDraftingOff() {
        //arrange
        initializeMocks();
        event = PowerMockito.mock(PlayerInteractEvent.class);
        when(event.getPlayer()).thenReturn(player);
        when(event.getAction()).thenReturn(Action.LEFT_CLICK_BLOCK);
        when(event.getClickedBlock()).thenReturn(block);

        playerState = new PlayerState("TestPlayer", false);
        playerManager.SetPlayerState(playerState);

        //act
        blueprintMouseActionListener.onPlayerInteract(event);
        PlayerState state = playerManager.GetPlayerState(player.getName());

        //assert
        Assert.assertTrue(state.Point1 == null);
    }

    @Test
    public void testRightClick() {
        //arrange
        initializeMocks();
        event = PowerMockito.mock(PlayerInteractEvent.class);
        when(event.getPlayer()).thenReturn(player);
        when(event.getAction()).thenReturn(Action.RIGHT_CLICK_BLOCK);
        when(event.getClickedBlock()).thenReturn(block2);

        //act
        blueprintMouseActionListener.onPlayerInteract(event);
        PlayerState state = playerManager.GetPlayerState(player.getName());

        //assert
        Assert.assertTrue(state.SelectedLocations.getMaxlocation().getX()==110);
    }
}
