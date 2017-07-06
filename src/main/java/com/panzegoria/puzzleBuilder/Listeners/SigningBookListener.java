package com.panzegoria.puzzleBuilder.Listeners;

import static com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin.wrappers;
import static com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin.persistence;

import com.panzegoria.puzzleBuilder.Entities.WrappedBlockSet;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayerState;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import com.panzegoria.puzzleBuilder.Services.Capabilities.Stateful;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;

/**
 * Created by roger.boone on 7/5/2017.
 */
public class SigningBookListener implements Listener {

    private Stateful _stateContainer;

    public SigningBookListener(Stateful stateContainer) {
        _stateContainer = stateContainer;
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event) {
        Player player = event.getPlayer();

        WrappedPlayerState puzzlePlayer = _stateContainer.getState(player.getName());

        WrappedBlockSet puzzleData = wrappers.wrapSelection(puzzlePlayer.getVectorsSelected(), event.getPlayer().getWorld());

        persistence.savePuzzle(event.getNewBookMeta().getTitle(), puzzleData);

    }
}