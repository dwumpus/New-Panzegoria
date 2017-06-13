package com.panzegoria.puzzleBuilder.Commands;

import com.panzegoria.puzzleBuilder.Entities.*;
import com.panzegoria.puzzleBuilder.Services.IBlockService;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.File;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by roger.boone on 6/13/2017.
 */
@PrepareForTest(CommandSender.class)
public class CommandBlueprintSaveTest {

    private CommandBlueprintSave _command;
    private WrappedPlayer _player;
    private PlayersState _playerState;
    private String _playerName;
    private Player _bukkitPlayer;
    private Command _commandMock;

    @Before
    public void setUp() throws Exception {
        _bukkitPlayer = PowerMockito.mock(Player.class);
        when(_bukkitPlayer.getName()).thenReturn("TestPlayer");

        _commandMock = PowerMockito.mock(Command.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void onCommand() throws Exception {
        //arrange
        _command = new CommandBlueprintSave(new MockBlockSevice(), new MockPlayerState(), new File(".") );
        String[] args = {"Test"};

        //act
        _command.onCommand(_bukkitPlayer,_commandMock, "Nothing", args);

        //assert
        

    }

    public class MockPlayerState implements IPlayersState {

        @Override
        public void savePlayerState(WrappedPlayer state) {

        }

        @Override
        public Integer getBlockCount() {
            return null;
        }

        @Override
        public WrappedPlayer getState(String name) {
            return null;
        }
    }

    public class MockBlockSevice implements IBlockService {

        @Override
        public WrappedBlockSet getBlocks(World world, ISelection selection) {
            return null;
        }

        @Override
        public void setBlocks(World world, WrappedBlockSet blocksIn, Vector targetVector, DIRECTION quadrant, boolean actualBlocks) {

        }

        @Override
        public void setActualBlock(World world, Vector target, WrappedBlock block) {

        }

        @Override
        public void setHintBlock(World world, Vector target, Material mat) {

        }
    }

}