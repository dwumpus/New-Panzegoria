package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.World;
import org.bukkit.util.Vector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.junit.Assert.*;

/**
 * Created by roger.boone on 6/5/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(World.class)
public class SelectionTest {

    private Vector point1 = new Vector(1,10,-3);
    private Vector point2 = new Vector(10,5,22);

    @Test
    public void testConstructor() {
        //arrange

        //act
        Selection selection = new Selection();

        //assert
        assertTrue("Instanced Correctly",selection instanceof Selection);
    }

    @Test
    public void testGetMin (){
        //arrange
        Selection selection = new Selection();
        selection.setPoint1(point1);
        selection.setPoint2(point2);

        //act
        Vector minLocation = selection.getMinVector();
        Vector locationVector = minLocation;

        //assert
        Vector idealResult = new Vector(1,5,-3);
        assertEquals(idealResult, locationVector);
    }

    @Test
    public void testGetMax (){
        //arrange
        Selection selection = new Selection();
        selection.setPoint1(point1);
        selection.setPoint2(point2);

        //act
        Vector maxLocation = selection.getMaxVector();
        Vector locationVector = maxLocation;

        //assert
        Vector idealResult = new Vector(11,12,23);
        assertEquals(idealResult, locationVector);
    }
}
