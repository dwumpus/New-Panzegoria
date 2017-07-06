package com.panzegoria.puzzleBuilder.Services.Capabilities;

import com.panzegoria.puzzleBuilder.Entities.*;

/**
 * Created by roger.boone on 7/5/2017.
 */
public interface ObjectWrappers<P, B, V, W> {
    WrappedPlayerState wrapPlayer(P player);
    WrappedBlock wrapBlock(B block, Vector3D originOffset);
    WrappedBlockSet wrapSelection(Selection selection, W world);
    P unwrapPlayer(WrappedPlayerState wrappedPlayerState);
    void placeBlock(WrappedBlock wrappedBlock, W world, V target);
    void placeHintBlock(WrappedBlock wrappedBlock, W world, V target, int timeToExpire);
    WrappedBlock[] unwrapBlocks(WrappedBlockSet wrappedBlockSet);
    V unwrapVector3D(Vector3D vector3D);
    Vector3D wrapVector(V vector);
    void placeBlocks(WrappedBlockSet wrappedBlockSet, V origin, W world);
    void placeHintBlocks(WrappedBlockSet wrappedBlockSet, V origin, W world, int timeToExpire);
}
