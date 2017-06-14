package com.panzegoria.puzzleBuilder.Listeners; /**
 * Created by roger.boone on 6/5/2017.
 */
import com.panzegoria.puzzleBuilder.Entities.MODE;
import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
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
public class ModelNewBuildingListenerTest {

    PlayerInteractEvent event;
    Player player;
    ModelNewBuildingListener modelNewBuildingListener;
    Block block;
    Block block2;
    WrappedPlayer wrappedPlayer;
    Material configuredItem;
    PlayerInventory inventory;
    PlayersState playerState;
    Vector point1;
    Vector point2;

    private void initializeMocks() {
        //mock all the minecraft items involved
        World world = PowerMockito.mock(World.class);
        playerState = new PlayersState();

        configuredItem = Material.STICK;
        point1 = new Vector(10,4,-2);
        point2 = new Vector(110, -24, 22);

        block = PowerMockito.mock(Block.class);
        when(block.getLocation()).thenReturn(new Location(world,point1.getX(),point1.getY(),point1.getZ()));
        when(block.getType()).thenReturn(Material.STONE);

        block2 = PowerMockito.mock(Block.class);
        when(block2.getLocation()).thenReturn(new Location(world, point2.getX(),point2.getY(),point2.getZ()));
        when(block2.getType()).thenReturn(Material.STONE);

        inventory = PowerMockito.mock(PlayerInventory.class);
        when(inventory.getItemInMainHand()).thenReturn(new ItemStack(configuredItem));

        player = PowerMockito.mock(Player.class);
        when(player.getName()).thenReturn("TestPlayer");
        when(player.getInventory()).thenReturn(inventory);

        wrappedPlayer = new WrappedPlayer(player, playerState);
        wrappedPlayer.Save();

        modelNewBuildingListener = new ModelNewBuildingListener(Material.STICK, playerState);
    }

    @Test
    public void testLeftClickDraftingOn() {
        //arrange
        initializeMocks();
        event = PowerMockito.mock(PlayerInteractEvent.class);
        when(event.getPlayer()).thenReturn(player);
        when(event.getAction()).thenReturn(Action.LEFT_CLICK_BLOCK);
        when(event.getClickedBlock()).thenReturn(block);
        wrappedPlayer.Mode = MODE.MODEL_NEW_BUILDING;

        //act
        modelNewBuildingListener.onPlayerInteract(event);
        Vector point = playerState.getState(player.getName()).Selection.getMinVector();

        //assert
        Assert.assertTrue(point.getX()==10);
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
        modelNewBuildingListener.onPlayerInteract(event);
        Vector point = playerState.getState(player.getName()).Selection.getPoint1();

        //assert
        Assert.assertTrue(point == null);
    }

    @Test
    public void testLeftClickDraftingOff() {
        //arrange
        initializeMocks();
        event = PowerMockito.mock(PlayerInteractEvent.class);
        when(event.getPlayer()).thenReturn(player);
        when(event.getAction()).thenReturn(Action.LEFT_CLICK_BLOCK);
        when(event.getClickedBlock()).thenReturn(block);

        wrappedPlayer = playerState.getState(player.getName());
        wrappedPlayer.Selection.setPoint1(new Vector(0,0,0));
        wrappedPlayer.Mode= MODE.BUILD_PUZZLE;
        wrappedPlayer.Save();

        //act
        modelNewBuildingListener.onPlayerInteract(event);
        Vector point = playerState.getState(player.getName()).Selection.getPoint1();

        //assert
        Assert.assertTrue(point.getX() == 0);
    }

    @Test
    public void testRightClick() {
        //arrange
        initializeMocks();

        //setup the mouseclick
        event = PowerMockito.mock(PlayerInteractEvent.class);
        when(event.getPlayer()).thenReturn(player);
        when(event.getAction()).thenReturn(Action.RIGHT_CLICK_BLOCK);
        when(event.getClickedBlock()).thenReturn(block2);

        //act
        wrappedPlayer.Selection.setPoint1(point1);
        wrappedPlayer.Mode = MODE.MODEL_NEW_BUILDING;
        wrappedPlayer.Save();
        wrappedPlayer = playerState.getState(player.getName());

        //click the mouse button
        modelNewBuildingListener.onPlayerInteract(event);

        //get the results
        Vector point = wrappedPlayer.Selection.getMaxVector();

        //assert
        Assert.assertTrue(point.getX()==111);
    }
}
