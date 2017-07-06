package com.panzegoria.puzzleBuilder.Entities;


/**
 * Created by roger.boone on 6/4/2017.
 */
public class WrappedPlayerState {
    private Selection VectorsSelected = new Selection();
    private String Name = "";
    private Boolean IsHintShowing = false;

    public Selection getVectorsSelected() {
        return VectorsSelected;
    }

    public void setVectorsSelected(Selection vectorsSelected) {
        VectorsSelected = vectorsSelected;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getHintShowing() {
        return IsHintShowing;
    }

    public void setHintShowing(Boolean hintShowing) {
        IsHintShowing = hintShowing;
    }
}
