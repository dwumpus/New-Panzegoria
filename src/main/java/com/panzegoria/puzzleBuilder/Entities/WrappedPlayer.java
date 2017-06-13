package com.panzegoria.puzzleBuilder.Entities;

import com.panzegoria.puzzleBuilder.Services.BlockService;
import org.bukkit.entity.Player;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class WrappedPlayer {
    private IPlayersState _stateContainer;

    public boolean IsDrafting = true;
    public DIRECTION Direction = DIRECTION.NORTH_EAST;
    public ISelection Selection;
    public String Name;

    public WrappedPlayer(Player bukkitPlayer, PlayersState stateContainer) {
        Direction = DIRECTION.NORTH_EAST;
        Selection = new Selection();
        Name = bukkitPlayer.getName();
        _stateContainer = stateContainer;
    }

    public void Save() {
        _stateContainer.savePlayerState(this);
    }
}
