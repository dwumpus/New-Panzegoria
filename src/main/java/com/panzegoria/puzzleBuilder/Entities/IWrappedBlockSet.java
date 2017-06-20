package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

/**
 * Created by roger.boone on 6/19/2017.
 */
public interface IWrappedBlockSet {
    void addWrappedBlock(Vector loc, WrappedBlock block);

    Integer getBlockCount();

    List<String> getBlocks();
}
