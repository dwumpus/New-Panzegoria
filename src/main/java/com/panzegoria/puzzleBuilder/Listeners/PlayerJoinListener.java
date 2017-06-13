package com.panzegoria.puzzleBuilder.Listeners;

import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;


/**
 * Created by roger.boone on 6/10/2017.
 */
public class PlayerJoinListener implements Listener {

    private PlayersState _state;

    public PlayerJoinListener(PlayersState state) {
        _state =state;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player joinedPlayer = event.getPlayer();
        WrappedPlayer wrappedPlayer = new WrappedPlayer(joinedPlayer, _state);
        _state.savePlayerState(wrappedPlayer);
    }

}
