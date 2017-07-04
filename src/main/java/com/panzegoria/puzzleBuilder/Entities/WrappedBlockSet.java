package com.panzegoria.puzzleBuilder.Entities;

import com.panzegoria.puzzleBuilder.Services.Capabilities.Vectorable;
import org.bukkit.util.Vector;
import java.util.HashMap;

/**
 * Created by roger.boone on 6/11/2017.
 */
public class WrappedBlockSet implements Vectorable {

    private HashMap<Vector, WrappedBlock> map;

    public WrappedBlockSet() {
        initialize();
    }

    public WrappedBlockSet(String serializedValue) {
        initialize();
        deserialize(serializedValue);
    }

    private void initialize() {
        map = new HashMap<>();
    }

    @Override
    public void addWrappedBlock(Vector loc, WrappedBlock block) {
        map.put(loc, block);
    }

    @Override
    public String toString() {

        String valueOut;
        valueOut = "";

        for (Vector vector : map.keySet()) {
            valueOut = valueOut + fromVector(vector);
            valueOut = valueOut + map.get(vector).toString();
            valueOut = valueOut + "\r\n";
        }

        return valueOut;
    }

    @Override
    public Integer getBlockCount() {
        return map.size();
    }

    @Override
    public HashMap<Vector, WrappedBlock> getBlocks() {

        return map;
    }

    private void deserialize(String allDataIn) {
        String [] items = allDataIn.split("\r\n");

        for (String item : items) {
            String[] parts = item.split("@");

            map.put(toVector(parts[0]), new WrappedBlock(parts[1]));
        }
    }

    private String fromVector(Vector vectorIn) {
        String valueOut = new String();
        valueOut = valueOut + vectorIn.toString();
        valueOut = valueOut + "@";

        return valueOut;
    }

    @org.jetbrains.annotations.NotNull
    private Vector toVector(String parsedValueIn) {
        String[] values = parsedValueIn.split(",");

        return new Vector(Double.parseDouble(values[0]),
                Double.parseDouble(values[1]),
                Double.parseDouble(values[2]));
    }
}
