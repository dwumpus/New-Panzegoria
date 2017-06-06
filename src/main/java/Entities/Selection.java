package Entities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

/**
 * Created by roger.boone on 6/4/2017.
 */
public class Selection {
    private Location _minLocation;
    private Location _maxLocation;
    private World _world;

    private Location ComputeMinLocation (Vector point1, Vector point2) {
        if(point1== null) return new Location(_world, point2.getX(),point2.getY(),point2.getZ());
        if(point2== null) return new Location(_world, point1.getX(),point1.getY(),point1.getZ());

        return new Location(_world, (point1.getX() < point2.getX()) ? point1.getX() : point2.getX(),
                (point1.getY() < point2.getY()) ? point1.getY() : point2.getY(),
                (point1.getZ() < point2.getZ()) ? point1.getZ() : point2.getZ());
    }

    private Location ComputeMaxLocation (Vector point1, Vector point2) {
        if(point1== null) return new Location(_world, point2.getX(),point2.getY(),point2.getZ());
        if(point2== null) return new Location(_world, point1.getX(),point1.getY(),point1.getZ());

        return new Location(_world, (point1.getX() > point2.getX()) ? point1.getX() : point2.getX(),
                (point1.getY() > point2.getY()) ? point1.getY() : point2.getY(),
                (point1.getZ() > point2.getZ()) ? point1.getZ() : point2.getZ());
    }

    public Selection(World world, Vector point1, Vector point2) {
        _minLocation = ComputeMinLocation(point1, point2);
        _maxLocation = ComputeMaxLocation(point1, point2);
        _world = world;
    }

    public Location getMinlocation() {
        return _minLocation;
    }

    public Location getMaxlocation() {
        return _maxLocation;
    }
}
