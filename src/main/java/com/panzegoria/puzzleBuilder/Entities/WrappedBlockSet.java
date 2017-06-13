package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by roger.boone on 6/11/2017.
 */
public class WrappedBlockSet {

    private HashMap<Vector, WrappedBlock> map;
    private World _world;

    public WrappedBlockSet() {
        initialize();
    }

    public WrappedBlockSet(World world, String serializedValue) {
        _world = world;
        initialize();
        deserialize(serializedValue);
    }

    private void initialize() {
        map = new HashMap<>();
    }

    public void addWrappedBlock(Vector loc, WrappedBlock block) {
        map.put(loc, block);
    }

    @Override
    public String toString() {
        String valueOut = new String();

        for (Vector vector : map.keySet()) {
            valueOut = valueOut + fromVector(vector);
            valueOut = valueOut + map.get(vector).toString();
            valueOut = valueOut + "\r\n";
        }

        return valueOut;
    }

    public Integer getBlockCount() {
        return map.size();
    }

    public HashMap<Vector, WrappedBlock> getBlocks() {
        return map;
    }

    private void deserialize(String allDataIn) {
        String [] items = allDataIn.split("\r\n");

        for (String item : items) {
            String[] parts = item.split("@");

            map.put(toVector(parts[0]), new WrappedBlock( _world, parts[1]));
        }
    }

    private String fromVector(Vector vectorIn) {
        String valueOut = new String();
        valueOut = valueOut + vectorIn.toString();
        valueOut = valueOut + "@";

        return valueOut;
    }

    private Vector toVector(String parsedValueIn) {
        String[] values = parsedValueIn.split(",");

        return new Vector(Double.parseDouble(values[0]),
                Double.parseDouble(values[1]),
                Double.parseDouble(values[2]));
    }
}
