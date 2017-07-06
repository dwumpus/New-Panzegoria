package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.WrappedPlayerState;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import com.panzegoria.puzzleBuilder.Services.Capabilities.Stateful;
import org.bukkit.Bukkit;

import java.util.HashMap;

/**
 * Created by roger.boone on 6/10/2017.
 */
public class PlayerStateService implements Stateful {
    private HashMap<String, WrappedPlayerState> map;

    public PlayerStateService() {
        initialize();
    }

    private void initialize() {
        map = new HashMap<>();
    }

    @Override
    public void savePlayerState(WrappedPlayerState state) {
        map.put(state.getName(), state);
    }

    @Override
    public WrappedPlayerState getState(String name)
    {
        if(map.containsKey(name)) {
            return map.get(name);
        }

        WrappedPlayerState player =new WrappedPlayerState();
        player.setName(name);
        savePlayerState(player);
        return player;
    }

}
