package Controllers;

import Entities.PlayerState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class PlayerManager {
    private List<PlayerState> playersState;

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

        return new PlayerState(name, true);
    }

    public PlayerManager() {
        playersState = new ArrayList<>();
    }
}
