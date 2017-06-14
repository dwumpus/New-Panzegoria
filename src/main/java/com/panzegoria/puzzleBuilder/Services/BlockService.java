package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
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
    public void setBlocks(World world, WrappedBlockSet blocksIn, Vector targetVector, DIRECTION quadrant, boolean actualBlocks) throws Exception {

        if(world==null)
            return;

        Location newLocation;
        HashMap<Vector, WrappedBlock> blocks = blocksIn.getBlocks();

        for(Vector loc : blocks.keySet()) {

            Vector newVector = loc.clone().add(targetVector);
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
                setActualBlock(newLocation, blocks.get(loc));
            } else {
                if(blocks == null) return;
                if(loc==null) return;
                if(blocks.get(loc)==null) {
                    throw new Exception("Location Null!");
                }
                setHintBlock(newLocation, blocks.get(loc).BlockType);
            }

        }


    }

    @Override
    public void setActualBlock(Location target, WrappedBlock block) {
        Block targetBlock = target.getWorld().getBlockAt(target);
        targetBlock.setType(block.BlockType);
        targetBlock.setData(block.OldData);
    }

    @Override
    public void setHintBlock(Location target, Material mat) {
        target = target.add(new Vector(.5, -.5, .5));
        ArmorStand armorStand = (ArmorStand) target.getWorld().spawnEntity(target, EntityType.ARMOR_STAND);
        //ArmorStand armorStand = world.spawn(loc, ArmorStand.class);
        if(armorStand == null)
            return;

        ItemStack itemStack = new ItemStack(mat);
        armorStand.setHelmet(itemStack);
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
    }

}
