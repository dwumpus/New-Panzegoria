package com.panzegoria.puzzleBuilder.Entities;

import org.bukkit.Material;

/**
 * Created by roger.boone on 6/11/2017.
 */
public class WrappedBlock {

    private Vector3D NormalVector;
    private String BlockType;
    private Byte OldData;

    public Vector3D getNormalVector() {
        return NormalVector;
    }

    public void setNormalVector(Vector3D normalVector) {
        NormalVector = normalVector;
    }

    public String getBlockType() {
        return BlockType;
    }

    public void setBlockType(String blockType) {
        BlockType = blockType;
    }

    public Byte getOldData() {
        return OldData;
    }

    public void setOldData(Byte oldData) {
        OldData = oldData;
    }
}
