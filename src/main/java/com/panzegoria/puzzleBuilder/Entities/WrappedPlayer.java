package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.entity.Player;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class WrappedPlayer {
    private IPlayersState _stateContainer;

    public ISelection Selection;
    public String Name;
    public String LoadedBlocks;
    public Boolean IsHintShowing;

    public WrappedPlayer(Player bukkitPlayer, IPlayersState stateContainer) {
        Selection = new Selection();
        Name = bukkitPlayer.getName();
        IsHintShowing = false;
        _stateContainer = stateContainer;
    }

    public void Save() {
        _stateContainer.savePlayerState(this);
    }
}
