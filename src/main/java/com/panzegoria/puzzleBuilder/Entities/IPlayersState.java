package com.panzegoria.puzzleBuilder.Entities;

/**
 * Created by roger.boone on 6/13/2017.
 */
public interface IPlayersState {
    void savePlayerState(WrappedPlayer state);
    Integer getBlockCount();
    WrappedPlayer getState(String name);
}
