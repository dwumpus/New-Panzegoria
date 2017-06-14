package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.*;
import org.bukkit.Location;

import org.bukkit.World;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by roger.boone on 6/8/2017.
 */
public class BlockServiceTest {
    World _world;
    ISelection _selection;
    IBlockService _blockService;
    private Player _bukkitPlayer;
    Location loc;
    ArmorStand armorStand;

    @Before
    public void setUp() throws Exception {
        _world = PowerMockito.mock(World.class);
        loc = new Location(_world, 0,0,0);
        armorStand = PowerMockito.mock(ArmorStand.class);
        when(_world.spawn(loc, ArmorStand.class)).thenReturn(armorStand);

        _selection = PowerMockito.mock(Selection.class);
        _bukkitPlayer = PowerMockito.mock(Player.class);
        when(_bukkitPlayer.getName()).thenReturn("TestPlayer");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getBlocks() throws Exception {
    }

    @Test
    public void setBlocks() throws Exception {
        //arrange
        IBlockService blockService = new BlockService();
        String value = "4.0,5.0,6.0@ACACIA_FENCE!2\r\n1.0,2.0,3.0@STONE!1\r\n7.0,8.0,9.0@ANVIL!3\r\n";

        //act
        WrappedBlockSet set = new WrappedBlockSet(_world, value);
        _blockService = new BlockService();
        //_blockService.setBlocks(_world,set, new Vector(0,0,0),DIRECTION.NORTH_EAST, false);

        //assert
        //times(1).verify(_world.spawn(loc, ArmorStand.class));


    }

    public class MockPlayerState implements IPlayersState {

        @Override
        public void savePlayerState(WrappedPlayer state) {

        }

        @Override
        public Integer getBlockCount() {
            return 3;
        }

        @Override
        public WrappedPlayer getState(String name) {
            WrappedPlayer player = new WrappedPlayer(_bukkitPlayer, this);
            player.Mode= MODE.SETUP_PUZZLE;

            return player;
        }
    }

}