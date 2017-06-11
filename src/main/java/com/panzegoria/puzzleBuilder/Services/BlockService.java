package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.ISelection;
import com.panzegoria.puzzleBuilder.Entities.PlayersState;
import com.panzegoria.puzzleBuilder.Entities.WrappedPlayer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by roger.boone on 6/8/2017.
 */
public class BlockService {

    private World _world;
    private ISelection _selection;
    private PlayersState _playerState;

    public BlockService(Player player, PlayersState playerState) {
        _playerState = playerState;
        _selection = _playerState.get(player.getName()).Selection;
        _world = player.getWorld();
    }

    public ArrayList<BlockState> getBlocks() {
        Vector minLocation = _selection.getMinVector();
        Vector maxLocation = _selection.getMaxVector();

        ArrayList<BlockState> blocksOut = new ArrayList<>();

        for (double x = minLocation.getX(); x < maxLocation.getX(); x++) {
            for (double y = minLocation.getY(); y < maxLocation.getY(); y++) {
                for (double z = minLocation.getZ(); z < maxLocation.getZ(); z++) {
                    blocksOut.add(_world.getBlockAt(new Location(_world, x, y, z)).getState());
                }
            }
        }

        return blocksOut;
    }

    private Vector getOffset(Vector originalVector) {
        Vector origin = new Vector(0, 0, 0);
        return origin.subtract(originalVector);
    }

    public void setBlocks(ArrayList<BlockState> blocksIn, Vector targetVector, DIRECTION quadrant, boolean actualBlocks) {

        Vector offset = getOffset(_selection.getMinVector());
        Location newLocation;

        for (BlockState block : blocksIn) {
            newLocation = block.getLocation().add(offset).add(targetVector);

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
                setActualBlock(newLocation.toVector(), block);
            } else {
                setHintBlock(newLocation.toVector(), block.getType());
            }

        }


    }

    public void setActualBlock(Vector target, BlockState block) {
        Location newLocation = new Location(_world, target.getX(), target.getY(), target.getZ());
        Block targetBlock = _world.getBlockAt(newLocation);
        targetBlock.setType(block.getType());
        targetBlock.setData(block.getData().getData());
    }

    public void setHintBlock(Vector target, Material mat) {
        target = target.add(new Vector(.5, -.5, .5));
        Location loc = new Location(_world, target.getX(), target.getY(), target.getZ());
        ArmorStand armorStand = _world.spawn(loc, ArmorStand.class);
        armorStand.setHelmet(new ItemStack(mat));
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.setVisible(false);
    }

    public enum DIRECTION {
        NORTH_EAST, NORTH_WEST, SOUTH_EAST, SOUTH_WEST
    }
}
