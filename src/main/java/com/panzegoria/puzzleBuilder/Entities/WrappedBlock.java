package com.panzegoria.puzzleBuilder.Entities;

import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by roger.boone on 6/11/2017.
 */
public class WrappedBlock {

    public Material BlockType;
    public Byte OldData;

    public WrappedBlock(Location location) {
        BlockType = location.getBlock().getType();
        //BlockData = location.getBlock().getState().getData();
        OldData = location.getBlock().getData();
        //_world = location.getWorld();
    }

    public WrappedBlock(Block block) {
        BlockType = block.getType();
        //BlockData = block.getState().getData();
        OldData = block.getData();
        //_world = block.getWorld();
    }
    public WrappedBlock(BlockState blockState) {
        BlockType = blockState.getType();
        //BlockData = blockState.getData();
        OldData = blockState.getBlock().getData();
        //_world = blockState.getWorld();
    }

    public WrappedBlock(String serializedState) {
        String[] serializedData = serializedState.split("!");

        BlockType = materialFromString(serializedData[0]);
        OldData = Byte.parseByte(serializedData[1]);
        //_world = world;
    }

    public void setActualBlock(World world, Vector target) {
        Location newLocation = new Location(world, target.getX(), target.getY(), target.getZ());
        Block targetBlock = world.getBlockAt(newLocation);
        targetBlock.setType(BlockType);
        targetBlock.setData(OldData);
    }

    public void setHintBlock(World world, Vector target) {
        Location newLocation = new Location(world, target.getX(), target.getY(), target.getZ());
        Block targetBlock = newLocation.getBlock();
        WrappedBlock originalBlock = new WrappedBlock(targetBlock);

        targetBlock.setType(BlockType);
        targetBlock.setData(OldData);

        new BukkitRunnable() {

            @Override
            public void run() {
                targetBlock.setType(originalBlock.BlockType);
                targetBlock.setData(originalBlock.OldData);
            }
        }.runTaskLater(PuzzleBuilderPlugin.INSTANCE, 100);
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
