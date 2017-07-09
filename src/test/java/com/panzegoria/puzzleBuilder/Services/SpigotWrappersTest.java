package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.Selection;
import com.panzegoria.puzzleBuilder.Entities.Vector3D;
import com.panzegoria.puzzleBuilder.Services.Capabilities.ObjectWrappers;
import org.bukkit.util.Vector;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by roger.boone on 7/6/2017.
 */
public class SpigotWrappersTest {
    ObjectWrappers wrappers = new SpigotWrappers();
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void wrapPlayer() throws Exception {
    }

    @Test
    public void wrapBlock() throws Exception {
    }

    @Test
    public void wrapSelection() throws Exception {
        //arrange
        //Selection


        //act

        //assert

    }

    @Test
    public void unwrapPlayer() throws Exception {
    }

    @Test
    public void placeBlock() throws Exception {
    }

    @Test
    public void placeHintBlock() throws Exception {
    }

    @Test
    public void unwrapBlocks() throws Exception {
    }

    @Test
    public void unwrapVector3D() throws Exception {
    }

    @Test
    public void wrapVector() throws Exception {
        //arrange
        Vector testVector = new Vector(5,6,7);

        //act
        Vector3D newVector = wrappers.wrapVector(testVector);

        //assert
        Assert.assertTrue(newVector.getX() == testVector.getX());
        Assert.assertTrue(newVector.getY() == testVector.getY());
        Assert.assertTrue(newVector.getZ() == testVector.getZ());
    }

    @Test
    public void placeBlocks() throws Exception {
    }

    @Test
    public void placeHintBlocks() throws Exception {
    }

}