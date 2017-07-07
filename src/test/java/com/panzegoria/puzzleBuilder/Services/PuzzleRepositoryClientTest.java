package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.Vector3D;
import com.panzegoria.puzzleBuilder.Entities.WrappedBlock;
import com.panzegoria.puzzleBuilder.Entities.WrappedBlockSet;
import com.panzegoria.puzzleBuilder.Services.Capabilities.ObjectWrappers;
import com.panzegoria.puzzleBuilder.Services.Capabilities.PuzzlePersistence;
import javafx.scene.paint.Material;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by roger.boone on 7/6/2017.
 */
public class PuzzleRepositoryClientTest {
    private Random _random = new Random();
    private PuzzlePersistence _persistence;

    @Before
    public void setUp() throws Exception {
        String url = "http://localhost:8080/PuzzleRepositoryRest-0.0.1-SNAPSHOT/rest/puzzle";
        _persistence = new PuzzleRepositoryClient(url);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void savePuzzle() throws Exception {
        //arrange
        WrappedBlockSet set = getMockBlockSet(2000000);

        //act
        _persistence.savePuzzle("chicken", set);

        //assert

    }

    @Test
    public void loadPuzzle() throws Exception {
    }

    private WrappedBlockSet getMockBlockSet(int qtyBlocks) {

        WrappedBlock[] blocks = new WrappedBlock[qtyBlocks];

        for (int i = 0; i < qtyBlocks; i++) {
            blocks[i] = getMockBlock();
        }

        WrappedBlockSet out = new WrappedBlockSet();
        out.setMap(blocks);

        return out;
    }

    private WrappedBlock getMockBlock() {
        WrappedBlock block1 = new WrappedBlock();

        double x = (double)_random.nextInt(50) + 1;
        double y = (double)_random.nextInt(50) + 1;
        double z = (double)_random.nextInt(50) + 1;

        Vector3D v3D = new Vector3D();
        v3D.setX(x);
        v3D.setY(y);
        v3D.setZ(z);

        block1.setOldData(Byte.parseByte("6"));
        block1.setBlockType("TEST_MATERIAL");
        block1.setNormalVector(v3D);

        return block1;
    }
}