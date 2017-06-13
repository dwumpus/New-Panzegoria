package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

/**
 * Created by roger.boone on 6/11/2017.
 */
public class WrappedBlock {

    public Material BlockType;
    public MaterialData BlockData;
    public Byte OldData;
    private World _world;

    public WrappedBlock(Location location) {
        BlockType = location.getBlock().getType();
        //BlockData = location.getBlock().getState().getData();
        OldData = location.getBlock().getData();
        _world = location.getWorld();
    }

    public WrappedBlock(Block block) {
        BlockType = block.getType();
        //BlockData = block.getState().getData();
        OldData = block.getData();
        _world = block.getWorld();
    }
    public WrappedBlock(BlockState blockState) {
        BlockType = blockState.getType();
        //BlockData = blockState.getData();
        OldData = blockState.getBlock().getData();
        _world = blockState.getWorld();
    }

    public WrappedBlock(World world, String serializedState) {
        String[] serializedData = serializedState.split("!");

        BlockType = materialFromString(serializedData[0]);
        //BlockData = serializedData.
        OldData = Byte.parseByte(serializedData[1]);
        _world = world;
    }

    public void setActualBlock(Vector target) {
        Location newLocation = new Location(_world, target.getX(), target.getY(), target.getZ());
        Block targetBlock = _world.getBlockAt(newLocation);
        targetBlock.setType(BlockType);
        targetBlock.setData(OldData);
    }

    public void setHintBlock(Vector target) {
        target = target.add(new Vector(.5, -.5, .5));
        Location loc = new Location(_world, target.getX(), target.getY(), target.getZ());
        ArmorStand armorStand = _world.spawn(loc, ArmorStand.class);
        armorStand.setHelmet(new ItemStack(BlockType));
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
    }

    private Material materialFromString(String stringIn) {
        return Material.valueOf(stringIn);
    }

    @Override
    public String toString() {
        String stringOut = new String();
        stringOut = stringOut + BlockType.toString();
        stringOut = stringOut + "!";
        //stringOut = stringOut + "0";
        //stringOut = stringOut + BlockData.toString();
        //stringOut = stringOut + "|";
        stringOut = stringOut + OldData.toString();
        return stringOut;
    }
}
