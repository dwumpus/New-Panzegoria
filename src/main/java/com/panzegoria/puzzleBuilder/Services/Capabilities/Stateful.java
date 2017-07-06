package com.panzegoria.puzzleBuilder.Services.Capabilities;

import com.panzegoria.puzzleBuilder.Entities.WrappedPlayerState;

/**
 * Created by roger.boone on 6/13/2017.
 */
public interface Stateful {
    void savePlayerState(WrappedPlayerState state);
    WrappedPlayerState getState(String name);
}
