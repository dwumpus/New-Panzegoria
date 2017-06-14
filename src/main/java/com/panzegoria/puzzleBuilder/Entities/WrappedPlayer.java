package com.panzegoria.puzzleBuilder.Entities;

import com.panzegoria.puzzleBuilder.Services.BlockService;
import org.bukkit.entity.Player;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class WrappedPlayer {
    private IPlayersState _stateContainer;

    public DIRECTION Direction = DIRECTION.NORTH_EAST;
    public ISelection Selection;
    public String Name;
    public MODE Mode;
    public String LoadedBlocks;

    public WrappedPlayer(Player bukkitPlayer, IPlayersState stateContainer) {
        Direction = DIRECTION.NORTH_EAST;
        Selection = new Selection();
        Name = bukkitPlayer.getName();
        Mode = MODE.BUILD_PUZZLE;
        _stateContainer = stateContainer;
    }

    public void Save() {
        _stateContainer.savePlayerState(this);
    }
}
