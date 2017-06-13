package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import java.util.HashMap;

/**
 * Created by roger.boone on 6/8/2017.
 */
public class BlockService implements IBlockService {

    @Override
    public WrappedBlockSet getBlocks(World world, ISelection selection) {
        Vector minLocation = selection.getMinVector();
        Vector maxLocation = selection.getMaxVector();

        Block block;
        WrappedBlockSet blocksOut = new WrappedBlockSet();

        Vector offset = getOffset(minLocation);

        for (double x = minLocation.getX(); x < maxLocation.getX(); x++) {
            for (double y = minLocation.getY(); y < maxLocation.getY(); y++) {
                for (double z = minLocation.getZ(); z < maxLocation.getZ(); z++) {
                    block = world.getBlockAt(
                            new Location(world, x, y, z));

                    if(block.getType() != Material.AIR) {
                        blocksOut.addWrappedBlock(new Vector(x, y, z).add(offset),
                                new WrappedBlock(block));
                    }
                }
            }
        }

        return blocksOut;
    }

    private Vector getOffset(Vector originalVector) {
        Vector origin = new Vector(0, 0, 0);
        return origin.subtract(originalVector);
    }

    @Override
    public void setBlocks(World world, WrappedBlockSet blocksIn, Vector targetVector, DIRECTION quadrant, boolean actualBlocks) {

        Location newLocation;
        HashMap<Vector, WrappedBlock> blocks = blocksIn.getBlocks();

        for(Vector loc : blocks.keySet()) {

            Vector newVector = loc.add(targetVector);
            newLocation = new Location(world, newVector.getX(),
                    newVector.getY(), newVector.getZ());

            switch (quadrant) {
                case NORTH_EAST: {
                }
                break;

                case NORTH_WEST: {
                    newLocation.setX(newLocation.getX() * -1);
                    newLocation.setPitch(90);
                    break;
                }
                case SOUTH_EAST: {
                    newLocation.setZ(newLocation.getX());
                    newLocation.setX(newLocation.getZ() * -1);
                    newLocation.setPitch(180);
                    break;
                }
                case SOUTH_WEST: {
                    newLocation.setX(newLocation.getX() * -1);
                    newLocation.setZ(newLocation.getZ() * -1);
                    newLocation.setPitch(270);
                    break;
                }
                default: {

                }
            }

            //Set the block;
            if (actualBlocks) {
                setActualBlock(world, newLocation.toVector(), blocks.get(loc));
            } else {
                setHintBlock(world, newLocation.toVector(), blocks.get(loc).BlockType);
            }

        }


    }

    @Override
    public void setActualBlock(World world, Vector target, WrappedBlock block) {
        Location newLocation = new Location(world, target.getX(), target.getY(), target.getZ());
        Block targetBlock = world.getBlockAt(newLocation);
        targetBlock.setType(block.BlockType);
        targetBlock.setData(block.OldData);
    }

    @Override
    public void setHintBlock(World world, Vector target, Material mat) {
        target = target.add(new Vector(.5, -.5, .5));
        Location loc = new Location(world, target.getX(), target.getY(), target.getZ());
        ArmorStand armorStand = world.spawn(loc, ArmorStand.class);
        armorStand.setHelmet(new ItemStack(mat));
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
    }

}
