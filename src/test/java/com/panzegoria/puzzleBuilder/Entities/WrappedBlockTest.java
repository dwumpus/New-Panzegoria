package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.*;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by roger.boone on 6/12/2017.
 */
public class WrappedBlockTest {
    Block block;
    World world;
    Material material = Material.STONE;
    Byte data = new Byte("2");
    WrappedBlock wrappedBlock;

    public WrappedBlockTest() {
        world = PowerMockito.mock(World.class);

        block = PowerMockito.mock(Block.class);
        when(block.getType()).thenReturn(material);
        when(block.getData()).thenReturn(data);
        when(block.getWorld()).thenReturn(world);

    }

    @Test
    public void setActualBlock() {
    }

    @Test
    public void setHintBlock()  {
    }

    @Test
    public void testToString()  {
        //arrange
        wrappedBlock = new WrappedBlock(block);
        //act
        String valueOut = wrappedBlock.toString();

        //assert
        valueOut.contains("STONE|2");
    }

}