package com.panzegoria.puzzleBuilder.Entities;

import com.panzegoria.puzzleBuilder.Services.Capabilities.Selectable;
import com.panzegoria.puzzleBuilder.Services.Capabilities.Stateful;
import org.bukkit.entity.Player;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class WrappedPlayer {
    private Stateful _stateContainer;

    public Selectable Selection;
    public String Name;
    public String LoadedBlocks;
    public Boolean IsHintShowing;

    public WrappedPlayer(Player bukkitPlayer, Stateful stateContainer) {
        Selection = new Selection();
        Name = bukkitPlayer.getName();
        IsHintShowing = false;
        _stateContainer = stateContainer;
    }

    public void Save() {
        _stateContainer.savePlayerState(this);
    }
}
