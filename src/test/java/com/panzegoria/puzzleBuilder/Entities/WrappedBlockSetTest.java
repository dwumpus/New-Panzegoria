package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by roger.boone on 6/12/2017.
 */
public class WrappedBlockSetTest {
    WrappedBlockSet set;
    World _world;


    @Before
    public void setUp() {
        _world = PowerMockito.mock(World.class);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testToString() {
        //arrange
        set = new WrappedBlockSet();
        Block block1 = PowerMockito.mock(Block.class);
        when(block1.getType()).thenReturn(Material.STONE);
        when(block1.getData()).thenReturn(new Byte("1"));
        when(block1.getWorld()).thenReturn(_world);
        when(block1.getLocation()).thenReturn(new Location(_world, 1,2,3));

        Block block2 = PowerMockito.mock(Block.class);
        when(block2.getType()).thenReturn(Material.ACACIA_FENCE);
        when(block2.getData()).thenReturn(new Byte("2"));
        when(block2.getWorld()).thenReturn(_world);
        when(block2.getLocation()).thenReturn(new Location(_world, 4,5,6));

        Block block3 = PowerMockito.mock(Block.class);
        when(block3.getType()).thenReturn(Material.ANVIL);
        when(block3.getData()).thenReturn(new Byte("3"));
        when(block3.getWorld()).thenReturn(_world);
        when(block3.getLocation()).thenReturn(new Location(_world, 7,8,9));

        set.addWrappedBlock(block1.getLocation().toVector(), new WrappedBlock(block1));
        set.addWrappedBlock(block2.getLocation().toVector(), new WrappedBlock(block2));
        set.addWrappedBlock(block3.getLocation().toVector(), new WrappedBlock(block3));

        //act
        String fullValue = set.toString();

        //assert
        Assert.assertTrue(fullValue.contains("ANVIL"));
    }

    @Test
    public void TestConstructorDeserialize() {
        //arrange
        String value = "4.0,5.0,6.0@ACACIA_FENCE!2\r\n1.0,2.0,3.0@STONE!1\r\n7.0,8.0,9.0@ANVIL!3\r\n";

        //act
        set = new WrappedBlockSet(_world, value);

        //assert
        //Assert.assertTrue(set.getBlockCount() ==3);
        //Assert.assertTrue(set.getBlocks().containsKey(new Vector(7.0,8.0,9.0)));
       // Assert.assertTrue(set.getBlocks().get(new Vector(7.0,8.0,9.0)).BlockType == Material.ANVIL);
    }

}