package Entities;

import org.bukkit.util.Vector;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class PlayerState {
    public PlayerState(String name, boolean isDrafting) {
        Name = name;
        IsDrafting = isDrafting;
    }

    public String Name = "";
    public boolean IsDrafting = false;
    public Vector Point1;
    public Vector Point2;
    public Selection SelectedLocations;
}
