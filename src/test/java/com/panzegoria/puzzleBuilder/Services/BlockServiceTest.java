package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.Selection;
import org.bukkit.World;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

/**
 * Created by roger.boone on 6/8/2017.
 */
public class BlockServiceTest {
    World _world;
    Selection _selection;
    BlockService _blockService;

    @Before
    public void setUp() throws Exception {
        _world = PowerMockito.mock(World.class);
        _selection = PowerMockito.mock(Selection.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getBlocks() throws Exception {
    }

    @Test
    public void setBlocks() throws Exception {
    }

}