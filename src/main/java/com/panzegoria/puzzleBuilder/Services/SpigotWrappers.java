package com.panzegoria.puzzleBuilder.Services;

import com.panzegoria.puzzleBuilder.Entities.*;
import com.panzegoria.puzzleBuilder.PuzzleBuilderPlugin;
import com.panzegoria.puzzleBuilder.Services.Capabilities.ObjectWrappers;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Created by roger.boone on 7/5/2017.
 */
public class SpigotWrappers implements ObjectWrappers<Player, Block, Vector, World> {

    @Override
    public WrappedPlayerState wrapPlayer(Player player) {
        WrappedPlayerState playerOut = new WrappedPlayerState();

        //set values
        playerOut.setHintShowing(false);
        playerOut.setName(player.getDisplayName());
        playerOut.setVectorsSelected(new Selection());

        return playerOut;
    }

    @Override
    public WrappedBlock wrapBlock(Block block, Vector3D originOffset) {
        WrappedBlock blockOut = new WrappedBlock();

        blockOut.setNormalVector(wrapVector(block.getLocation().toVector().add(unwrapVector3D(originOffset))));
        blockOut.setBlockType(block.getType().name());
        blockOut.setOldData(block.getData());

        return blockOut;
    }

    @Override
    public WrappedBlockSet wrapSelection(Selection selection, World world) {

        Vector highPoint = Vector.getMaximum(
                unwrapVector3D(selection.getPoint1()),
                unwrapVector3D(selection.getPoint2()));

        Vector lowPoint = Vector.getMinimum(
                unwrapVector3D(selection.getPoint1()),
                unwrapVector3D(selection.getPoint2()));

        Vector length = highPoint.subtract(lowPoint);

        int blockCount = (int)(Math.abs(length.getX()) *
                Math.abs(length.getY()) *
                Math.abs(length.getZ()));


        WrappedBlock[] map = new WrappedBlock[blockCount];

        int index = 0;
        Vector3D offset = wrapVector(getOffset(lowPoint));

        for (double x = lowPoint.getX(); x < highPoint.getX(); x++) {
            for (double y = lowPoint.getY(); y <highPoint.getZ(); y++) {
                for (double z = lowPoint.getY(); z <highPoint.getZ(); z++) {
                    Block block = world.getBlockAt(
                            new Location(world, x, y, z));

                    if(block.getType() != Material.AIR) {
                        map[index] = wrapBlock(block, offset);
                    }
                    index ++;
                }
            }
        }

        WrappedBlockSet out = new WrappedBlockSet();
        out.setMap(map);

        return out;
    }

    private Vector getOffset(Vector originalVector) {
        Vector origin = new Vector(0, 0, 0);
        return origin.subtract(originalVector);
    }


    @Override
    public Player unwrapPlayer(WrappedPlayerState wrappedPlayerState) {
        return Bukkit.getServer().getPlayer(wrappedPlayerState.getName());
    }

    @Override
    public void placeBlock(WrappedBlock wrappedBlock, World world, Vector target) {
        Location newLocation = new Location(world, target.getX(), target.getY(), target.getZ());
        Block targetBlock = world.getBlockAt(newLocation);
        targetBlock.setType(Material.valueOf(wrappedBlock.getBlockType()));
        targetBlock.setData(wrappedBlock.getOldData());
    }

    @Override
    public void placeHintBlock(WrappedBlock wrappedBlock, World world, Vector target, int timeToExpire) {
        Location newLocation = new Location(world, target.getX(), target.getY(), target.getZ());
        Block targetBlock = newLocation.getBlock();
        Material originalType = targetBlock.getType();
        Byte originalData = targetBlock.getData();

        targetBlock.setType(Material.valueOf(wrappedBlock.getBlockType()));
        targetBlock.setData(wrappedBlock.getOldData());

        new BukkitRunnable() {

            @Override
            public void run() {
                targetBlock.setType(originalType);
                targetBlock.setData(originalData);
            }
        }.runTaskLater(PuzzleBuilderPlugin.INSTANCE, timeToExpire);
    }

    @Override
    public WrappedBlock[] unwrapBlocks(WrappedBlockSet wrappedBlockSet) {
        return wrappedBlockSet.getMap();
    }


    @Override
    public Vector unwrapVector3D(Vector3D vector3D) {
        Vector vectorOut = new Vector(vector3D.getX(),vector3D.getY(),vector3D.getZ());
        return vectorOut;
    }

    @Override
    public Vector3D wrapVector(Vector vector) {
        Vector3D vector3DOut = new Vector3D();
        if(vector == null) return vector3DOut;

        vector3DOut.setX(vector.getX());
        vector3DOut.setY(vector.getY());
        vector3DOut.setZ(vector.getZ());

        return vector3DOut;
    }

    @Override
    public void placeBlocks(WrappedBlockSet wrappedBlockSet, Vector origin, World world) {
        for (WrappedBlock block : wrappedBlockSet.getMap()) {
            placeBlock(block, world, origin.add(unwrapVector3D(block.getNormalVector())));
        }
    }

    @Override
    public void placeHintBlocks(WrappedBlockSet wrappedBlockSet, Vector origin, World world, int timeToExpire) {
        for (WrappedBlock block : wrappedBlockSet.getMap()) {
            placeHintBlock(block,
                    world,
                    origin.add(unwrapVector3D(block.getNormalVector())),
                    timeToExpire);
        }
    }

}
