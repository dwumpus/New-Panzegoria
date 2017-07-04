package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import com.panzegoria.puzzleBuilder.Services.Capabilities.Stateful;
import org.bukkit.Bukkit;

import java.util.HashMap;

/**
 * Created by roger.boone on 6/10/2017.
 */
public class PlayerStateService implements Stateful {
    private HashMap<String, WrappedPlayer> map;

    public PlayerStateService() {
        initialize();
    }

    private void initialize() {
        map = new HashMap<>();
    }

    @Override
    public void savePlayerState(WrappedPlayer state) {
        map.put(state.Name, state);
    }

    @Override
    public Integer getBlockCount() {
        return map.size();
    }

    @Override
    public WrappedPlayer getState(String name)
    {
        if(map.containsKey(name)) {
            return map.get(name);
        }

        WrappedPlayer player = new WrappedPlayer(Bukkit.getPlayer(name), this);
        savePlayerState(player);
        return player;
    }

}
