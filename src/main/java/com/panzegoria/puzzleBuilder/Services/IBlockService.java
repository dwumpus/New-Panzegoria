package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.DIRECTION;
import com.panzegoria.puzzleBuilder.Entities.ISelection;
import com.panzegoria.puzzleBuilder.Entities.WrappedBlock;
import com.panzegoria.puzzleBuilder.Entities.WrappedBlockSet;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.Vector;

public interface IBlockService {
    WrappedBlockSet getBlocks(World world, ISelection selection);

    void setBlocks(World world, WrappedBlockSet blocksIn, Vector targetVector, DIRECTION quadrant, boolean actualBlocks);

    void setActualBlock(World world, Vector target, WrappedBlock block);

    void setHintBlock(World world, Vector target, Material mat);
}
