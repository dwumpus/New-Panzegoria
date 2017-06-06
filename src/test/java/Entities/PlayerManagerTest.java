package Entities;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.Vector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by roger.boone on 6/6/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({Player.class, PlayerInteractEvent.class, PlayerInventory.class})
public class PlayerManagerTest {
    PlayerManager _pm;
    Player _player;

    @Before
    public void setUp() throws Exception {
        _pm = new PlayerManager();
        _player = mock(Player.class);
        when(_player.getName()).thenReturn("TestPlayer");
    }

    @After
    public void tearDown() throws Exception {
        _pm = null;
    }

    @Test
    public void setPlayerState() throws Exception {
        //arrange
        PlayerState state = _pm.GetPlayerState(_player.getName());
        state.Point1 = new Vector(3,3,3);
        _pm.SetPlayerState(state);

        //act
        state.Point1 = new Vector(4,3,3);
        _pm.SetPlayerState(state);

        //assert
        Assert.assertTrue(_pm.GetPlayerState(_player.getName()).Point1.getX()==4);
    }

    @Test
    public void setPlayerStateWithSelection() throws Exception {
        //arrange
        PlayerState state = _pm.GetPlayerState(_player.getName());
        state.Point1 = new Vector(3,3,3);
        Selection selection = new Selection(null, state.Point1, state.Point1);
        state.SelectedLocations = selection;
        _pm.SetPlayerState(state);

        //act
        state.Point1 = new Vector(4,3,3);
        _pm.SetPlayerState(state);

        //assert
        Assert.assertTrue(_pm.GetPlayerState(_player.getName()).Point1.getX()==4);
        Assert.assertTrue(_pm.GetPlayerState(_player.getName()).SelectedLocations == selection);
    }


    @Test
    public void getPlayerState() throws Exception {
        //arrange

        //act

        //assert
    }

}