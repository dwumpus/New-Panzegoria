package com.panzegoria.puzzleBuilder.Services.Capabilities;

import com.panzegoria.puzzleBuilder.Entities.WrappedBlock;
import org.bukkit.util.Vector;

import java.util.HashMap;

/**
 * Created by roger.boone on 6/19/2017.
 */
public interface Vectorable {
    void addWrappedBlock(Vector loc, WrappedBlock block);
    Integer getBlockCount();
    HashMap<Vector, WrappedBlock> getBlocks();
}
