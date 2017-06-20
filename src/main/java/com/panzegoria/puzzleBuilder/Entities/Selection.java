package com.panzegoria.puzzleBuilder.Entities;
import org.bukkit.util.Vector;


/**
 * Created by roger.boone on 6/4/2017.
 */
public class Selection implements ISelection {

    private Vector point1;
    private Vector point2;

    @Override
    public Vector getMinVector() {
        if (point1 == null) return new Vector(point2.getX(), point2.getY(), point2.getZ());
        if (point2 == null) return new Vector(point1.getX(), point1.getY(), point1.getZ());

        return new Vector((point1.getX() < point2.getX()) ? point1.getX() : point2.getX(),
                (point1.getY() < point2.getY()) ? point1.getY() : point2.getY(),
                (point1.getZ() < point2.getZ()) ? point1.getZ() : point2.getZ());
    }

    @Override
    public Vector getMaxVector() {
        if (point1 == null) return new Vector(point2.getX(), point2.getY(), point2.getZ());
        if (point2 == null) return new Vector(point1.getX(), point1.getY(), point1.getZ());

        return new Vector((point1.getX() > point2.getX()) ? point1.getX() + 1 : point2.getX(),
                (point1.getY() > point2.getY()) ? point1.getY() + 1 : point2.getY(),
                (point1.getZ() > point2.getZ()) ? point1.getZ() + 1 : point2.getZ()).add(new Vector(1, 1, 1));
    }

    @Override
    public Vector getPoint1() {
        return point1;
    }

    @Override
    public void setPoint1(Vector point1) {
        this.point1 = point1;
    }

    @Override
    public Vector getPoint2() {
        return point2;
    }

    @Override
    public void setPoint2(Vector point2) {
        this.point2 = point2;
    }

}
