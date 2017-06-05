package Entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class BlueprintState {
    private static BlueprintState ourInstance = new BlueprintState();
    private static List<PlayerState> playersState = new ArrayList<>();

    public void SetPlayerState(PlayerState state) {
        playersState.removeIf((PlayerState p) -> p.Name.equalsIgnoreCase(state.Name));
        playersState.add(state);
    }

    public PlayerState GetPlayerState(String name) {
        for (PlayerState player : playersState
             ) {
            if(player.Name.equalsIgnoreCase(name)){
                return player;
            }
        }

        return new PlayerState(name, false);
    }

    public static BlueprintState getInstance() {
        return ourInstance;
    }

    private BlueprintState() {
    }
}
