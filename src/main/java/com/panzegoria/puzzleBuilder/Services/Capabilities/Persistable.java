package com.panzegoria.puzzleBuilder.Services.Capabilities;

/**
 * Created by roger.boone on 7/3/2017.
 */
public interface Persistable<T> {
    void Save(String name);
    T Load(String name);
}
