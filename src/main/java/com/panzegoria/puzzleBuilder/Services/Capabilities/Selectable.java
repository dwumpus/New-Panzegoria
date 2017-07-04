package com.panzegoria.puzzleBuilder.Services.Capabilities;

import org.bukkit.util.Vector;

/**
 * Created by roger.boone on 6/9/2017.
 */
public interface Selectable {
    Vector getMinVector();

    Vector getMaxVector();

    Vector getPoint1();

    void setPoint1(Vector point1);

    Vector getPoint2();

    void setPoint2(Vector point2);

}
