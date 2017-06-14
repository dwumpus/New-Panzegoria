package com.panzegoria.puzzleBuilder.Commands;

import com.panzegoria.puzzleBuilder.Entities.*;
import com.panzegoria.puzzleBuilder.Services.IBlockService;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by roger.boone on 6/13/2017.
 */
@PrepareForTest(CommandSender.class)
public class CommandSaveTest {

    private CommandSave _command;
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
        Path path = FileSystems.getDefault().getPath("C:/Users/roger.boone/IdeaProjects/PuzzleBuilder/Maps","Map-Test.txt");

        Files.deleteIfExists(path);
    }

    @Test
    public void onCommand() throws Exception {
        //arrange
        _command = new CommandSave(new MockBlockSevice(), new MockPlayerState(), new File("") );
        String[] args = {"Test"};

        //act
        _command.onCommand(_bukkitPlayer,_commandMock, "Nothing", args);

        //assert
        File file = new File("C:\\Users\\roger.boone\\IdeaProjects\\PuzzleBuilder\\Maps\\map-Test.txt");
        Assert.assertTrue(file.exists());

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
            player.Mode=MODE.MODEL_NEW_BUILDING;
            player.Selection = new MockSelection();
            return player;
        }
    }

    public class MockSelection implements ISelection {

        @Override
        public Vector getMinVector() {
            return new Vector (-1,14, 29);
        }

        @Override
        public Vector getMaxVector() {
            return new Vector (22,14, 43);
        }

        @Override
        public Vector getPoint1() {
            return null;
        }

        @Override
        public void setPoint1(Vector point1) {

        }

        @Override
        public Vector getPoint2() {
            return null;
        }

        @Override
        public void setPoint2(Vector point2) {

        }
    }

    public class MockBlockSevice implements IBlockService {

        @Override
        public WrappedBlockSet getBlocks(World world, ISelection selection) {
            String value = "4.0,5.0,6.0@ACACIA_FENCE!2\r\n1.0,2.0,3.0@STONE!1\r\n7.0,8.0,9.0@ANVIL!3\r\n";

            return new WrappedBlockSet(world, value);
        }

        @Override
        public void setBlocks(World world, WrappedBlockSet blocksIn, Vector targetVector, DIRECTION quadrant, boolean actualBlocks) {

        }

        @Override
        public void setActualBlock(Location target, WrappedBlock block) {

        }

        @Override
        public void setHintBlock(Location target, Material mat) {

        }
    }

}