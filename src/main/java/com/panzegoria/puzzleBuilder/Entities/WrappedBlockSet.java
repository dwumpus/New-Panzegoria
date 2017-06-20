package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by roger.boone on 6/11/2017.
 */
public class WrappedBlockSet implements IWrappedBlockSet {

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

    public WrappedBlockSet(World world, ISelection selection) {
        _world = world;
        initialize();
        Vector minLocation = selection.getMinVector();
        Vector maxLocation = selection.getMaxVector();

        Block block;

        Vector offset = getOffset(minLocation);

        for (double x = minLocation.getX(); x < maxLocation.getX(); x++) {
            for (double y = minLocation.getY(); y < maxLocation.getY(); y++) {
                for (double z = minLocation.getZ(); z < maxLocation.getZ(); z++) {
                    block = world.getBlockAt(
                            new Location(world, x, y, z));

                    if(block.getType() != Material.AIR) {
                        map.put(new Vector(x, y, z).add(offset), new WrappedBlock(block));
                    }
                }
            }
        }
    }

    private Vector getOffset(Vector originalVector) {
        Vector origin = new Vector(0, 0, 0);
        return origin.subtract(originalVector);
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
        String valueOut = new String();

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
    public List<String> getBlocks() {
        List<String> valuesOut = new ArrayList<>();

        String workingBlock = new String();
        for (Vector vector : map.keySet()) {
            workingBlock = fromVector(vector);
            workingBlock = workingBlock + map.get(vector).toString();
            valuesOut.add(workingBlock);
        }

        return valuesOut;
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

    @org.jetbrains.annotations.NotNull
    private Vector toVector(String parsedValueIn) {
        String[] values = parsedValueIn.split(",");

        return new Vector(Double.parseDouble(values[0]),
                Double.parseDouble(values[1]),
                Double.parseDouble(values[2]));
    }
}
