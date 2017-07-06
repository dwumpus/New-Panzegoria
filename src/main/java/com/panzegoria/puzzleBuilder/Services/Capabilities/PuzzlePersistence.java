package com.panzegoria.puzzleBuilder.Services.Capabilities;

import com.panzegoria.puzzleBuilder.Entities.WrappedBlockSet;

/**
 * Created by roger.boone on 7/5/2017.
 */
public interface PuzzlePersistence {
    void savePuzzle(String name, WrappedBlockSet puzzle);
    WrappedBlockSet loadPuzzle(String name);
}
