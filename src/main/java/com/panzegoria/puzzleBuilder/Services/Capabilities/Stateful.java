package com.panzegoria.puzzleBuilder.Services.Capabilities;

import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;

/**
 * Created by roger.boone on 6/13/2017.
 */
public interface Stateful {
    void savePlayerState(WrappedPlayer state);
    Integer getBlockCount();
    WrappedPlayer getState(String name);
}
